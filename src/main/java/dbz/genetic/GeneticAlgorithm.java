package dbz.genetic;

import dbz.board.CommunityBoard;
import dbz.domain.BoardName;
import dbz.domain.Emblem;
import dbz.visualize.BoardsViewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneticAlgorithm {

    // GA parameters
    private static final int POPULATION_SIZE = 200;
    private static final int GENERATIONS = 2000;
    private static final double CROSSOVER_RATE = 0.8;
    private static final double MUTATION_RATE = 0.3;
    private static final int TOURNAMENT_SIZE = 3;
    private static final int ELITE_COUNT = 2;

    // SA parameters
    private static final double INITIAL_TEMPERATURE = 200;
    private static final double COOLING_RATE = 0.97;
    private static final int SA_ITERATIONS = 100;

    // Diversity injection parameters:
    private static final int DI_INTERVAL = 5;
    private static final double DI_FRACTION = 0.4;

    private final Random random = new Random();

    private final BoardManager boardManager = new BoardManager();

    /**
     * Runs the hybrid GA + simulated annealing solver over all boards.
     */
    public Chromosome run() {
        BoardsViewer viewer = new BoardsViewer();

        List<Chromosome> population = initializePopulation();
        Chromosome best = null;
        int bestFitness = Integer.MIN_VALUE;

        for (int gen = 0; gen < GENERATIONS; gen++) {
            Map<Chromosome, Integer> fitnessMap = evaluatePopulation(population);

            for (Map.Entry<Chromosome, Integer> entry : fitnessMap.entrySet()) {
                if (entry.getValue() > bestFitness) {
                    bestFitness = entry.getValue();
                    best = entry.getKey();
                }
            }
            viewer.updatePanel(best);

            // Apply elitism
            List<Chromosome> newPopulation = new ArrayList<>();
            List<Chromosome> sortedPopulation = new ArrayList<>(population);
            sortedPopulation.sort(Comparator.comparingInt(fitnessMap::get).reversed());

            for (int i = 0; i < ELITE_COUNT && i < sortedPopulation.size(); i++) {
                newPopulation.add(sortedPopulation.get(i));
            }

            while (newPopulation.size() < POPULATION_SIZE) {
                Chromosome parent1 = tournamentSelection(population, fitnessMap);
                Chromosome parent2 = tournamentSelection(population, fitnessMap);
                Chromosome offspring = (random.nextDouble() < CROSSOVER_RATE) ? crossover(parent1, parent2) : parent1;

                newPopulation.add(simulatedAnnealing(mutate(offspring)));
            }

            // Replace a fraction of non-elite individuals with random chromosomes.
            if (gen % DI_INTERVAL == 0) {
                int injectionCount = (int) (POPULATION_SIZE * DI_FRACTION);
                for (int i = 0; i < injectionCount; i++) {
                    newPopulation.set(ELITE_COUNT + random.nextInt(POPULATION_SIZE - ELITE_COUNT), createRandomChromosome());
                }
            }

            population = newPopulation;
            System.out.println("Generation " + gen + ", best fitness: " + bestFitness);
        }
        return best;
    }

    private List<Chromosome> initializePopulation() {
        return IntStream.range(0, POPULATION_SIZE)
                .parallel()
                .mapToObj(i -> createRandomChromosome())
                .collect(Collectors.toList());
    }

    private Chromosome createRandomChromosome() {
        List<Gene> template = new ArrayList<>();

        for (BoardName board : BoardName.values()) {
            int count = boardManager.getBoardSize(board);
            for (int i = 0; i < count; i++) {
                template.add(new Gene(i == 0 ? Emblem.getCenterForBoard(board) : null, board, i));
            }
        }

        List<Integer> unlockedIndices = IntStream.range(0, template.size())
                .parallel()
                .filter(i -> template.get(i).vertexID() != 0)
                .boxed().toList();

        List<Emblem> available = new ArrayList<>(BoardManager.EMBLEMS);

        List<Gene> genes = new ArrayList<>(template);

        List<Emblem> perm = new ArrayList<>(available);
        Collections.shuffle(perm, random);
        int assignCount = Math.min(unlockedIndices.size(), perm.size());

        for (int j = 0; j < assignCount; j++) {
            int idx = unlockedIndices.get(j);
            Gene oldGene = genes.get(idx);
            genes.set(idx, new Gene(perm.get(j), oldGene.board(), oldGene.vertexID()));
        }

        return new Chromosome(genes);
    }

    /**
     * Evaluates the population by converting each chromosome into full board configurations
     * and summing the fitness scores (as computed by BoardScorer) from all boards.
     */
    private Map<Chromosome, Integer> evaluatePopulation(List<Chromosome> population) {
        return population.parallelStream()
                .collect(Collectors.toMap(c -> c, this::evaluateChromosome, (a, b) -> b));
    }

    /**
     * Evaluates a chromosome by grouping genes by board, constructing each board with the gene assignments,
     * and summing the fitness scores across boards.
     */
    private int evaluateChromosome(Chromosome chromosome) {
        int totalFitness = 0;

        Map<BoardName, List<Gene>> boardGeneMap = new HashMap<>();
        for (Gene gene : chromosome.genes()) {
            boardGeneMap.computeIfAbsent(gene.board(), k -> new ArrayList<>()).add(gene);
        }

        // For each board, build the board and calculate its fitness.
        for (BoardName board : BoardName.values()) {
            CommunityBoard cb = boardManager.getGraphBoard(board);
            List<Gene> genes = boardGeneMap.get(board);
            if (genes != null) {
                for (Gene gene : genes) {
                    if (gene.emblem() != null) {
                        cb.setEmblemInSpace(gene.emblem(), gene.vertexID());
                    }
                }
            }
            totalFitness += FitnessCalculator.calculateFitness(cb);
        }
        return totalFitness;
    }

    /**
     * Tournament selection: choose the best individual among a small random subset.
     */
    private Chromosome tournamentSelection(List<Chromosome> population, Map<Chromosome, Integer> fitnessMap) {
        List<Chromosome> tournament = new ArrayList<>();

        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            tournament.add(population.get(random.nextInt(population.size())));
        }

        return tournament.stream()
                .max(Comparator.comparingInt(fitnessMap::get))
                .orElse(tournament.getFirst());
    }

    /**
     * Crossover operator: applies an ordered crossover on the unlocked gene positions,
     * while preserving locked genes.
     */
    private Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        List<Gene> genes1 = new ArrayList<>(parent1.genes());
        List<Gene> genes2 = new ArrayList<>(parent2.genes());
        List<Integer> unlockedIndices = new ArrayList<>();

        for (int i = 0; i < genes1.size(); i++) {
            if (genes1.get(i).vertexID() != 0) {
                unlockedIndices.add(i);
            }
        }

        int start = random.nextInt(unlockedIndices.size());
        int end = start + random.nextInt(unlockedIndices.size() - start);
        Set<Emblem> segment = new HashSet<>();

        for (int i = start; i <= end; i++) {
            int idx = unlockedIndices.get(i);
            segment.add(genes1.get(idx).emblem());
        }

        List<Gene> childGenes = new ArrayList<>(genes2);
        int childIndex = 0;

        for (int idx : unlockedIndices) {
            if (childIndex < start || childIndex > end) {
                Gene candidate = genes2.get(idx);
                if (segment.contains(candidate.emblem())) {
                    candidate = genes1.get(idx);
                }
                childGenes.set(idx, candidate);
            }
            childIndex++;
        }
        return new Chromosome(childGenes);
    }

    /**
     * Mutation operator: performs a swap mutation on two unlocked genes.
     */
    private Chromosome mutate(Chromosome chromosome) {
        List<Gene> genes = new ArrayList<>(chromosome.genes());
        List<Integer> unlockedIndices = new ArrayList<>();

        for (int i = 0; i < genes.size(); i++) {
            if (genes.get(i).vertexID() != 0) {
                unlockedIndices.add(i);
            }
        }

        if (random.nextDouble() < MUTATION_RATE && unlockedIndices.size() >= 2) {
            int idx1 = unlockedIndices.get(random.nextInt(unlockedIndices.size()));
            int idx2 = unlockedIndices.get(random.nextInt(unlockedIndices.size()));
            Gene temp = genes.get(idx1);
            genes.set(idx1, genes.get(idx2));
            genes.set(idx2, temp);
        }
        return new Chromosome(genes);
    }

    /**
     * Refines an individual using simulated annealing.
     */
    private Chromosome simulatedAnnealing(Chromosome chromosome) {
        double temperature = INITIAL_TEMPERATURE;
        Chromosome current = chromosome;
        int currentFitness = evaluateChromosome(current);

        for (int i = 0; i < SA_ITERATIONS; i++) {
            Chromosome neighbor = getNeighbor(current);
            int neighborFitness = evaluateChromosome(neighbor);
            int delta = neighborFitness - currentFitness;

            if (delta > 0 || Math.exp(delta / temperature) > random.nextDouble()) {
                current = neighbor;
                currentFitness = neighborFitness;
            }
            temperature *= COOLING_RATE;
        }
        return current;
    }

    /**
     * Generates a neighboring solution by swapping two unlocked genes.
     */
    private Chromosome getNeighbor(Chromosome chromosome) {
        List<Gene> genes = new ArrayList<>(chromosome.genes());
        List<Integer> unlockedIndices = new ArrayList<>();

        for (int i = 0; i < genes.size(); i++) {
            if (genes.get(i).vertexID() != 0) {
                unlockedIndices.add(i);
            }
        }

        if (unlockedIndices.size() < 2) {
            return chromosome;
        }

        int idx1 = unlockedIndices.get(random.nextInt(unlockedIndices.size()));
        int idx2 = unlockedIndices.get(random.nextInt(unlockedIndices.size()));

        Gene temp = genes.get(idx1);
        genes.set(idx1, genes.get(idx2));
        genes.set(idx2, temp);

        return new Chromosome(genes);
    }
}

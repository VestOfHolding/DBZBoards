package dbz.genetic;

import dbz.domain.Emblem;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneticAlgorithm {

    private static final double MUTATION_RATE = 0.04;

    private static final Random RANDOM = new Random();

    private static int tournamentSize;
    private static int populationSize;

    public static void go(int size) {
        populationSize = size;
        tournamentSize = Math.min((int)Math.round((double)populationSize * 0.2), 10);
        Population currentGen = new Population(size);
        currentGen.buildInitialGeneration();

        double bestScore = 0.0;

        for (int i = 0; i <= 4000; ++i) {
            if (currentGen.getBestOfGeneration().getScore() > bestScore || i % 20 == 0) {
                System.out.println("Best score of generation " + i + ": " + currentGen.getBestOfGeneration().getScore());
                bestScore = currentGen.getBestOfGeneration().getScore();
            }
            currentGen = createNextGeneration(currentGen);
        }
        currentGen.getBestOfGeneration().printBoards();
    }

    private static Population createNextGeneration(Population currentGen) {
        Population nextGen = new Population(populationSize);

        nextGen.addIndividual(currentGen.getBestOfGeneration());

        nextGen.addIndividuals(IntStream.range(1, populationSize)
                .parallel()
                .mapToObj(i -> {
                    Individual i0 = currentGen.tournamentSelect(tournamentSize);
                    Individual i1 = currentGen.tournamentSelect(tournamentSize);
                    return crossover(i0, i1);
        }).collect(Collectors.toList()));

        nextGen.getPopulation().forEach(GeneticAlgorithm::mutate);

        return nextGen;
    }

    private static Individual crossover(Individual i0, Individual i1) {
        Set<Emblem> emblemsSoFar = new HashSet<>();

        Individual child = new Individual();
        List<Gene> genes = Emblem.getNonCenterEmblems()
                .parallelStream()
                .map(emblem -> {
                    Gene gene;
                    if (RANDOM.nextDouble() < 0.5) {
                        gene = getGeneFromParents(i0, i1, emblemsSoFar, emblem);
                    }
                    else {
                        gene = getGeneFromParents(i1, i0, emblemsSoFar, emblem);
                    }
                    emblemsSoFar.add(gene.getEmblem());
                    return gene;
                })
                .collect(Collectors.toList());

        child.setGenes(genes);
        return child;
    }

    private static Gene getGeneFromParents(Individual i0, Individual i1, Set<Emblem> emblemsSoFar, Emblem emblem) {
        Gene gene = i0.getGeneByEmblem(emblem);

        if (gene == null || emblemsSoFar.contains(gene.getEmblem())) {
            gene = i1.getGeneByEmblem(emblem);

            if (gene == null || emblemsSoFar.contains(gene.getEmblem())) {
                gene = new Gene(emblem, getRandomUnusedChromosome(i0, i1));
            }
        }
        return gene;
    }

    private static Chromosome getRandomUnusedChromosome(Individual i0, Individual i1) {
        if (CollectionUtils.isEmpty(i0.getUnusedChromosomes())) {
            return i1.getUnusedChromosomes().get(RANDOM.nextInt(i1.getUnusedChromosomes().size()));
        }
        else if (CollectionUtils.isEmpty(i1.getUnusedChromosomes())) {
            return i0.getUnusedChromosomes().get(RANDOM.nextInt(i0.getUnusedChromosomes().size()));
        }
        else {
            return CollectionUtils.intersection(i0.getUnusedChromosomes(), i1.getUnusedChromosomes()).iterator().next();
        }
    }

    private static void mutate(Individual individual) {
        for (Emblem emblem : Emblem.getNonCenterEmblems()) {
            if (RANDOM.nextDouble() > MUTATION_RATE) {
                continue;
            }

            Gene mutatedGene = individual.getGeneByEmblem(emblem);
            Chromosome oldChromosome = mutatedGene.getChromosome();
            Chromosome unusedChromosome = individual.getUnusedChromosomes().get(RANDOM.nextInt(individual.getUnusedChromosomes().size()));

            individual.getUnusedChromosomes().remove(unusedChromosome);
            individual.getUnusedChromosomes().add(oldChromosome);

            mutatedGene.setChromosome(unusedChromosome);
        }
    }
}

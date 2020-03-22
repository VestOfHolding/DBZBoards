package dbz.genetic;

import dbz.domain.Emblem;

import java.util.Random;

public class GeneticAlgorithm {

    private static final double MUTATION_RATE = 0.04;

    private static final Random RANDOM = new Random();

    public static void go(int size) {
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
        Population nextGen = PopulationGenerator.rouletteStyleGenerator(currentGen);

        nextGen.getPopulation().forEach(GeneticAlgorithm::mutate);
        return nextGen;
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

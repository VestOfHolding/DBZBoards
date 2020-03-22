package dbz.genetic;

import dbz.domain.Emblem;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PopulationGenerator {

    private static final Random RANDOM = new Random();

    public static Population rouletteStyleGenerator(Population currentGen) {
        double[] cumulativeScores = new double[currentGen.getSize()];
        List<Individual> currentGenList = currentGen.getPopulation();

        cumulativeScores[0] = currentGenList.get(0).getScore();

        for (int i = 1; i < currentGen.getSize(); ++i) {
            cumulativeScores[i] = cumulativeScores[i - 1] + currentGenList.get(i).getScore();
        }

        Population nextGen = new Population(currentGen.getSize());
        nextGen.addIndividual(currentGen.getBestOfGeneration());
        nextGen.addIndividuals(IntStream.range(1, currentGen.getSize())
                .parallel()
                .mapToObj(i -> crossover(rouletteSelect(cumulativeScores, currentGenList),
                        rouletteSelect(cumulativeScores, currentGenList)))
                .collect(Collectors.toList()));
        return nextGen;
    }

    private static Individual rouletteSelect(double[] cumulativeScores, List<Individual> currentGenList) {
        double rouletteVal = RANDOM.nextDouble() * cumulativeScores[cumulativeScores.length - 1];

        int index = Arrays.binarySearch(cumulativeScores, rouletteVal);
        return currentGenList.get(index < 0 ? Math.abs(index + 1) : index);
    }

    public static Population tournamentStyleGenerator(Population currentGen) {
        int tournamentSize = Math.min((int) Math.round((double) currentGen.getSize() * 0.2), 10);

        Population nextGen = new Population(currentGen.getSize());
        nextGen.addIndividual(currentGen.getBestOfGeneration());
        nextGen.addIndividuals(IntStream.range(1, currentGen.getSize())
                .parallel()
                .mapToObj(i -> crossover(tournamentSelect(tournamentSize, currentGen.getPopulation()),
                        tournamentSelect(tournamentSize, currentGen.getPopulation())))
                .collect(Collectors.toList()));
        return nextGen;
    }

    private static Individual tournamentSelect(int tournamentSize, List<Individual> currentGenList) {
        Population tempPopulation = new Population(tournamentSize);
        Random random = new Random();
        for (int i = 0; i < tournamentSize; ++i) {
            tempPopulation.addIndividual(currentGenList.get(random.nextInt(currentGenList.size())));
        }
        return tempPopulation.getBestOfGeneration();
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
        if (CollectionUtils.isNotEmpty(i1.getUnusedChromosomes())) {
            return i1.getUnusedChromosomes().get(RANDOM.nextInt(i1.getUnusedChromosomes().size()));
        }
        else if (CollectionUtils.isNotEmpty(i0.getUnusedChromosomes())) {
            return i0.getUnusedChromosomes().get(RANDOM.nextInt(i0.getUnusedChromosomes().size()));
        }
        else {
            return null;
        }
    }
}

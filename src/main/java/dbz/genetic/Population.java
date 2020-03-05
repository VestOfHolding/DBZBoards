package dbz.genetic;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Population {

    private List<Individual> population;

    private int size;

    public Population(int size) {
        this.size = size;
        population = new ArrayList<>();
    }

    public void buildInitialGeneration() {
        population = IntStream.range(0, size).parallel()
                .mapToObj(i -> new Individual())
                .sorted(Comparator.comparing(Individual::getScore).reversed())
                .collect(Collectors.toList());
    }

    public void addIndividual(Individual individual) {
        if (population == null) {
            population = new ArrayList<>();
        }
        population.add(individual);
    }

    public void addIndividuals(List<Individual> individuals) {
        if (population == null) {
            population = new ArrayList<>();
        }
        population.addAll(individuals);
    }

    public Individual getBestOfGeneration() {
        population.sort(Comparator.comparing(Individual::getScore).reversed());
        return population.get(0);
    }

    public Individual tournamentSelect(int tournamentSize) {
        Population tempPopulation = new Population(size);
        Random random = new Random();
        for (int i = 0; i < tournamentSize; ++i) {
            tempPopulation.addIndividual(population.get(random.nextInt(size)));
        }
        return tempPopulation.getBestOfGeneration();
    }
}

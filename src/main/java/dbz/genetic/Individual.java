package dbz.genetic;

import dbz.domain.Emblem;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Individual implements Comparable<Individual> {
    @Getter
    private List<Gene> genes;

    private BoardManager boardManager;

    private Random random;

    @Getter
    private List<Chromosome> unusedChromosomes;

    public Individual() {
        this(true);
    }

    public Individual(boolean randomlyInitializeBoardManager) {
        random = new Random();
        genes = new ArrayList<>();
        unusedChromosomes = new ArrayList<>();
        boardManager = randomlyInitializeBoardManager ? initFullBoardManager() : new BoardManager();
    }

    private BoardManager initFullBoardManager() {
        BoardManager boardManager = new BoardManager();

        List<Emblem> emblems = new ArrayList<>(Emblem.getNonCenterEmblems());
        List<Chromosome> chromosomes = new ArrayList<>(ChromsomeFactory.getAllChromosomes());

        do {
            Emblem emblem = emblems.get(random.nextInt(emblems.size()));
            Chromosome chromosome = chromosomes.get(random.nextInt(chromosomes.size()));

            genes.add(new Gene(emblem, chromosome));

            emblems.remove(emblem);
            chromosomes.remove(chromosome);
        } while (CollectionUtils.isNotEmpty(emblems));

        genes.forEach(boardManager::setEmblemOnBoard);
        setUnusedChromosomes(chromosomes);

        boardManager.calculateScores();
        return boardManager;
    }

    private void setUnusedChromosomes(List<Chromosome> chromosomes) {
        if (CollectionUtils.isEmpty(genes)) {
            return;
        }

        unusedChromosomes = chromosomes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
        genes.forEach(boardManager::setEmblemOnBoard);
        setUnusedChromosomes(ChromsomeFactory.getAllChromosomes().stream()
                .filter(c -> genes.stream().noneMatch(g -> g.getChromosome().equals(c)))
                .collect(Collectors.toList()));
        boardManager.calculateScores();
    }

    public double getScore() {
        return boardManager.getScore();
    }

    public Gene getGeneByEmblem(Emblem emblem) {
        return genes.stream()
                .filter(g -> g.getEmblem().equals(emblem))
                .findFirst()
                .orElse(null);
    }

    public void printBoards() {
        boardManager.printBoards();
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(getScore(), o.getScore());
    }
}

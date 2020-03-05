package dbz;

import dbz.board.BaseBoard;
import dbz.board.TrainingBoard;
import dbz.domain.Emblem;
import dbz.genetic.GeneticAlgorithm;
import dbz.visualize.GraphMaker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static dbz.Utils.getEmblemsAndLinkedEmblems;

public class Main {

    public static void main(String[] args) {
        GeneticAlgorithm.go(100);
    }

    private static void printSpecificBoard() {
        BaseBoard board = new TrainingBoard();

        GraphMaker graphMaker = new GraphMaker();
        graphMaker.displayBoard(board);
    }

    private static void printSubsetSizes() {
        LinkBonusFactory linkFactory = new LinkBonusFactory();

        List<Emblem> emblems = Emblem.getAsList();

        Set<Emblem> emblemSubset = getEmblemsAndLinkedEmblems(linkFactory, emblems, Emblem::getZBonus);
        System.out.println(emblemSubset.size());

        emblemSubset = getEmblemsAndLinkedEmblems(linkFactory, emblems, Emblem::getCookBonus);
        System.out.println(emblemSubset.size());

        emblemSubset = getEmblemsAndLinkedEmblems(linkFactory, emblems, Emblem::getTrainBonus);
        System.out.println(emblemSubset.size());

        emblemSubset = getEmblemsAndLinkedEmblems(linkFactory, emblems, Emblem::getDevBonus);
        System.out.println(emblemSubset.size());

        emblemSubset = getEmblemsAndLinkedEmblems(linkFactory, emblems, Emblem::getGodBonus);
        System.out.println(emblemSubset.size());

        emblemSubset = getEmblemsAndLinkedEmblems(linkFactory, emblems, Emblem::getAdultBonus);
        System.out.println(emblemSubset.size());

        emblemSubset = getEmblemsAndLinkedEmblems(linkFactory, emblems, Emblem::getAdventureBonus);
        System.out.println(emblemSubset.size());
    }

    private static void display() {
        GraphMaker graphMaker = new GraphMaker();
        graphMaker.display();
    }

    private static void printAdjacencyMatrix(LinkBonusFactory linkFactory, List<Emblem> emblems) {
        System.out.println("," + emblems.stream()
                .map(Emblem::normalizeName)
                .collect(Collectors.joining(","))
        );

        List<String> linkMarks;

        for (Emblem emblem : emblems) {
            linkMarks = new ArrayList<>();

            for (Emblem secondEmblem : emblems) {
                linkMarks.add(linkFactory.doEmblemsShareLink(emblem, secondEmblem) ? "1" : "0");
            }
            System.out.println(emblem.normalizeName() + ',' + String.join(",", linkMarks));
        }
    }

    private static void printAdjacencyList(LinkBonusFactory linkFactory, List<Emblem> emblems) {
        Set<Emblem> emblemSubset = getEmblemsAndLinkedEmblems(linkFactory, emblems, Emblem::getZBonus);

        emblemSubset.forEach(e -> System.out.println(e.normalizeName()));

        List<Emblem> linkedEmblems;

        for (Emblem emblem : emblemSubset) {

            Set<LinkBonus> linkBonuses = linkFactory.getLinkBonusesByEmblem(emblem);

            linkedEmblems = linkBonuses.stream()
                    .map(LinkBonus::getLinkMembers)
                    .flatMap(Collection::stream)
                    .distinct()
                    .collect(Collectors.toList());

            System.out.println(String.join(" ", linkedEmblems.stream()
                            .map(Emblem::normalizeName)
                            .collect(Collectors.toSet())));
        }
    }
}

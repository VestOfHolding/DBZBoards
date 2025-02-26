package dbz.genetic;

import dbz.LinkBonus;
import dbz.board.CommunityBoard;
import dbz.board.layout.EmblemSpace;
import dbz.domain.Emblem;
import org.apache.commons.collections4.CollectionUtils;
import org.jgrapht.graph.AsSubgraph;

import java.util.Set;
import java.util.stream.Collectors;

public class FitnessCalculator {

    private static final int PENALTY = 1;

    public static int calculateScore(CommunityBoard board) {
        int emblemScore = board.getEmblemsOnBoard().stream()
                .mapToInt(e -> e.getBonusForBoard(board.getBoardName()))
                .sum();

        int linkScore = getActiveLinkBonuses(board).stream()
                .mapToInt(link -> link.getBonus() * link.getLinkMembers().size())
                .sum();

        return emblemScore + linkScore;
    }

    public static Set<LinkBonus> getActiveLinkBonuses(CommunityBoard board) {
        Set<Emblem> boardEmblems = board.getEmblemsOnBoard();
        Set<LinkBonus> potentialBonuses = board.getLinkBonusFactory().linksContainingAllEmblems(boardEmblems);
        if (CollectionUtils.isEmpty(potentialBonuses)) {
            return Set.of();
        }

        return potentialBonuses.parallelStream().filter(linkBonus -> {
            Set<Emblem> linkMembers = linkBonus.getLinkMembers();
            Set<EmblemSpace> spaces = board.getVerticesByEmblems(linkMembers);
            if (spaces.size() < linkMembers.size()) {
                return false;
            }
            // Build a subgraph with the vertices corresponding to the link members.
            AsSubgraph<EmblemSpace, ?> subgraph = new AsSubgraph<>(board, spaces);
            return subgraph.vertexSet().stream()
                    .anyMatch(space -> CollectionUtils.isNotEmpty(subgraph.edgesOf(space)));
        }).collect(Collectors.toSet());
    }

    public static int calculateFitness(CommunityBoard board) {
        int score = calculateScore(board);
        int target = board.getBoardName().getTargetScore();

        if (score < target) {
            int penalty = PENALTY * (target - score);
            return score - penalty;
        }
        return score;
    }
}

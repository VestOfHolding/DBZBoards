package dbz.board;

import dbz.LinkBonus;
import dbz.LinkBonusFactory;
import dbz.board.layout.DBZEdge;
import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;
import org.apache.commons.collections4.CollectionUtils;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CommunityBoard extends DefaultUndirectedGraph<EmblemSpace, DBZEdge> {

    private Map<Integer, EmblemSpace> vertexMap;

    private BoardName boardName;

    private Set<LinkBonus> activeLinkBonuses;

    private LinkBonusFactory linkBonusFactory;

    private int score;

    public CommunityBoard(BoardName boardName) {
        super(DBZEdge.class);
        vertexMap = new HashMap<>();
        score = 0;
        this.boardName = boardName;
        activeLinkBonuses = new HashSet<>();
        linkBonusFactory = new LinkBonusFactory();
    }

    @Override
    public boolean addVertex(EmblemSpace v) {
        if (v == null || vertexMap.containsKey(v.getSpaceId())) {
            return false;
        }

        vertexMap.put(v.getSpaceId(), v);
        return super.addVertex(v);
    }

    public boolean setEmblemInSpace(Emblem emblem, int spaceId) {
        if (spaceId >= getBoardSize()) {
            return false;
        }

        EmblemSpace space = vertexMap.get(spaceId);

        if (space.getEmblem() != null) {
            return false;
        }

        space.setEmblem(emblem);
        return true;
    }

    public void populateActiveLinkBonuses() {
        Set<LinkBonus> potentialBonuses = linkBonusFactory.linksContainingAllEmblems(getEmblemsOnBoard());

        if (CollectionUtils.isEmpty(potentialBonuses)) {
            return;
        }

        for (LinkBonus linkBonus : potentialBonuses) {
            AsSubgraph<EmblemSpace, DBZEdge> linkSubgraph = new AsSubgraph<>(this, getVerticesByEmblems(linkBonus.getLinkMembers()));

            linkSubgraph.vertexSet().stream()
                    .filter(space -> CollectionUtils.isNotEmpty(linkSubgraph.edgesOf(space)))
                    .forEach(space -> activeLinkBonuses.add(linkBonus));
        }
    }

    public void calculateScore() {
        score = 0;
        getEmblemsOnBoard().forEach(e -> score += e.getBonusForBoard(boardName));
        activeLinkBonuses.forEach(link -> score += (link.getBonus() * link.getLinkMembers().size()));
    }

//    @Override
//    public boolean removeVertex(EmblemSpace v) {
//        if (v == null || !vertexMap.containsKey(v.getSpaceId())) {
//            return false;
//        }
//
//        vertexMap.remove(v.getSpaceId());
//        return super.removeVertex(v);
//    }

    public int getBoardSize() {
        return vertexSet().size();
    }

    public EmblemSpace getVertexById(int id) {
        return vertexMap.getOrDefault(id, null);
    }

    public EmblemSpace getVertexByEmblem(Emblem emblem) {
        return vertexSet().stream()
                .filter(e -> e.getEmblem() == emblem)
                .findFirst()
                .orElse(null);
    }

    public Set<EmblemSpace> getVerticesByEmblems(Set<Emblem> emblems) {
        return emblems.stream().map(this::getVertexByEmblem).collect(Collectors.toSet());
    }

    public Set<Emblem> getEmblemsOnBoard() {
        return vertexSet().stream()
                .map(EmblemSpace::getEmblem)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public int getScore() {
        populateActiveLinkBonuses();
        calculateScore();
        return score;
    }
}

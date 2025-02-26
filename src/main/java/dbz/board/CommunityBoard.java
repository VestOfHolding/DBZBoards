package dbz.board;

import dbz.LinkBonusFactory;
import dbz.board.layout.DBZEdge;
import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;
import lombok.Getter;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CommunityBoard extends DefaultUndirectedGraph<EmblemSpace, DBZEdge> {

    private final Map<Integer, EmblemSpace> vertexMap;

    @Getter
    private final BoardName boardName;

    @Getter
    private final LinkBonusFactory linkBonusFactory;

    public CommunityBoard(BoardName boardName) {
        super(DBZEdge.class);
        vertexMap = new HashMap<>();
        this.boardName = boardName;
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

    public void setEmblemInSpace(Emblem emblem, int spaceId) {
        if (spaceId >= getBoardSize()) {
            return;
        }

        EmblemSpace space = vertexMap.get(spaceId);

        if (space.getEmblem() != null) {
            return;
        }

        space.setEmblem(emblem);
        updateEdgeStates(emblem, spaceId);
    }

    private void updateEdgeStates(Emblem emblem, int spaceId) {
        for (DBZEdge edge : edgeSet()) {
            if (getEdgeSource(edge).getSpaceId() == spaceId) {
                edge.updateSourceEmblem(emblem);
            }
            else if (getEdgeTarget(edge).getSpaceId() == spaceId) {
                edge.updateTargetEmblem(emblem);
            }
        }
    }

    public int getBoardSize() {
        return vertexSet().size();
    }

    public EmblemSpace getVertexById(int id) {
        return vertexMap.getOrDefault(id, null);
    }

    public EmblemSpace getVertexByEmblem(Emblem emblem) {
        return vertexSet().stream().filter(e -> e.getEmblem() == emblem).findFirst().orElse(null);
    }

    public Set<EmblemSpace> getVerticesByEmblems(Set<Emblem> emblems) {
        return emblems.stream().map(this::getVertexByEmblem).collect(Collectors.toSet());
    }

    public Set<Emblem> getEmblemsOnBoard() {
        return vertexSet().stream().map(EmblemSpace::getEmblem).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public void scaleVertices(double scaleFactor) {
        vertexMap.values().forEach(e -> e.scaleCoordinates(scaleFactor));
    }
}

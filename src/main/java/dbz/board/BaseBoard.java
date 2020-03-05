package dbz.board;

import dbz.board.layout.DBZEdge;
import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;
import lombok.Getter;
import org.jgrapht.graph.builder.GraphBuilder;

public abstract class BaseBoard {

    @Getter
    protected BoardName boardName;

    @Getter
    protected CommunityBoard board;

    protected GraphBuilder<EmblemSpace, DBZEdge, CommunityBoard> graphBuilder;

    protected Emblem startEmblem;

    protected abstract void buildInitialGraph();

    public void initializeBoard() {
        board = new CommunityBoard(boardName);
        graphBuilder = new GraphBuilder<>(board);

        buildInitialGraph();
    }

    public int getScore() {
        return board.getScore();
    }
}

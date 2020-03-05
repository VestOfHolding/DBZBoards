package dbz.board;

import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;

public class AdultBoard extends BaseBoard {
    public AdultBoard() {
        boardName = BoardName.ADULT;
        startEmblem = Emblem.getCenterForBoard(boardName);

        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                new EmblemSpace(startEmblem, index++, true),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++)
        );

        graphBuilder.addVertex(new EmblemSpace(index++));
        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(0));
        graphBuilder.addEdge(board.getVertexById(5), board.getVertexById(index - 1));

        graphBuilder.addEdge(board.getVertexById(3), board.getVertexById(0));
    }
}

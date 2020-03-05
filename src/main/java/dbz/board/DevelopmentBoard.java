package dbz.board;

import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;

public class DevelopmentBoard extends BaseBoard {
    public DevelopmentBoard() {
        boardName = BoardName.DEVELOPMENT;
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
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++)
        );

        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(1));

        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 2));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 3));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 5));
    }
}

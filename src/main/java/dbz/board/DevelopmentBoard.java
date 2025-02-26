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
                new EmblemSpace(startEmblem, index++, true, 2765, 345),
                new EmblemSpace(index++, 3060, 345),
                new EmblemSpace(index++, 3030, 240),
                new EmblemSpace(index++, 2925, 130),
                new EmblemSpace(index++, 2765, 80),
                new EmblemSpace(index++, 2605, 130),
                new EmblemSpace(index++, 2510, 240),
                new EmblemSpace(index++, 2470, 345), //
                new EmblemSpace(index++, 2540, 550),
                new EmblemSpace(index++, 2670, 620), //
                new EmblemSpace(index++, 2860, 620), //
                new EmblemSpace(index++, 2990, 550)
        );

        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(1));

        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 2));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 3));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 5));
    }
}

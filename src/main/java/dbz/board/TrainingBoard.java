package dbz.board;

import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;

public class TrainingBoard extends BaseBoard {

    public TrainingBoard() {
        boardName = BoardName.TRAINING;
        startEmblem = Emblem.getCenterForBoard(boardName);

        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                new EmblemSpace(startEmblem, index++, true, 2020, 350),
                new EmblemSpace(index++, 2200, 350),
                new EmblemSpace(index++, 2150, 480),
                new EmblemSpace(index++, 2020, 530),
                new EmblemSpace(index++, 1890, 480),
                new EmblemSpace(index++, 1840, 350),
                new EmblemSpace(index++, 1890, 225),
                new EmblemSpace(index++, 2020, 160),
                new EmblemSpace(index++, 2150, 225)
        );

        graphBuilder.addEdge(board.getVertexById(1), board.getVertexById(index - 1));

        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(1));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(2));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(4));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(5));

        int startOuterCircleId = index;

        graphBuilder.addEdgeChain(
                new EmblemSpace(index++, 2080, 65),
                new EmblemSpace(index++, 2200, 105),
                new EmblemSpace(index++, 2300, 205),
                new EmblemSpace(index++, 2350, 350),
                new EmblemSpace(index++, 2300, 495),
                new EmblemSpace(index++, 2200, 595),
                new EmblemSpace(index++, 2080, 635),
                new EmblemSpace(index++, 1950, 635),
                new EmblemSpace(index++, 1830, 595),
                new EmblemSpace(index++, 1730, 495),
                new EmblemSpace(index++, 1680, 350),
                new EmblemSpace(index++, 1730, 205),
                new EmblemSpace(index++, 1830, 105),
                new EmblemSpace(index++, 1950, 65)
        );

        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(startOuterCircleId));
    }
}

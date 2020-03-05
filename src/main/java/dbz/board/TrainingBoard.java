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
                new EmblemSpace(startEmblem, index++, true),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++)
        );

        graphBuilder.addEdge(board.getVertexById(1), board.getVertexById(index - 1));

        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(1));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(2));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(4));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(5));

        int startOuterCircleId = index;

        graphBuilder.addEdgeChain(
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
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++)
        );

        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(startOuterCircleId));
    }
}

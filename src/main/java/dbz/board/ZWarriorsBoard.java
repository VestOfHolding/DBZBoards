package dbz.board;

import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;

public class ZWarriorsBoard extends BaseBoard {

    public ZWarriorsBoard() {
        boardName = BoardName.ZWARRIOR;
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
                new EmblemSpace(index++)
        );

        graphBuilder.addEdgeChain(
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++),
                new EmblemSpace(index++)
        );

        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 4));

        graphBuilder.addVertex(new EmblemSpace(index++));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 1));

        graphBuilder.addVertex(new EmblemSpace(index++));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 1));
    }
}

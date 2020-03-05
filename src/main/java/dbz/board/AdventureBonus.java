package dbz.board;

import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;

public class AdventureBonus extends BaseBoard {
    public AdventureBonus() {
        boardName = BoardName.ADVENTURE;
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
                new EmblemSpace(index)
        );

        graphBuilder.addEdge(board.getVertexById(1), board.getVertexById(7));
        graphBuilder.addEdge(board.getVertexById(1), board.getVertexById(10));
        graphBuilder.addEdge(board.getVertexById(1), board.getVertexById(11));

        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(3));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(6));
    }
}

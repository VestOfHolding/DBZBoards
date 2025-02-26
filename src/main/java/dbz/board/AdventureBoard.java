package dbz.board;

import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;

public class AdventureBoard extends BaseBoard {
    public AdventureBoard() {
        boardName = BoardName.ADVENTURE;
        startEmblem = Emblem.getCenterForBoard(boardName);

        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                new EmblemSpace(startEmblem, index++, true, 2460, 1150),
                new EmblemSpace(index++, 2630, 1015),
                new EmblemSpace(index++, 2650, 1260),
                new EmblemSpace(index++, 2450, 1350),
                new EmblemSpace(index++, 2300, 1290),
                new EmblemSpace(index++, 2180, 1210),
                new EmblemSpace(index++, 2170, 1060),
                new EmblemSpace(index++, 2340, 940),
                new EmblemSpace(index++, 2290, 790),
                new EmblemSpace(index++, 2450, 820),
                new EmblemSpace(index++, 2600, 860),
                new EmblemSpace(index, 2730, 900)
        );

        graphBuilder.addEdge(board.getVertexById(1), board.getVertexById(7));
        graphBuilder.addEdge(board.getVertexById(1), board.getVertexById(10));
        graphBuilder.addEdge(board.getVertexById(1), board.getVertexById(11));

        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(3));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(6));
    }
}

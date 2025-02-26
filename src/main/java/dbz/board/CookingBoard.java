package dbz.board;

import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;

public class CookingBoard extends BaseBoard {

    public CookingBoard() {
        boardName = BoardName.COOKING;
        startEmblem = Emblem.getCenterForBoard(boardName);

        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                new EmblemSpace(startEmblem, index++, true, 1230, 420),
                new EmblemSpace(index++, 1400, 370),
                new EmblemSpace(index++, 1495, 480),
                new EmblemSpace(index++, 1385, 560),
                new EmblemSpace(index++, 1230, 585),
                new EmblemSpace(index++, 1075, 560),
                new EmblemSpace(index++, 965, 480),
                new EmblemSpace(index++, 1060, 370)
        );

        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 1));

        graphBuilder.addEdgeChain(
                new EmblemSpace(index++, 1350, 140),
                new EmblemSpace(index++, 1475, 230),
                new EmblemSpace(index++, 1560, 100)
        );
        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(index - 3));

        graphBuilder.addEdgeChain(
                new EmblemSpace(index++, 830, 155),
                new EmblemSpace(index++, 840, 285),
                new EmblemSpace(index++, 1040, 140)
        );
        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(index - 3));

        graphBuilder.addVertex(new EmblemSpace(index++, 1240, 70));
        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(index - 2));
    }
}

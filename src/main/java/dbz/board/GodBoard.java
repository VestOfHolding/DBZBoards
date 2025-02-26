package dbz.board;

import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;

public class GodBoard extends BaseBoard {
    public GodBoard() {
        boardName = BoardName.GODS;
        startEmblem = Emblem.getCenterForBoard(boardName);

        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                new EmblemSpace(startEmblem, index++, true, 750, 985),
                new EmblemSpace(index++, 575, 985),
                new EmblemSpace(index++, 630, 1130),
                new EmblemSpace(index++, 750, 1250),
                new EmblemSpace(index++, 870, 1130),
                new EmblemSpace(index++, 925, 985),
                new EmblemSpace(index++, 910, 810)
        );

        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(5));

        graphBuilder.addVertex(new EmblemSpace(index++, 590, 810));
        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(1));

        graphBuilder.addEdgeChain(
                new EmblemSpace(index++, 365, 985),
                new EmblemSpace(index++, 405, 1120),
                new EmblemSpace(index++, 530, 1210)
        );
        graphBuilder.addEdge(board.getVertexById(1), board.getVertexById(index - 3));
        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(3));

        graphBuilder.addEdgeChain(
                new EmblemSpace(index++, 970, 1210),
                new EmblemSpace(index++, 1095, 1120),
                new EmblemSpace(index++, 1135, 985)
        );
        graphBuilder.addEdge(board.getVertexById(3), board.getVertexById(index - 3));
        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(5));
    }
}

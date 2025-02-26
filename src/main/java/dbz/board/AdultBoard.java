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
                new EmblemSpace(startEmblem, index++, true, 1600, 1150),
                new EmblemSpace(index++, 1780, 1275),
                new EmblemSpace(index++, 2015, 1050),
                new EmblemSpace(index++, 1840, 920),
                new EmblemSpace(index++, 1660, 800),
                new EmblemSpace(index++, 1530, 890),
                new EmblemSpace(index++, 1290, 890),
                new EmblemSpace(index++, 1260, 1200)
        );

        graphBuilder.addVertex(new EmblemSpace(index++, 1390, 1000));
        graphBuilder.addEdge(board.getVertexById(index - 1), board.getVertexById(0));
        graphBuilder.addEdge(board.getVertexById(5), board.getVertexById(index - 1));

        graphBuilder.addEdge(board.getVertexById(3), board.getVertexById(0));
    }
}

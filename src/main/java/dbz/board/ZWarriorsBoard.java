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
                new EmblemSpace(startEmblem, index++, true, 380, 340),
                new EmblemSpace(index++, 75, 610),
                new EmblemSpace(index++, 320, 610),
                new EmblemSpace(index++, 575, 610),
                new EmblemSpace(index++, 650, 265)
        );

        graphBuilder.addEdgeChain(
                new EmblemSpace(index++, 700, 60),
                new EmblemSpace(index++, 465, 60),
                new EmblemSpace(index++, 215, 60),
                new EmblemSpace(index++, 120, 450)
        );

        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 4));

        graphBuilder.addVertex(new EmblemSpace(index++, 510, 410));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 1));

        graphBuilder.addVertex(new EmblemSpace(index++, 250, 280));
        graphBuilder.addEdge(board.getVertexById(0), board.getVertexById(index - 1));
    }
}

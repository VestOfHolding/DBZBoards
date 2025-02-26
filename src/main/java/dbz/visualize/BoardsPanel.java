package dbz.visualize;

import dbz.LinkBonus;
import dbz.board.CommunityBoard;
import dbz.board.layout.DBZEdge;
import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;
import dbz.genetic.Chromosome;
import dbz.genetic.FitnessCalculator;
import dbz.genetic.Gene;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;

public class BoardsPanel extends JPanel {

    private static final int EMBLEM_DIAMETER = 50;
    private static final int EMBLEM_RADIUS = EMBLEM_DIAMETER / 2;

    private final Map<BoardName, CommunityBoard> boards;
    private final Map<Emblem, BufferedImage> emblemImages;

    public BoardsPanel(Map<BoardName, CommunityBoard> boards,
                          Map<Emblem, BufferedImage> emblemImages) {
        this.boards = boards;
        this.emblemImages = emblemImages;

        setPreferredSize(new Dimension(2500, 1120));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        for (Map.Entry<BoardName, CommunityBoard> entry : boards.entrySet()) {
            CommunityBoard board = entry.getValue();

            drawEdges(g, board);
            drawEmblems(g, board);
        }
    }

    private void drawEdges(Graphics g, CommunityBoard board) {
        Set<LinkBonus> activeLinkBonuses = FitnessCalculator.getActiveLinkBonuses(board);

        Set<DBZEdge> edges = board.edgeSet();
        for (DBZEdge edge : edges) {
            EmblemSpace source = board.getEdgeSource(edge);
            EmblemSpace target = board.getEdgeTarget(edge);
            if (source == null || target == null) {
                return;
            }

            g.setColor(isLinkBonus(source, target, activeLinkBonuses) ? Color.GREEN : Color.GRAY);
            g.drawLine(source.getX(), source.getY(), target.getX(), target.getY());
        }
    }

    private static boolean isLinkBonus(EmblemSpace source, EmblemSpace target, Set<LinkBonus> activeLinkBonuses) {
        Emblem e1 = source.getEmblem();
        Emblem e2 = target.getEmblem();
        if (e1 != null && e2 != null) {
            return activeLinkBonuses.stream()
                    .anyMatch(link -> link.getLinkMembers().contains(e1) && link.getLinkMembers().contains(e2));
        }
        return false;
    }

    private void drawEmblems(Graphics g, CommunityBoard board) {
        for (EmblemSpace space : board.vertexSet()) {
            Emblem emblem = space.getEmblem();

            if (emblem != null) {
                BufferedImage img = emblemImages.get(emblem);
                if (img != null) {
                    g.drawImage(img,
                            space.getX() - EMBLEM_RADIUS, space.getY() - EMBLEM_RADIUS,
                            EMBLEM_DIAMETER, EMBLEM_DIAMETER,
                            this);
                } else {
                    drawEmptySpace(g, space);
                }
            } else {
                drawEmptySpace(g, space);
            }
        }
    }

    private static void drawEmptySpace(Graphics g, EmblemSpace space) {
        g.setColor(Color.BLACK);
        g.fillOval(space.getX() - EMBLEM_RADIUS, space.getY() - EMBLEM_RADIUS, EMBLEM_DIAMETER, EMBLEM_DIAMETER);
    }

    public void updateBoards(Chromosome chromosome) {
        for (Gene gene : chromosome.genes()) {
            CommunityBoard board = boards.get(gene.board());

            EmblemSpace space = board.getVertexById(gene.vertexID());
            if (space != null) {
                space.setEmblem(gene.emblem());
            }
        }

        repaint();
    }
}

package dbz.visualize;

import dbz.board.AdultBoard;
import dbz.board.AdventureBoard;
import dbz.board.CookingBoard;
import dbz.board.DevelopmentBoard;
import dbz.board.GodBoard;
import dbz.board.TrainingBoard;
import dbz.board.ZWarriorsBoard;
import dbz.board.CommunityBoard;
import dbz.domain.BoardName;
import dbz.domain.Emblem;
import dbz.genetic.Chromosome;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class BoardsViewer extends JFrame {

    private final BoardsPanel boardsPanel;

    public BoardsViewer() {
        super("DBZ Community Boards");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Map<BoardName, CommunityBoard> boards = new HashMap<>();
        boards.put(BoardName.ADULT, new AdultBoard().getBoard());
        boards.put(BoardName.ADVENTURE, new AdventureBoard().getBoard());
        boards.put(BoardName.COOKING, new CookingBoard().getBoard());
        boards.put(BoardName.DEVELOPMENT, new DevelopmentBoard().getBoard());
        boards.put(BoardName.GODS, new GodBoard().getBoard());
        boards.put(BoardName.TRAINING, new TrainingBoard().getBoard());
        boards.put(BoardName.ZWARRIOR, new ZWarriorsBoard().getBoard());

        Map<Emblem, BufferedImage> emblemImages = loadEmblemImages();

        boardsPanel = new BoardsPanel(boards, emblemImages);
        getContentPane().add(boardsPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Map<Emblem, BufferedImage> loadEmblemImages() {
        Map<Emblem, BufferedImage> map = new HashMap<>();
        for (Emblem emblem : Emblem.values()) {
            try {

                BufferedImage img = ImageIO.read(new File(getClass().getClassLoader().getResource(emblem.fileName()).toURI()));
                map.put(emblem, img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public void updatePanel(Chromosome chromosome) {
        boardsPanel.updateBoards(chromosome);
    }
}


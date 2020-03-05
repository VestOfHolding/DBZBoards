package dbz.genetic;

import dbz.board.AdultBoard;
import dbz.board.AdventureBonus;
import dbz.board.BaseBoard;
import dbz.board.CookingBoard;
import dbz.board.DevelopmentBoard;
import dbz.board.GodBoard;
import dbz.board.TrainingBoard;
import dbz.board.ZWarriorsBoard;
import dbz.domain.BoardName;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardManager {
    private Map<BoardName, BaseBoard> BOARDS_BY_NAME;

    public BoardManager() {
        BaseBoard zWarriorsBoard = new ZWarriorsBoard();
        BaseBoard cookingBoard = new CookingBoard();
        BaseBoard trainingBoard = new TrainingBoard();
        BaseBoard developmentBoard = new DevelopmentBoard();
        BaseBoard godBoard = new GodBoard();
        BaseBoard adultBoard = new AdultBoard();
        BaseBoard adventureBonus = new AdventureBonus();

        List<BaseBoard> communityBoards = List.of(zWarriorsBoard, cookingBoard,
                trainingBoard, developmentBoard,
                godBoard, adultBoard, adventureBonus);

        BOARDS_BY_NAME = communityBoards.stream()
                .collect(Collectors.toMap(BaseBoard::getBoardName, board -> board));
    }

    public BaseBoard getBoardByName(BoardName name) {
        return BOARDS_BY_NAME.get(name);
    }

    public int getBoardSize(BoardName name) {
        return BOARDS_BY_NAME.get(name).getBoard().vertexSet().size();
    }

    public void setEmblemOnBoard(Gene gene) {
        BOARDS_BY_NAME.get(gene.getBoard()).getBoard().setEmblemInSpace(gene.getEmblem(), gene.getSpaceId());
    }

    public void calculateScores() {
        BOARDS_BY_NAME.values().forEach(c -> c.getBoard().calculateScore());
    }

    public double getScore() {
        return BOARDS_BY_NAME.values().stream()
                .mapToDouble(b -> ((double)b.getScore() / (double)b.getBoardName().getTargetScore()))
                .average()
                .orElse(0.0);
    }

    public Map<BoardName, Integer> getScoresByBoard() {
        return BOARDS_BY_NAME.values().stream()
                .collect(Collectors.toMap(BaseBoard::getBoardName, BaseBoard::getScore, (a, b) -> b));
    }

    public void printBoards() {
        BOARDS_BY_NAME.values().forEach(b -> System.out.println(b.getBoardName() + " ; " + b.getScore() + " ; " + b.getBoard().toString()));
    }
}

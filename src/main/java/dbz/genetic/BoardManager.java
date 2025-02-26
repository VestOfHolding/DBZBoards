package dbz.genetic;

import dbz.board.AdultBoard;
import dbz.board.AdventureBoard;
import dbz.board.BaseBoard;
import dbz.board.CommunityBoard;
import dbz.board.CookingBoard;
import dbz.board.DevelopmentBoard;
import dbz.board.GodBoard;
import dbz.board.TrainingBoard;
import dbz.board.ZWarriorsBoard;
import dbz.domain.BoardName;
import dbz.domain.Emblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BoardManager {

    public static final List<Emblem> ALL_EMBLEMS = Arrays.asList(Emblem.values());

    public static final List<Emblem> EMBLEMS = new ArrayList<>(Emblem.getNonCenterEmblems());

    public final Map<BoardName, BaseBoard> BOARDS_BY_NAME;

    private final Map<BoardName, Integer> BOARD_VERTEX_COUNTS;

    public BoardManager() {
        List<BaseBoard> communityBoards = getBaseBoards();

        BOARDS_BY_NAME = communityBoards.stream()
                .collect(Collectors.toMap(BaseBoard::getBoardName, board -> board));

        BOARD_VERTEX_COUNTS = communityBoards.stream()
                .collect(Collectors.toMap(BaseBoard::getBoardName, b -> b.getBoard().getBoardSize()));
    }

    private static List<BaseBoard> getBaseBoards() {
        BaseBoard zWarriorsBoard = new ZWarriorsBoard();
        BaseBoard cookingBoard = new CookingBoard();
        BaseBoard trainingBoard = new TrainingBoard();
        BaseBoard developmentBoard = new DevelopmentBoard();
        BaseBoard godBoard = new GodBoard();
        BaseBoard adultBoard = new AdultBoard();
        BaseBoard adventureBonus = new AdventureBoard();

        return List.of(zWarriorsBoard, cookingBoard,
                trainingBoard, developmentBoard,
                godBoard, adultBoard, adventureBonus);
    }

    public CommunityBoard getGraphBoard(BoardName name) {
        return BOARDS_BY_NAME.get(name).getBoard();
    }

    public int getBoardSize(BoardName name) {
        return BOARD_VERTEX_COUNTS.get(name);
    }
}

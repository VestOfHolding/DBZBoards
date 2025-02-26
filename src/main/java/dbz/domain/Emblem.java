package dbz.domain;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Emblem {
    GOKU(25, 8, 25, 6, 19, 12, 25, BoardName.ZWARRIOR),
    GOHAN(25, 10, 25, 12, 25, 8, 15),
    CHICHI(11, 59, 11, 13, 8, 21, 11, BoardName.COOKING),
    MASTER_ROSHI(10, 13, 21, 13, 8, 25, 11, BoardName.ADULT),
    NAM(13, 13, 16, 10, 10, 16, 13),
    ANDROID_8(12, 15, 12, 25, 10, 15, 12),
    KING_YEMMA(17, 9, 12, 12, 25, 17, 9),
    KAMI(12, 8, 10, 12, 25, 25, 12),
    YAJIROBE(8, 15, 11, 8, 13, 11, 25, BoardName.ADVENTURE),
    TIEN(25, 13, 18, 11, 9, 13, 16),
    CHIAOTZU(11, 25, 13, 8, 8, 11, 15),
    YAMCHA(25, 12, 10, 10, 10, 25, 17),
    KRILLIN(25, 9, 25, 5, 7, 9, 25),
    PICCOLO(25, 4, 25, 8, 25, 13, 10),
    KING_KAI(8, 10, 25, 13, 20, 15, 10, BoardName.TRAINING),
    PUAR(4, 25, 4, 6, 12, 14, 25),
    MR_POPO(13, 15, 13, 13, 25, 13, 10, BoardName.GODS),
    PILAF(7, 13, 7, 25, 16, 10, 23),
    SHU(11, 11, 11, 25, 11, 11, 11),
    MAI(3, 23, 3, 25, 3, 23, 13),
    LAUNCH(5, 25, 5, 5, 5, 25, 22),
    SAIBAMAN(15, 15, 15, 19, 12, 12, 12),
    MERCENARY_TAO(13, 16, 16, 19, 7, 19, 10),
    MASTER_SHEN(8, 10, 15, 13, 10, 25, 10),
    TURTLE(7, 25, 5, 7, 16, 20, 10),
    BULMA(6, 13, 11, 25, 15, 15, 25, BoardName.DEVELOPMENT),
    VEGETA(25, 25, 25, 12, 7, 12, 14),
    GURU(7, 10, 10, 10, 15, 15, 25),
    NAIL(13, 16, 13, 11, 16, 13, 18),
    OOLONG(4, 8, 6, 10, 12, 25, 25),
    TRUNKS(25, 10, 25, 25, 6, 11, 8),
    BUBBLES(11, 11, 25, 7, 18, 11, 7),
    DOCTOR_BRIEFS(4, 9, 13, 25, 4, 21, 13),
    KORIN(6, 9, 13, 13, 25, 15, 9),
    BABA(6, 10, 13, 10, 23, 25, 3),
    ANDROID_16(18, 13, 10, 25, 10, 18, 13),
    DENDE(10, 25, 10, 13, 25, 7, 15),
    SHENRON(14, 14, 14, 14, 25, 14, 14),
    OX_KING(11, 16, 11, 13, 8, 19, 11),
    MARRON(8, 11, 16, 19, 5, 13, 19),
    ANDROID_18(16, 25, 7, 25, 3, 25, 9),
    VIDEL(12, 25, 25, 10, 8, 12, 12),
    W_T_ANNOUNCER(6, 15, 9, 15, 9, 25, 12),
    SUPREME_KAI(15, 25, 10, 9, 25, 12, 9),
    KIBITO(14, 16, 12, 12, 25, 14, 12),
    GOTEN(25, 11, 17, 11, 17, 5, 14),
    TRUNKS_KID(25, 9, 17, 17, 14, 6, 12),
    CAPTAIN_GINYU(16, 11, 25, 11, 16, 13, 13),
    MR_SATAN(1, 25, 1, 1, 1, 25, 1),
    PUPPY(10, 10, 10, 10, 14, 14, 25),
    MAJIN_BUU(25, 25, 14, 7, 25, 10, 14),
    SUNO(9, 16, 7, 12, 12, 9, 25),
    BORA(11, 11, 11, 11, 16, 16, 14),
    UPA(13, 11, 11, 11, 16, 13, 16),
    ELDER_KAI(11, 10, 11, 10, 25, 25, 10),
    ARALE(25, 8, 25, 25, 12, 8, 17),
    GATCHAN(15, 18, 13, 20, 15, 5, 25),
    SENBEI(8, 25, 13, 25, 10, 17, 13),
    PORUNGA(15, 15, 15, 15, 25, 15, 15),
    ANDROID_17(20, 10, 10, 25, 8, 13, 25),
    BONYU(15, 19, 25, 19, 7, 25, 11),
    CELL(25, 9, 25, 25, 14, 14, 9),
    FRIEZA(25, 10, 25, 25, 12, 8, 15),
    DABURA(17, 13, 25, 10, 15, 17, 13),
    NAPPA(16, 16, 25, 8, 10, 16, 16),
    RADITZ(14, 11, 16, 16, 16, 11, 16),
    RECOOME(17, 11, 19, 11, 11, 14, 17),
    BURTER(17, 14, 19, 17, 11, 11, 11),
    JEICE(17, 17, 19, 11, 11, 11, 14),
    GULDO(16, 14, 19, 11, 14, 16, 11),
    APPULE(12, 17, 17, 14, 14, 14, 12),
    ZARBON(16, 13, 25, 13, 10, 16, 13),
    DODORIA(16, 13, 25, 10, 13, 18, 10),
    CUI(11, 14, 19, 16, 11, 16, 14),
    CELL_JR(14, 12, 20, 20, 17, 6, 12),
    KING_COLD(13, 11, 25, 13, 8, 25, 15),
    BABIDI(10, 8, 16, 25, 18, 13, 10),
    YAMU(11, 11, 25, 11, 11, 13, 11),
    SPOPOVICH(11, 11, 25, 11, 11, 13, 11),
    PUI_PUI(12, 10, 25, 15, 12, 15, 12),
    YAKON(12, 10, 25, 12, 15, 12, 15),
    ANDROID_20(16, 10, 13, 25, 10, 22, 10),
    ANDROID_19(17, 15, 1, 25, 9, 12, 15),
    TRUNKS_WOHS(25, 3, 23, 25, 23, 8, 15),
    GOHAN_WOHS(25, 12, 29, 3, 19, 25, 15),
    BARDOCK(25, 11, 25, 9, 11, 14, 25),
    SHUGESH(15, 24, 15, 9, 9, 15, 18),
    BORGOS(17, 12, 17, 12, 12, 15, 17),
    TORA(16, 12, 16, 12, 6, 22, 22),
    FASHA(21, 9, 15, 12, 9, 18, 21),
    GRANDPA_GOHAN(12, 21, 15, 3, 25, 25, 9),
    YOUNG_VEGETA(25, 25, 25, 12, 7, 12, 14),
    YOUNG_NAPPA(16, 16, 25, 8, 10, 16, 16);

    private final int zBonus;
    private final int cookBonus;
    private final int trainBonus;
    private final int devBonus;
    private final int godBonus;
    private final int adultBonus;
    private final int adventureBonus;

    private final Map<BoardName, Integer> bonusesByBoard;

    private final BoardName homeBoard;

    Emblem(int zBonus, int cookBonus, int trainBonus, int devBonus, int godBonus, int adultBonus, int adventureBonus) {
        this(zBonus, cookBonus, trainBonus, devBonus, godBonus, adultBonus, adventureBonus, null);
    }

    Emblem(int zBonus, int cookBonus, int trainBonus, int devBonus, int godBonus, int adultBonus, int adventureBonus, BoardName homeBoard) {
        this.zBonus = zBonus;
        this.cookBonus = cookBonus;
        this.trainBonus = trainBonus;
        this.devBonus = devBonus;
        this.godBonus = godBonus;
        this.adultBonus = adultBonus;
        this.adventureBonus = adventureBonus;

        bonusesByBoard = Map.of(BoardName.ZWARRIOR, zBonus, BoardName.COOKING, cookBonus, BoardName.TRAINING, trainBonus,
                BoardName.DEVELOPMENT, devBonus, BoardName.GODS, godBonus, BoardName.ADULT, adultBonus, BoardName.ADVENTURE, adventureBonus);
        this.homeBoard = homeBoard;
    }

    public static List<Emblem> getAsList() {
        return Arrays.asList(values());
    }

    public static Emblem getCenterForBoard(BoardName board) {
        return getAsList().stream().filter(e -> e.homeBoard == board).findFirst().orElse(null);
    }

    public static Set<Emblem> getNonCenterEmblems() {
        return getAsList().stream().filter(e -> e.homeBoard == null).collect(Collectors.toSet());
    }

    public String normalizeName() {
        return StringUtils.capitalize(name().replace("_", " ").toLowerCase());
    }

    public String fileName() {
        return normalizeName().replaceAll(" ", "") + "_se.png";
    }

    public int getBonusForBoard(BoardName boardName) {
        return bonusesByBoard.get(boardName);
    }

    @Override
    public String toString() {
        return name();
    }
}

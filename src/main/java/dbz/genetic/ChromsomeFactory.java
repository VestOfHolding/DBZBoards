package dbz.genetic;

import dbz.domain.BoardName;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class ChromsomeFactory {

    private static Set<Chromosome> allChromosomes;

    public static void init() {
        allChromosomes = new HashSet<>();
        BoardManager boardManager = new BoardManager();

        for (BoardName boardName : BoardName.values()) {
            IntStream.range(1, boardManager.getBoardSize(boardName)).forEach(i -> allChromosomes.add(new Chromosome(i, boardName)));
        }
    }

    public static Set<Chromosome> getAllChromosomes() {
        if (CollectionUtils.isEmpty(allChromosomes)) {
            init();
        }
        return new HashSet<>(allChromosomes);
    }
}

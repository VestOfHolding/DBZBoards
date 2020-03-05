package dbz.genetic;

import dbz.domain.BoardName;
import dbz.domain.Emblem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Gene {
    @Getter
    private Emblem emblem;

    @Getter
    @Setter
    private Chromosome chromosome;

    public BoardName getBoard() {
        return chromosome.getBoard();
    }

    public int getSpaceId() {
        return chromosome.getSpaceId();
    }
}

package dbz.genetic;

import dbz.domain.BoardName;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Chromosome {
    private int spaceId;

    private BoardName board;
}

package dbz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardName {
    ZWARRIOR(11, 276),
    COOKING(15, 375),
    TRAINING(23, 575),
    DEVELOPMENT(12, 300),
    GODS(14, 350),
    ADULT(9, 225),
    ADVENTURE(12, 300);

    private final int size;

    private final int targetScore;
}

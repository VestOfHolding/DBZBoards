package dbz.board.layout;

import dbz.domain.Emblem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmblemSpace {
    private Emblem emblem;

    private int spaceId;

    private boolean isLocked;

    public EmblemSpace(Emblem emblem, int spaceId) {
        this.emblem = emblem;
        this.spaceId = spaceId;
        isLocked = false;
    }

    public EmblemSpace(int spaceId) {
        this.spaceId = spaceId;
    }

    public void setEmblem(Emblem emblem) {
        if (!isLocked) {
            this.emblem = emblem;
        }
    }

    public String toString() {
        return emblem == null ? "NULL" + spaceId : emblem.name();
    }
}

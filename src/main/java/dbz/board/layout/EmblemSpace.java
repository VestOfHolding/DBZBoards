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

    private int x;
    private int y;

    public EmblemSpace(Emblem emblem, int spaceId, int x, int y) {
        this.emblem = emblem;
        this.spaceId = spaceId;
        isLocked = false;
        this.x = x;
        this.y = y;
    }

    public EmblemSpace(Emblem emblem, int spaceId, boolean isLocked) {
        this(emblem, spaceId, isLocked, 0, 0);
    }

    public EmblemSpace(Emblem emblem, int spaceId) {
        this(emblem, spaceId, 0, 0);
    }

    public EmblemSpace(int spaceId, int x, int y) {
        this(null, spaceId, x, y);
    }

    public EmblemSpace(int spaceId) {
        this(null, spaceId);
    }

    public void setEmblem(Emblem emblem) {
        if (!isLocked) {
            this.emblem = emblem;
        }
    }

    public void scaleCoordinates(double scaleFactor) {
        x = (int)((double)x * scaleFactor);
        y = (int)((double)y * scaleFactor);
    }

    public String toString() {
        return emblem == null ? "NULL" + spaceId : emblem.name();
    }
}

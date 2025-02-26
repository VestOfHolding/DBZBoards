package dbz.board.layout;

import dbz.domain.Emblem;
import org.jgrapht.graph.DefaultEdge;

public class DBZEdge extends DefaultEdge {
    private final EmblemSpace source;
    private final EmblemSpace target;

    public DBZEdge() {
        this.source = new EmblemSpace(0);
        this.target = new EmblemSpace(0);
    }

    public DBZEdge(EmblemSpace source, EmblemSpace target) {
        super();
        this.source = source;
        this.target = target;
    }

    @Override
    public EmblemSpace getSource() {
        return source;
    }

    @Override
    public EmblemSpace getTarget() {
        return target;
    }

    public void updateSourceEmblem(Emblem source) {
        this.source.setEmblem(source);
    }

    public void updateTargetEmblem(Emblem target) {
        this.target.setEmblem(target);
    }

    @Override
    public String toString() {
        return "(" + source + " -> " + target + ")";
    }
}

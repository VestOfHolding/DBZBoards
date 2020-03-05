package dbz.board.layout;

import org.jgrapht.graph.DefaultEdge;

public class DBZEdge extends DefaultEdge {
    @Override
    public EmblemSpace getSource() {
        return (EmblemSpace)super.getSource();
    }

    @Override
    public EmblemSpace getTarget() {
        return (EmblemSpace)super.getTarget();
    }
}

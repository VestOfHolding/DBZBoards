package dbz.visualize;

import dbz.LinkBonus;
import dbz.LinkBonusFactory;
import dbz.board.BaseBoard;
import dbz.board.layout.DBZEdge;
import dbz.board.layout.EmblemSpace;
import dbz.domain.BoardName;
import dbz.domain.Emblem;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.LinLog;
import org.graphstream.ui.view.Viewer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static dbz.Utils.getEmblems;

public class GraphMaker {

    public void display() {
        System.setProperty("org.graphstream.ui", "swing");

        LinkBonusFactory linkFactory = new LinkBonusFactory();

        List<Emblem> emblems = Emblem.getAsList();

        Set<Emblem> emblemSubset = getEmblems(emblems, Emblem::getZBonus);
        BoardName currentBoard = BoardName.ZWARRIOR;

        Graph gsGraph = new SingleGraph("DBZ Kakarot Emblems Connected", false, true);
        gsGraph.setAttribute("ui.stylesheet", getStyleSheet());

        Set<LinkBonus> linkBonuses = getLinkBonuses(linkFactory, emblemSubset, currentBoard);

        drawLinksAsGroupNodes(linkBonuses, gsGraph);

        Layout layout = new LinLog();
        layout.setQuality(3.9);
        layout.setStabilizationLimit(0.9);
        layout.setForce(0.9f);

        Viewer viewer = gsGraph.display();
        viewer.enableAutoLayout(layout);
    }

    public void displayBoard(BaseBoard board) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph gsGraph = new SingleGraph("DBZ Kakarot Emblems Connected", false, true);
        gsGraph.setAttribute("ui.stylesheet", getStyleSheet());

        for (EmblemSpace emblemSpace : board.getBoard().vertexSet()) {
            Node node = gsGraph.addNode(Integer.toString(emblemSpace.getSpaceId()));
            node.setAttribute("ui.class",
                    emblemSpace.getEmblem() == null ? "group" : emblemSpace.getEmblem().normalizeName());
        }

        int edgeId = 0;
        for (DBZEdge edge : board.getBoard().edgeSet()) {
            gsGraph.addEdge(Integer.toString(edgeId++), edge.getSource().getSpaceId(), edge.getTarget().getSpaceId());
        }

        Layout layout = new LinLog();
        layout.setQuality(3.9);
        layout.setStabilizationLimit(0.9);
        layout.setForce(0.9f);

        Viewer viewer = gsGraph.display();
        viewer.enableAutoLayout(layout);
    }

    private void drawLinksAsClique(Set<LinkBonus> linkBonuses, Graph gsGraph) {
        int edgeId = 0;

        for (LinkBonus linkBonus : linkBonuses) {

            for (Emblem firstEmblem : linkBonus.getLinkMembers()) {

                if (gsGraph.getNode(firstEmblem.normalizeName()) == null) {
                    Node node = gsGraph.addNode(firstEmblem.normalizeName());
                    node.setAttribute("ui.class", firstEmblem.normalizeName());
                }

                for (Emblem secondEmblem : linkBonus.getLinkMembers()) {
                    if (firstEmblem == secondEmblem) {
                        continue;
                    }

                    if (gsGraph.getNode(secondEmblem.normalizeName()) == null) {
                        Node node = gsGraph.addNode(secondEmblem.normalizeName());
                        node.setAttribute("ui.class", secondEmblem.normalizeName());
                    }

                    gsGraph.addEdge(Integer.toString(edgeId++), firstEmblem.normalizeName(), secondEmblem.normalizeName());
                }
            }
        }
    }

    private void drawLinksAsGroupNodes(Set<LinkBonus> linkBonuses, Graph gsGraph) {
        int edgeId = 0;
        int groupId = 0;

        for (LinkBonus linkBonus : linkBonuses) {

            Node groupNode = gsGraph.addNode("G" + (groupId++));
            groupNode.setAttribute("ui.class", "group");

            for (Emblem firstEmblem : linkBonus.getLinkMembers()) {

                Node node;
                if ((node = gsGraph.getNode(firstEmblem.normalizeName())) == null) {
                    node = gsGraph.addNode(firstEmblem.normalizeName());
                    node.setAttribute("ui.class", firstEmblem.normalizeName());
                }

                gsGraph.addEdge(Integer.toString(edgeId++), groupNode, node);
            }
        }
    }

    private void drawLinksAsLine(Set<LinkBonus> linkBonuses, Graph gsGraph) {
        int edgeId = 0;

        Emblem firstEmblem = null;
        Emblem lastEmblem = null;

        for (LinkBonus linkBonus : linkBonuses) {
            firstEmblem = null;
            lastEmblem = null;
            Emblem previousEmblem = null;

            for (Emblem currentEmblem : linkBonus.getLinkMembers()) {
                if (gsGraph.getNode(currentEmblem.normalizeName()) == null) {

                    Node node = gsGraph.addNode(currentEmblem.normalizeName());
                    node.setAttribute("ui.class", currentEmblem.normalizeName());
                }

                if (firstEmblem == null) {
                    firstEmblem = currentEmblem;
                }

                if (previousEmblem != null) {
                    gsGraph.addEdge(Integer.toString(edgeId++), previousEmblem.normalizeName(), currentEmblem.normalizeName());
                }
                previousEmblem = currentEmblem;
                lastEmblem = currentEmblem;
            }
        }

        if (firstEmblem != null && firstEmblem != lastEmblem) {
            gsGraph.addEdge(Integer.toString(edgeId), firstEmblem.normalizeName(), lastEmblem.normalizeName());
        }
    }

    private Set<LinkBonus> getLinkBonuses(LinkBonusFactory linkFactory, Set<Emblem> emblemSubset, BoardName currentBoard) {
        Set<Emblem> emblems = linkFactory.linksContainingAnyEmblems(emblemSubset, currentBoard).stream()
                .flatMap(link -> link.getLinkMembers().stream())
                .collect(Collectors.toSet());

        return linkFactory.linksContainingAllEmblems(emblems);
    }

    public String getStyleSheet() {
        StringBuilder css = new StringBuilder("node {\n" +
                "\tsize: 50px;\n" +
                "\tfill-mode: image-scaled-ratio-max;\n}\n" +
                "node.group {\n" +
                "\tfill-mode: plain;\n" +
                "\tfill-color: green;\n" +
                "\tsize: 20px;\n" +
                "}\n");

        for (Emblem emblem : Emblem.getAsList()) {
            css.append("node.")
                    .append(emblem.normalizeName())
                    .append(" {\n\tfill-image: url('")
                    .append(emblem.fileName())
                    .append("');\n}\n");
        }

        css.append("edge {\n\tstroke-mode: plain;\n\tstroke-color: #999;\n\tstroke-width: 2px;\n}");
        return css.toString();
    }
}

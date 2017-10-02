package gentree.client.desktop.configuration.helper;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vanilka on 21/11/2016.
 */
public class BorderPaneReloadHelper {

    private List<ObjectProperty<Node>> permutation;

    public void before(BorderPane pane) {
        // get pane properties sorted by
        final List<Node> children = pane.getChildren();
        permutation = Arrays.asList(pane.topProperty(), pane.leftProperty(), pane.bottomProperty(), pane.rightProperty(), pane.centerProperty());
        permutation.sort(Comparator.comparingInt(p -> children.indexOf(p.get())));
    }

    public void after(BorderPane pane) {
        // before and after have to be called with the same argument
        if (permutation == null || permutation.get(0).getBean() != pane) {
            throw new IllegalStateException("no corresponding before call");
        }

        Node[] nodes = permutation.stream().map(ObjectProperty::get).toArray(Node[]::new);
        pane.getChildren().removeAll(nodes);
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            if (node != null) {
                permutation.get(i).set(node);
            }
        }

        // restore initial state to allow reuse of class
        permutation = null;
    }

}

package gentree.client.visualization;

import gentree.client.visualization.elements.configuration.AutoCleanable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.util.List;

/**
 * Created by vanilka on 06/01/2018.
 */
public class CleanThread extends Thread {

    private HBox n = new HBox();

    public CleanThread(HBox node) {
        List<Node> list = node.getChildren();
        node.getChildren().clear();
        this.n.getChildren().addAll(list);
    }

    @Override
    public void run() {
        n.getChildren().forEach(child -> {
            if(child instanceof AutoCleanable) ((AutoCleanable) child).clean();
        });
        Thread.currentThread().interrupt();
    }
}

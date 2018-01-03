package gentree.client.visualization;

import gentree.client.visualization.elements.configuration.AutoCleanable;
import javafx.concurrent.Task;
import javafx.scene.Node;

/**
 * Created by vanilka on 03/01/2018.
 */
public class CleanTask extends Task {

    private Node n;

    public CleanTask(Node node) {
        this.n = node;
    }


    @Override
    protected Object call() throws Exception {
        if (n instanceof AutoCleanable) ((AutoCleanable) n).clean();
        return null;
    }
}

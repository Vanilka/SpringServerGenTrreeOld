package gentree.client.visualization.gustave.panels;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import lombok.Getter;

/**
 * Created by Martyna SZYMKOWIAK on 04/09/2017.
 */
public abstract class SubRelationPane extends SubBorderPane {

    @Getter
    protected final HBox childrenBox;

    SubRelationPane() {
        childrenBox = new HBox();
    }


    public Node getConnectionNode() {
        return null;
    }

}

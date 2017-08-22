package gentree.client.desktop.controllers.tree_elements.connectors;

import gentree.client.desktop.controllers.tree_elements.panels.PanelChild;
import gentree.client.desktop.controllers.tree_elements.panels.SubBorderPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 20/08/2017.
 */
@Getter
@Setter
public class ChildConnector {

    private final SubBorderPane subBorderPane;
    private final PanelChild panelChild;

    private final ObjectProperty<Bounds> boundsObjectProperty;

    {
        boundsObjectProperty = new SimpleObjectProperty<>();
    }

    public ChildConnector(PanelChild child, SubBorderPane subBorderPane) {
        this.panelChild = child;
        this.subBorderPane = subBorderPane;

        boundsObjectProperty.setValue(getRelativeBounds(panelChild.getPanelSingle().getValue().getMember()));

    }


    private  Bounds getRelativeBounds(Node node) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return subBorderPane.sceneToLocal(nodeBoundsInScene);
    }


}

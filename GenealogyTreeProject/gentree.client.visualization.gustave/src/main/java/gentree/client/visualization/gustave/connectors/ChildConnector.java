package gentree.client.visualization.gustave.connectors;

import gentree.client.visualization.gustave.panels.PanelChild;
import gentree.client.visualization.gustave.panels.SubRelationPane;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 20/08/2017.
 *
 * Class responsible to drawing Vertical Line from Child
 */
@Getter
@Setter
public class ChildConnector extends LineConnector  {

    private static final Double CHILD_CONNECTOR_HEIGHT = 100.0;
    private final SubRelationPane subBorderPane;
    private final PanelChild panelChild;


    public ChildConnector(PanelChild child, SubRelationPane subBorderPane) {
        super();
        this.panelChild = child;
        this.subBorderPane = subBorderPane;
        initLine();

    }


    public void removeLine() {
        subBorderPane.getChildren().remove(getLine());
    }

    private void initLine() {

        if (panelChild != null && panelChild.getPanelSingle() != null) {
            removeLine();
            subBorderPane.getChildren().add(line.get());

        } else {
            removeLine();
        }
        initLineListeners();

    }

    private void initLineListeners() {

        subBorderPane.getChildrenBox().boundsInParentProperty().addListener((obs, oldValue, newValue) -> {
            redrawLine();
        });

        panelChild.boundsInParentProperty().addListener((obs, oldValue, newValue) -> {
            redrawLine();
        });
    }

    private void redrawLine() {
        Bounds childBounds = getRelativeBounds(panelChild.getPanelSingle().get().getMember());
        Point2D startPoint = getTopPoint(childBounds);
        Point2D endPoint = new Point2D(startPoint.getX(), startPoint.getY() - CHILD_CONNECTOR_HEIGHT );

        getLine().setStartX(startPoint.getX());
        getLine().setStartY(startPoint.getY());
        getLine().setEndX(endPoint.getX());
        getLine().setEndY(endPoint.getY());
    }

    private Bounds getRelativeBounds(Node node) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        Bounds result = subBorderPane.sceneToLocal(nodeBoundsInScene);
        return result;
    }
}

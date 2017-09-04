package gentree.client.desktop.controllers.tree_elements.connectors;

import gentree.client.desktop.controllers.tree_elements.panels.PanelChild;
import gentree.client.desktop.controllers.tree_elements.panels.PanelRelationCurrent;
import gentree.client.desktop.controllers.tree_elements.panels.SubBorderPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 20/08/2017.
 *
 * Class responsible to drawing Vertical Line from Child
 */
@Getter
@Setter
public class ChildConnector extends LineConnector {

    private static final Double CHILD_CONNECTOR_HEIGHT = 100.0;
    private final SubBorderPane subBorderPane;
    private final PanelChild panelChild;


    public ChildConnector(PanelChild child, SubBorderPane subBorderPane) {
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
            redrawLine();
            subBorderPane.getChildren().add(line.get());

        } else {
            removeLine();
        }
        initLineListeners();

    }

    private void initLineListeners() {
        panelChild.getPanelSingle().get().getMember().boundsInParentProperty().addListener(observable -> {
            redrawLine();
        });


        panelChild.getPanelSingle().get().getMember().boundsInLocalProperty().addListener(observable -> {
            redrawLine();
        });

        subBorderPane.boundsInLocalProperty().addListener(observable -> {
            redrawLine();
        });

        subBorderPane.boundsInParentProperty().addListener(observable -> {
            redrawLine();
        });

        panelChild.boundsInParentProperty().addListener(observable -> {
            redrawLine();
        });

        panelChild.boundsInLocalProperty().addListener(observable -> {
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

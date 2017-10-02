package gentree.client.visualization.gustave.connectors;

import gentree.client.visualization.gustave.configuration.ChildrenConnectorsGuard;
import gentree.client.visualization.gustave.panels.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import lombok.Getter;

import java.util.Comparator;

/**
 * Created by Martyna SZYMKOWIAK on 20/08/2017.
 */
public class ParentToChildrenConnector extends LineConnector {

    /*
    *  Parent pane for this Connector
    */
    private SubRelationPane subBorderPane;

    /*
    *   Child Connectors for children in Sub-Relation-Pane
    */
    private final ObservableList<ChildConnector> list = FXCollections.observableArrayList();
    private final ChildrenConnectorsGuard childrenConnectorsGuard = new ChildrenConnectorsGuard(list);

    @Getter
    private ObjectProperty<Line> withNodeConnector = new SimpleObjectProperty<>(new Line());

    /**
     * SubRelationPane element is the parent for this Connector
     * @param subBorderPane
     */
    public ParentToChildrenConnector(SubRelationPane subBorderPane) {
        super();
        this.subBorderPane = subBorderPane;
        initLineProperties(withNodeConnector.get());
        initListeners();
    }

    private void initListeners() {

        initListListener();

    }

    private void initListListener() {
        list.addListener((ListChangeListener<ChildConnector>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (ChildConnector childConnector : c.getAddedSubList()) {
                        childrenConnectorsGuard.addObserverTo(childConnector);
                    }
                }
                if (c.wasRemoved()) {
                    c.getRemoved().forEach(ChildConnector::removeLine);
                }
            }
        });

        childrenConnectorsGuard.getFirstChild().addListener(c -> {
            System.out.println("newFirst Child " +childrenConnectorsGuard.getFirstChild());
            drawLine();
        });

        childrenConnectorsGuard.getLastChild().addListener(c -> {
            System.out.println("newLast Child " +childrenConnectorsGuard.getLastChild());
            drawLine();
        });
    }


    public void addPanelChild(PanelChild child) {
        list.add(new ChildConnector(child, subBorderPane));
    }

    public void removePanelChild(PanelChild child) {
        list.removeIf(p -> p.getPanelChild().equals(child));
    }

    public void drawChildrenConnectors() {
        drawLine();
    }

    /**
     *  Drawing horizontal line beetween children
     */
    private void drawLine() {
        if (list.size() > 0) {
            subBorderPane.getChildren().remove(getLine());
            if (childrenConnectorsGuard.getFirstChild().get() != null && childrenConnectorsGuard.getLastChild().get() != null) {

                Line start = childrenConnectorsGuard.getFirstChild().get().getLine();
                Line end = childrenConnectorsGuard.getLastChild().get().getLine();
                getLine().setStartX(start.getEndX());
                getLine().setStartY(start.getEndY());
                getLine().setEndX(end.getEndX());
                getLine().setEndY(end.getEndY());

                subBorderPane.getChildren().add(0, getLine());
            }
        }
    }

    /**
     * Drawing line beetween parent/ relation and connection point
     *
     * @param n
     */

    public void connectRelationToChildren(Node n) {
        if (list.size() != 0) {
            if (subBorderPane instanceof PanelRelationCurrent) {
                connectToLeft(n);
            } else if (subBorderPane instanceof PanelRelationEx) {
                connectToRight(n);
            } else {
                connectToCenter(n);
            }
        }
    }

    private void connectToLeft(Node n) {


        drawConnector(n, childrenConnectorsGuard.getFirstChild().get().getLine().getEndX(), childrenConnectorsGuard.getFirstChild().get().getLine().getEndY() );
    }

    private void connectToRight(Node n) {

        drawConnector(n,childrenConnectorsGuard.getLastChild().get().getLine().getEndX(),childrenConnectorsGuard.getLastChild().get().getLine().getEndY() );
    }

    private void connectToCenter(Node n) {
            Double middle = (childrenConnectorsGuard.getFirstChild().get().getLine().getEndX() + childrenConnectorsGuard.getLastChild().get().getLine().getEndX()) / 2;
            drawConnector(n, middle, getLine().getStartY());
    }

    /**
     * Drawing line beetween Node and children point
     * @param n
     * @param startX
     * @param startY
     */
    private void drawConnector(Node n, Double startX, Double startY) {
        subBorderPane.getChildren().remove(withNodeConnector.get());

        Bounds b = getRelativeBounds(n);
        Point2D bottomPoint = getBottomPoint(b);

        withNodeConnector.get().setStartX(startX);
        withNodeConnector.get().setStartY(startY);
        withNodeConnector.get().setEndX(bottomPoint.getX());
        withNodeConnector.get().setEndY(bottomPoint.getY());

        subBorderPane.getChildren().add(1, withNodeConnector.get());
        initLineProperties(withNodeConnector.get());
    }


    protected Bounds getRelativeBounds(Node node) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return subBorderPane.sceneToLocal(nodeBoundsInScene);
    }

    protected Bounds getRelativeBounds(Node node, Node relativeTo) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return relativeTo.sceneToLocal(nodeBoundsInScene);
    }
}

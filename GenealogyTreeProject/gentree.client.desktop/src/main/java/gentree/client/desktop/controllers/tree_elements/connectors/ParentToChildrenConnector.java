package gentree.client.desktop.controllers.tree_elements.connectors;

import gentree.client.desktop.controllers.tree_elements.panels.PanelChild;
import gentree.client.desktop.controllers.tree_elements.panels.PanelRelationCurrent;
import gentree.client.desktop.controllers.tree_elements.panels.PanelRelationEx;
import gentree.client.desktop.controllers.tree_elements.panels.SubBorderPane;
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

    private SubBorderPane subBorderPane;
    private ObservableList<ChildConnector> list = FXCollections.observableArrayList();
    private ObjectProperty<ChildConnector> firstChild = new SimpleObjectProperty<>();
    private ObjectProperty<ChildConnector> lastChild = new SimpleObjectProperty<>();

    @Getter
    private ObjectProperty<Line> withNodeConnector = new SimpleObjectProperty<>(new Line());

    public ParentToChildrenConnector(SubBorderPane subBorderPane) {
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
                    populateFirstAndLastChild();
                    drawLine();
                    for (ChildConnector childConnector : c.getAddedSubList()) {
                        populateFirstAndLastChild();

                        childConnector.getLine().boundsInParentProperty().addListener(observable -> {
                            drawLine();
                        });

                        childConnector.getLine().startXProperty().addListener(observable -> {
                            drawLine();
                        });

                        childConnector.getLine().endXProperty().addListener(observable -> {
                            drawLine();
                        });

                        childConnector.getLine().startYProperty().addListener(observable -> {
                            drawLine();
                        });

                        childConnector.getLine().endYProperty().addListener(observable -> {
                            drawLine();
                        });
                    }
                }
                if (c.wasRemoved()) {
                    c.getRemoved().forEach(ChildConnector::removeLine);
                }
            }
        });
    }

    private void populateFirstAndLastChild() {
        if (!list.isEmpty()) {
            firstChild.setValue(list.stream().min(Comparator.comparingDouble(value -> value.getLine().getStartX())).get());
            lastChild.setValue(list.stream().max(Comparator.comparingDouble(value -> value.getLine().getStartX())).get());
        } else {
            firstChild.setValue(null);
            lastChild.setValue(null);
        }
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
        populateFirstAndLastChild();
        if (list.size() > 0) {
            subBorderPane.getChildren().remove(getLine());
            if (firstChild.get() != null && lastChild.get() != null) {

                Line start = firstChild.get().getLine();
                Line end = lastChild.get().getLine();
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
        drawConnector(n, firstChild.get().getLine().getEndX(), firstChild.get().getLine().getEndY() );
    }

    private void connectToRight(Node n) {
        drawConnector(n,lastChild.get().getLine().getEndX(),lastChild.get().getLine().getEndY() );
    }

    private void connectToCenter(Node n) {
            Double middle = (firstChild.get().getLine().getEndX() + lastChild.get().getLine().getEndX()) / 2;
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

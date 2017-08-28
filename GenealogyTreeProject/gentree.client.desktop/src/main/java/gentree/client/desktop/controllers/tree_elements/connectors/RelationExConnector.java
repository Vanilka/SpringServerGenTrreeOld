package gentree.client.desktop.controllers.tree_elements.connectors;

import gentree.client.desktop.controllers.tree_elements.panels.PanelChild;
import gentree.client.desktop.controllers.tree_elements.panels.PanelRelationCurrent;
import gentree.client.desktop.controllers.tree_elements.panels.PanelRelationEx;
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
 * Created by Martyna SZYMKOWIAK on 25/08/2017.
 */
public class RelationExConnector extends LineConnector {

    private PanelRelationEx subBorderPane;
    private ObservableList<ChildConnector> list = FXCollections.observableArrayList();
    private ObjectProperty<ChildConnector> firstChild = new SimpleObjectProperty<>();
    private ObjectProperty<ChildConnector> lastChild = new SimpleObjectProperty<>();

    @Getter
    private ObjectProperty<Line> withNodeConnector = new SimpleObjectProperty<>(new Line());

    public RelationExConnector(PanelRelationEx subBorderPane) {
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
                    for (ChildConnector childc : c.getAddedSubList()) {
                        populateFirstAndLastChild();

                        childc.getLine().boundsInParentProperty().addListener(observable -> {
                            drawLine();
                        });

                        childc.getLine().startXProperty().addListener(observable -> {
                            drawLine();
                        });

                        childc.getLine().endXProperty().addListener(observable -> {
                            drawLine();
                        });

                        childc.getLine().startYProperty().addListener(observable -> {
                            drawLine();
                        });

                        childc.getLine().endYProperty().addListener(observable -> {
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

                subBorderPane.getChildren().add(getLine());
            }
        }
    }

    public void connectRelationToChildren(Node n) {
        if (list.size() != 0) {
            subBorderPane.getChildren().remove(withNodeConnector.get());
            Bounds b = getRelativeBounds(n);
            Point2D bottomPoint = getBottomPoint(b);

            withNodeConnector.get().setStartX(lastChild.get().getLine().getEndX());
            withNodeConnector.get().setStartY(lastChild.get().getLine().getEndY());
            withNodeConnector.get().setEndX(bottomPoint.getX());
            withNodeConnector.get().setEndY(bottomPoint.getY());

            subBorderPane.getChildren().add(withNodeConnector.get());
            initLineProperties(withNodeConnector.get());
        }
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

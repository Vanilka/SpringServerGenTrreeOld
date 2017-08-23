package gentree.client.desktop.controllers.tree_elements.connectors;

import gentree.client.desktop.controllers.tree_elements.panels.PanelChild;
import gentree.client.desktop.controllers.tree_elements.panels.PanelRelationCurrent;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
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
import java.util.Optional;

/**
 * Created by Martyna SZYMKOWIAK on 20/08/2017.
 */
public class RelationCurrentConnector extends LineConnector {

    private PanelRelationCurrent subBorderPane;
    private ObservableList<ChildConnector> list = FXCollections.observableArrayList();
    private ObjectProperty<Point2D> middle = new SimpleObjectProperty<>(new Point2D(0, 0));

    @Getter
    private ObjectProperty<Line> withNodeConnector = new SimpleObjectProperty<>(new Line());

    public RelationCurrentConnector(PanelRelationCurrent subBorderPane) {
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
                    drawLine();

                    for (ChildConnector childc : c.getAddedSubList()) {
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

        subBorderPane.getChildren().remove(getLine());

        Optional<ChildConnector> opStart = list.stream().min(Comparator.comparingDouble(value -> value.getLine().getStartX()));
        Optional<ChildConnector> opEnd = list.stream().max(Comparator.comparingDouble(value -> value.getLine().getStartX()));

        if (opStart.isPresent() && opEnd.isPresent()) {
            Line start = opStart.get().getLine();
            Line end = opEnd.get().getLine();
            getLine().setStartX(start.getEndX());
            getLine().setStartY(start.getEndY());
            getLine().setEndX(end.getEndX());
            getLine().setEndY(end.getEndY());

            //To jest punkt wyznaczający srodek poziomej linii
            middle.set(new Point2D((getLine().getStartX() + getLine().getEndX()) / 2, getLine().getStartY()));

            subBorderPane.getChildren().add(getLine());
        }
    }

    public void connectNodeWithMiddle(Node n) {
        if (list.size() != 0) {
            subBorderPane.getChildren().remove(withNodeConnector.get());
            Bounds b = getRelativeBounds(n);
            Point2D bottomPoint = getBottomPoint(b);

            withNodeConnector.get().setStartX(getMiddle().getX());
            withNodeConnector.get().setStartY(getMiddle().getY());
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


    public Point2D getMiddle() {
        return middle.get();
    }

    public ReadOnlyProperty<Point2D> middleProperty() {
        return middle;
    }


}

package genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import genealogytree.client.desktop.controllers.implementation.custom.GTPanel;
import genealogytree.client.desktop.controllers.implementation.custom.GTPanelSim;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 14/05/2017.
 */
@Getter
@Setter
public class GTPanelSignle extends GTPanelCurrent implements GTPanelSim, GTPanel {


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private HBox childrenbox;


    private ObjectProperty<GTLeaf> leaf;
    private ObservableList<GTPanelChild> childrenPanelList;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Line> connectors;


    public GTPanelSignle(GTLeaf leaf, GTPanel parent) {
        init();
        this.leaf.setValue(leaf);
        this.parentPanel = parent;
        setTop(leaf);
        setCenter(childrenbox);
        setAlignment(leaf, Pos.CENTER);
        resize(400, 500);
    }

    @Override
    public void setGTPanelChild(GTPanelChild panelChild) {
        childrenPanelList.add(panelChild);
    }

    @Override
    public void setGTPanelChildList(ObservableList<GTPanelChild> panelChildList) {
        childrenPanelList.addAll(panelChildList);
    }

    private void init() {
        connectors = new ArrayList<>();
        leaf = new SimpleObjectProperty<>();
        childrenPanelList = FXCollections.observableArrayList();
        initNodes();
        initListeners();
    }

    private void initNodes() {
        childrenbox = new HBox();
        childrenbox.setSpacing(50);
        autoresize();
    }

    private void initListeners() {
        childrenPanelList.addListener((ListChangeListener<? super GTPanelChild>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    childrenbox.getChildren().addAll(c.getAddedSubList());
                    c.getAddedSubList().forEach(this::initListenerChildLeaf);


                } else if (c.wasRemoved()) {
                    childrenbox.getChildren().removeAll(c.getRemoved());

                } else {

                }
            }
        });

        leaf.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                boundListener();
            }
        });

    }

    private void initListenerChildLeaf(GTPanelChild child) {
        drawLines();
        child.needsLayoutProperty().addListener((observable, oldValue, newValue) -> {
            drawLines();
        });
        child.returnLeaf().addListener((observable, oldValue, newValue) -> {
            drawLines();
        });

    }


    private void autoresize() {
        this.prefWidthProperty().bind(childrenbox.widthProperty());
        childrenbox.maxHeight(Double.MAX_VALUE);
        childrenbox.maxWidth(Double.MAX_VALUE);
    }

    private void drawLines() {
        if (leaf.get() != null) {
            this.getChildren().removeAll(connectors);
            connectors.clear();
            /*
                init Bound fo this Leaf
             */
            Bounds thisBound = getRelativeBounds(leaf.getValue());
            Point2D bottomPoint = getBottomPoint(thisBound);
            List<Bounds> childrenBounds = new ArrayList<>();

            /*
                Create Bounds of children
             */
            childrenPanelList.forEach(c -> {
                childrenBounds.add(getRelativeBounds(c.returnLeaf().get()));
            });

            /*
                Foreach child bounds create point2 ( top point)  -> start point for connector
             */
            List<Point2D> point2DList = new ArrayList<>();
            childrenBounds.forEach(childBounds -> {
                point2DList.add(getTopPoint(childBounds));
            });

            /*
                Create connector Vertical from child
             */
            point2DList.forEach(point -> {
                connectors.add(drawConnector(point, new Point2D(point.getX(), point.getY() - 20)));
            });

            Point2D p1 = point2DList.stream().min(Comparator.comparingDouble(Point2D::getX)).get();
            Point2D p2 = point2DList.stream().max(Comparator.comparingDouble(Point2D::getX)).get();

            /*
                Create connector beetwen siblings
             */
            if (p1.getX() != p2.getX()) {
                connectors.add(drawConnector(new Point2D(p1.getX(), p1.getY() - 20), new Point2D(p2.getX(), p2.getY() - 20)));
            }


            /*
                   Create connecor beetwen child and parent
             */
            connectors.add(drawConnector(bottomPoint, new Point2D((p1.getX() + p2.getX()) / 2, p1.getY() - 20)));

            getChildren().addAll(connectors);
        }
    }

    private void boundListener() {
        leaf.get().layoutXProperty().addListener((observable, oldValue, newValue) -> {
            drawLines();
        });

        leaf.get().layoutYProperty().addListener((observable, oldValue, newValue) -> {
            drawLines();
        });
    }

    /*
        GETTER
     */
    public ObjectProperty<GTLeaf> leafProperty() {
        return leaf;
    }

    /*
        SETTER
     */

    public void setLeaf(GTLeaf leaf) {
        this.leaf.set(leaf);
    }

    @Override
    public ReadOnlyObjectProperty<GTLeaf> returnLeaf() {
        return leaf;
    }
}

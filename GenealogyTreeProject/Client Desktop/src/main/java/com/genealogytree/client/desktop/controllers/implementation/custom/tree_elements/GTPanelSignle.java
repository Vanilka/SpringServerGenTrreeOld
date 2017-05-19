package com.genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanel;
import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanelSim;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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


    private Bounds getRelativeBounds(Node node) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return this.sceneToLocal(nodeBoundsInScene);
    }

    private Point2D getTopPoint(Bounds b) {
        System.out.println("TOP POINT");
        if (b != null) {
            System.out.println(b);
        }

        return b == null ? null : new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY());
    }

    private Point2D getBottomPoint(Bounds b) {

        System.out.println("Bottom POINT");
        if (b != null) {
            System.out.println(b);
        }

        return b == null ? null : new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY() + b.getHeight());
    }

    private void drawConnector(Point2D p1, Point2D p2) {
        Line connector = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        connector.setStroke(Color.BLACK);
        connector.setStrokeWidth(10);

        Circle circle = new Circle(p1.getX(), p1.getY(), 30, Color.BROWN);

        Circle circle2 = new Circle(p2.getX(), p2.getY(), 60, Color.grayRgb(23, 0.5));

        this.getChildren().addAll(connector, circle, circle2);


    }

    private void drawLines() {
        if (leaf.get() != null) {
            Bounds thisBound = getRelativeBounds(leaf.getValue());

            List<Bounds> childrenBounds = new ArrayList<>();

            childrenPanelList.forEach(c -> {
                childrenBounds.add(getRelativeBounds(c.returnLeaf().get()));
            });

            if (childrenBounds.size() == 1) {
                System.out.println("Children Pane bound " + childrenBounds.get(0));
                System.out.println("this bound " + thisBound);
                Point2D topPoint = getTopPoint(childrenBounds.get(0));
                Point2D bottomPoint = getBottomPoint(thisBound);
                if (topPoint != null && bottomPoint != null) {
                    drawConnector(bottomPoint, topPoint);
                }
            }
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

    @Override
    public ReadOnlyObjectProperty<GTLeaf> returnLeaf() {
        return leaf;
    }
}

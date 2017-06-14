package com.genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanel;
import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanelSim;
import com.genealogytree.client.desktop.controllers.implementation.custom.connectors.GTConnectorChildren;
import com.genealogytree.client.desktop.controllers.implementation.custom.connectors.GTConnectorSpouse;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 29/03/2017.
 * <p>
 * Top Hbox contains leaf, relationType, spouse
 * Left
 * Center
 * Right
 * Bottom  Children
 */
@Getter
@Setter
public class GTPanelCouple extends GTPanelCurrent implements GTPanelSim, GTPanel {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private HBox relation;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private AnchorPane spousePane;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private StackPane relationTypePane;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private HBox childrenbox;


    private GTRelationReference relationRef;

    private ObservableList<GTPanelChild> childrenPanelList;
    private ObjectProperty<GTLeaf> leaf;
    private ObjectProperty<GTLeaf> spouse;
    private ObjectProperty<GTRelationType> relationType;
    private BooleanProperty isActive;
    private GTConnectorSpouse connectorSpouse;
    private List<GTConnectorChildren> connectorChildrenList;

    private List<Line> connectors;


    {
        init();
    }

    public GTPanelCouple(GTLeaf leaf, GTLeaf spouse) {

        this(leaf, spouse, new GTRelationType(), null);
    }

    public GTPanelCouple(GTLeaf leaf, GTLeaf spouse, GTRelationType relationType) {

        this(leaf, spouse, relationType, null);
    }


    public GTPanelCouple(GTLeaf leaf, GTLeaf spouse, GTRelationType type, ObservableList<GTPanelChild> childrenPanelList) {

        autoresize();
        setLeaf(leaf);
        setSpouse(spouse);
        setRelationType(type);

        if (childrenPanelList != null) {
            this.childrenPanelList = childrenPanelList;
        }
        setCenter(childrenbox);
        setTop(relation);
    }


    @Override
    public void setGTPanelChild(GTPanelChild panelChild) {
        childrenPanelList.add(panelChild);
    }

    @Override
    public void setGTPanelChildList(ObservableList<GTPanelChild> panelChildList) {
        childrenPanelList.addAll(panelChildList);
    }

    private void boundListener() {
        relationType.get().needsLayoutProperty().addListener((observable, oldValue, newValue) -> {
            drawLines();
        });

        relationType.get().widthProperty().addListener((observable, oldValue, newValue) -> {
            drawLines();
        });

        relationType.get().heightProperty().addListener((observable, oldValue, newValue) -> {
            drawLines();
        });


        relation.heightProperty().addListener((observable, oldValue, newValue) -> {
            drawLines();
        });

        relation.widthProperty().addListener((observable, oldValue, newValue) -> {
            drawLines();
        });

        relationTypePane.needsLayoutProperty().addListener((observable, oldValue, newValue) -> {
            drawLines();
        });
    }

    @Override
    protected Bounds getRelativeBounds(Node node) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return this.sceneToLocal(nodeBoundsInScene);
    }

    @Override
    protected Point2D getBottomPoint(Bounds b) {

        if (b != null) {
            System.out.println(b);
        }
        return b == null ? null : new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY() + b.getHeight());
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

    private void drawLines() {
        this.getChildren().removeAll(connectors);
        connectors.clear();
            /*
                init Bound fo this Leaf
             */
        Bounds thisBound = getRelativeBounds(relationType.get());
        System.out.println("BOUND OF RELATION : " + thisBound);
        Point2D bottomPoint = getBottomPoint(thisBound);
        System.out.println("point of relation :" + bottomPoint);
        List<Bounds> childrenBounds = new ArrayList<>();

            /*
                Create Bounds of children
             */

        if (childrenPanelList.size() > 0) {
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

        spouse.addListener((observable, oldValue, newValue) -> {

            if (oldValue != null) {
                spousePane.getChildren().remove(oldValue);
            }

            if (newValue == null) {
                spousePane.getChildren().clear();
            } else {
                spousePane.getChildren().add(newValue);
            }
            relationsElementsLocate();
        });


        relationType.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                relationTypePane.getChildren().clear();
            } else {
                boundListener();
                relationTypePane.getChildren().add(newValue);
            }
        });

    }

    private void init() {
        this.connectors = new ArrayList<>();
        this.relationType = new SimpleObjectProperty<>();
        this.relationRef = new GTRelationReference();
        this.childrenPanelList = FXCollections.observableArrayList();
        this.leaf = new SimpleObjectProperty<>();
        this.spouse = new SimpleObjectProperty<>();
        this.isActive = new SimpleBooleanProperty(true);
        initNodes();
    }

    private void initNodes() {
        this.childrenbox = new HBox();
        this.spousePane = new AnchorPane();
        this.relationTypePane = new StackPane();
        this.relation = new HBox();
        initListeners();
        childrenbox.setAlignment(Pos.CENTER);
        childrenbox.setSpacing(50);
        relation.getChildren().addAll(relationTypePane, spousePane);

        // Hide spousePane if spouse leaf is null
        BooleanBinding visibleBinding = Bindings.createBooleanBinding(() -> (spouse.getValue() != null), spouse);
        spousePane.visibleProperty().bind(visibleBinding);
        relationPaneLayout();
    }

    private void relationsElementsLocate() {
        relation.getChildren().clear();
        if (spouse != null) {
            relation.getChildren().addAll(relationTypePane, spousePane);
        }
    }

    private void relationPaneLayout() {
        relation.setAlignment(Pos.CENTER);
        relation.setSpacing(40);
        relationTypePane.resize(60, 60);
    }

    private void autoresize() {
        this.prefWidthProperty().bind(childrenbox.widthProperty());
        childrenbox.maxHeight(Double.MAX_VALUE);
        childrenbox.maxWidth(Double.MAX_VALUE);
    }


    public void setLeaf(GTLeaf leaf) {
        this.leaf.set(leaf);
    }

    public void setSpouse(GTLeaf spouse) {
        this.spouse.set(spouse);
    }

    public void setRelationType(GTRelationType relationType) {
        this.relationType.set(relationType);
    }

    public void addPanelChild(GTPanelChild child) {
        this.childrenPanelList.add(child);
    }

    public List<GTLeaf> getLeafs() {
        List<GTLeaf> result = new ArrayList<>();

        childrenPanelList.forEach(p -> result.add(p.getLeaf()));
        return result;
    }

    public void setIsActive(boolean isActive) {
        this.isActive.set(isActive);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GTPanelCouple{");
        sb.append("childrenPanelList=").append(childrenPanelList);
        sb.append(", leaf=").append(leaf);
        sb.append(", spouse=").append(spouse);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public ReadOnlyObjectProperty<GTLeaf> returnLeaf() {
        return leaf;
    }
}

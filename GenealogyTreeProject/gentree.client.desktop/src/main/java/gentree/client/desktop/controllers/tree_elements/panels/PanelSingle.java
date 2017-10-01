package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.controllers.tree_elements.connectors.ParentToChildrenConnector;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.visualization.elements.RelationReference;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 * <p>
 * Panel representing Sim itself and his own childrenPanels
 * Top: Leaf
 * Center: List of childrenPanels
 */
@Getter
@Setter
public class PanelSingle extends SubRelationPane implements RelationPane{

    private final static double MARGIN_TOP = 0.0;
    private final static double MARGIN_LEFT = 0.0;
    private final static double MARGIN_RIGHT = 0.0;
    private final static double MARGIN_BOTTOM = 50.0;

    private final static double PADDING_TOP = 10.0;
    private final static double PADDING_LEFT = 10.0;
    private final static double PADDING_RIGHT = 10.0;
    private final static double PADDING_BOTTOM = 0.0;

    private final AnchorPane pane;
    private final FamilyMember member;
    private final ObjectProperty<Relation> thisRelation;

    private final ObservableList<PanelChild> childrenPanels;
    private final ParentToChildrenConnector childrenConnector;
    private final RelationReference thisRelationReference;

    /*
        Initialization
     */

    {
        pane = new AnchorPane();
        member = new FamilyMember();
        thisRelation = new SimpleObjectProperty<>();
        thisRelationReference = new RelationReference(RelationReference.RelationReferenceType.DSC);
        childrenPanels = FXCollections.observableArrayList();
        childrenConnector = new ParentToChildrenConnector(this);

    }

    public PanelSingle() {
        this(null, null,null);
    }

    public PanelSingle(Member m) {
        this(m, null, null);
    }

    public PanelSingle(Member m, SubBorderPane parent) {
        this(m, null, parent);
    }

    public PanelSingle(Member m, Relation thisRelation) {
        this(m, thisRelation,null);
    }

    public PanelSingle(Member m, Relation thisRelation, SubBorderPane parent) {
        super();
        init();
        this.member.setMember(m);
        this.thisRelation.setValue(thisRelation);
        setParentPane(parent);

        initBorder(Color.BLUE, this);
    }


    /*
        Init Panes
     */

    private void init() {
        initPane();
        initListeners();
        initHbox();
        setTop(pane);
        setCenter(childrenBox);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        setMargin(pane, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));

    }

    private void initPane() {
        setPrefSize(100,100);
        pane.setPrefHeight(RELATION_HEIGHT);
        pane.getChildren().addAll(member, thisRelationReference);
    }

    private void  initHbox() {
        childrenBox.setSpacing(10);
    }

    private void initListeners() {
        initThisRelationListener();
        initChildrenListener();
        initRelationElementsPositionListener();

    }

    private void initThisRelationListener() {

        thisRelation.addListener((observable, oldValue, newValue) -> {
            thisRelationReference.setRelation(newValue);
        });
    }


    private void initChildrenListener() {
        childrenPanels.addListener((ListChangeListener<PanelChild>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    childrenBox.getChildren().addAll(c.getAddedSubList());
                    c.getAddedSubList().forEach(childrenConnector::addPanelChild);
                }
                if (c.wasRemoved()) {
                    childrenBox.getChildren().removeAll(c.getRemoved());
                    c.getAddedSubList().forEach(childrenConnector::removePanelChild);
                }
            }
        });
    }




    private void initRelationElementsPositionListener() {

        childrenBox.prefWidthProperty().bindBidirectional(pane.prefWidthProperty());
        this.prefWidthProperty().bind(childrenBox.widthProperty());

        pane.widthProperty().addListener(observable -> {
            calculateRelationElementsPosition();
        });

        childrenConnector.getLine().startXProperty().addListener(c -> {
            calculateRelationElementsPosition();
        });

        childrenConnector.getLine().endXProperty().addListener((observable, oldValue, newValue) -> {
            calculateRelationElementsPosition();
        });

        childrenConnector.getLine().startYProperty().addListener(c -> {
            calculateRelationElementsPosition();
        });

        childrenConnector.getLine().endYProperty().addListener((observable, oldValue, newValue) -> {
            calculateRelationElementsPosition();
        });

        member.layoutXProperty().addListener(observable -> {
            calculateThisRelationPosition();
        });

        member.layoutYProperty().addListener(observable -> {
            calculateThisRelationPosition();
        });

        member.widthProperty().addListener(observable -> {
            calculateThisRelationPosition();
        });
    }

    private void calculateRelationElementsPosition() {
        if (childrenPanels.size() > 0) {
            Line line = childrenConnector.getLine();
            Double offset = (line.getStartX() + line.getEndX() - member.getWidth())/2 - PADDING_LEFT;
            member.setLayoutX(offset);
            childrenConnector.connectRelationToChildren(member);
        }
    }

    private void calculateThisRelationPosition() {

        thisRelationReference.setLayoutX(member.getLayoutX());
        thisRelationReference.setLayoutY(member.getLayoutY() + member.getHeight());

    }


    public void addMember(Member m) {
        if (m != null) {
            member.setMember(m);
        }
    }


    @Override
    public void addChild(PanelChild child) {
        childrenPanels.add(child);
        child.setParentPane(this);
    }
}

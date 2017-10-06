package gentree.client.visualization.gustave.panels;

import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationReference;
import gentree.client.visualization.elements.RelationTypeElement;
import gentree.client.visualization.gustave.connectors.ParentToChildrenConnector;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.Getter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class PanelRelationCurrent extends SubRelationPane implements RelationPane {

    private final static double MARGIN_TOP = 0.0;
    private final static double MARGIN_LEFT = 0.0;
    private final static double MARGIN_RIGHT = 0.0;
    private final static double MARGIN_BOTTOM = 50.0;

    private final static double PADDING_TOP = 10.0;
    private final static double PADDING_LEFT = 10.0;
    private final static double PADDING_RIGHT = 10.0;
    private final static double PADDING_BOTTOM = 0.0;

    private final static double MINIMAL_RELATION_WIDTH = 450.0;

    @Getter
    private final Pane relation;

    @Getter
    private final FamilyMember spouseCard;

    @Getter
    private final RelationTypeElement relationTypeElement;

    private final ObjectProperty<RelationType> relationType;
    private final ObjectProperty<Member> spouse;
    private final ObjectProperty<Relation> thisRelation;
    private final ObservableList<PanelChild> children;
    private final ParentToChildrenConnector childrenConnector;
    private final RelationReference thisRelationReference;
    private final RelationReference spouseRelationReference;
    private final ObjectProperty<Relation> spouseBornRelation;

    private final DoubleProperty offset = new SimpleDoubleProperty(0);

    {
        relation = new Pane();
        relationTypeElement = new RelationTypeElement();
        spouseCard = new FamilyMember();
        relationType = new SimpleObjectProperty<>();
        spouse = new SimpleObjectProperty<>();
        thisRelation = new SimpleObjectProperty<>();
        children = FXCollections.observableArrayList();
        childrenConnector = new ParentToChildrenConnector(this);
        spouseBornRelation = new SimpleObjectProperty<>();
        spouseRelationReference = new RelationReference(RelationReference.RelationReferenceType.ASC);
        thisRelationReference = new RelationReference(RelationReference.RelationReferenceType.DSC);
    }

    public PanelRelationCurrent() {
        this(null, null, null, null);
    }

    public PanelRelationCurrent(Member spouse, Relation thisRelation, Relation spouseBorn) {
        this(spouse, thisRelation, spouseBorn, null);
    }

    public PanelRelationCurrent(Member spouse, Relation thisRelation, Relation spouseBorn, SubBorderPane parent) {
        super();
        initPanes();
        initListeners();
        this.spouse.setValue(spouse);
        this.thisRelation.setValue(thisRelation);
        this.spouseBornRelation.setValue(spouseBorn);
        initBorder(Color.PURPLE, relation);
        initBorder(Color.ROSYBROWN, this);
        initBorder(Color.BLUE, childrenBox);

        minWidth(USE_PREF_SIZE);
        maxWidth(USE_PREF_SIZE);

        spouseCard.setOnMouseClicked(event -> {
            System.out.println("Relation reference " + thisRelation.getReferenceNumber());
        });
    }


    private void initPanes() {
        initHbox();
        initAnchorPanesOffset();

        this.setCenter(childrenBox);
        this.setTop(relation);
        BorderPane.setAlignment(childrenBox, Pos.TOP_RIGHT);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        setMargin(relation, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));

        relation.setPrefHeight(RELATION_HEIGHT);

    }


    private void initAnchorPanesOffset() {
    }

    private void initHbox() {
        childrenBox.setSpacing(10);
    }

    /*
        Listeners
     */

    private void initListeners() {
        initSpouseListener();
        initThisRelationListener();
        initSpouseBornRelationListener();
        initChildrenListener();
        initElementsPositionListeners();
    }

    private void initSpouseListener() {
        spouse.addListener((observable, oldValue, newValue) -> {
            relation.getChildren().removeAll();
            if (newValue != null) {
                spouseCard.setMember(newValue);
                relation.getChildren().addAll(relationTypeElement, spouseCard, spouseRelationReference, thisRelationReference);
            }
        });
    }

    private void initThisRelationListener() {

        thisRelation.addListener((observable, oldValue, newValue) -> {
            relationTypeElement.setRelation(newValue);
            thisRelationReference.setRelation(newValue);
        });
    }


    private void initSpouseBornRelationListener() {
        spouseBornRelation.addListener((observable, oldValue, newValue) -> {
            spouseRelationReference.setRelation(newValue);
        });

    }

    private void initChildrenListener() {
        children.addListener((ListChangeListener<PanelChild>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    childrenBox.getChildren().addAll(c.getAddedSubList());
                    c.getAddedSubList().forEach(childrenConnector::addPanelChild);
                } else if (c.wasRemoved()) {
                    childrenBox.getChildren().removeAll(c.getRemoved());
                    c.getRemoved().forEach(childrenConnector::removePanelChild);
                }
            }
        });
    }

    private void initElementsPositionListeners() {

        /*
         *  Init bindings
         */
        offset.bind(Bindings.when((spouseCard.layoutXProperty().add(spouseCard.widthProperty()).add(10)).greaterThan(relation.widthProperty()))
                .then(spouseCard.layoutXProperty().add(spouseCard.widthProperty()).add(10).subtract(relation.widthProperty()))
                .otherwise(0.0));

        relationTypeElement.layoutYProperty().bind(spouseCard.heightProperty().subtract(relationTypeElement.heightProperty()).divide(2));
        spouseCard.layoutXProperty().bind(relationTypeElement.layoutXProperty().add(200));

        thisRelationReference.layoutXProperty().bind(relationTypeElement.layoutXProperty().add(relationTypeElement.widthProperty().subtract(thisRelationReference.widthProperty()).divide(2)));
        thisRelationReference.layoutYProperty().bind(relationTypeElement.layoutYProperty().add(relationTypeElement.prefHeightProperty()));

        spouseRelationReference.layoutXProperty().bind(spouseCard.layoutXProperty().add(30));
        spouseRelationReference.layoutYProperty().bind(spouseCard.layoutYProperty().subtract(60));

        relationTypeElement.layoutXProperty().bind(Bindings.when(Bindings.isNotEmpty(children))
                .then(childrenConnector.getLine().startXProperty()
                        .subtract(relationTypeElement.widthProperty().divide(2))
                        .subtract(PADDING_LEFT))
                .otherwise(100));
    }

    @Override
    public Node getConnectionNode() {
        return relationTypeElement;
    }

    @Override
    public void addChild(PanelChild child) {
        children.add(child);
        child.setParentPane(this);
    }

    protected Bounds getRelativeBounds(Node node, Node relativeTo) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return relativeTo.sceneToLocal(nodeBoundsInScene);
    }

    protected Point2D getBottomPoint(Bounds b) {

        return b == null ? null : new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY() + b.getHeight());
    }


    /*
        GETTERS AND SETTERS
     */

    public Relation getSpouseBornRelation() {
        return spouseBornRelation.get();
    }

    public void setSpouseBornRelation(Relation spouseBornRelation) {
        this.spouseBornRelation.set(spouseBornRelation);
    }

    public ObjectProperty<Relation> spouseBornRelationProperty() {
        return spouseBornRelation;
    }
}



package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.controllers.tree_elements.RelationTypeElement;
import gentree.client.desktop.controllers.tree_elements.connectors.ParentToChildrenConnector;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.visualization.elements.RelationReference;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
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
    private final AnchorPane relation;

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




    {
        relation = new AnchorPane();
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

        initPanes();
        initListeners();
        this.spouse.setValue(spouse);
        this.thisRelation.setValue(thisRelation);
        this.spouseBornRelation.setValue(spouseBorn);

        setOnMouseClicked(event -> {
            System.out.println("clicket on me :" + this.toString());
        });

    }


    private void initPanes() {
        relation.setPrefHeight(RELATION_HEIGHT);
        setPrefSize(MINIMAL_RELATION_WIDTH, RELATION_HEIGHT+100);
        initHbox();
        initAnchorPanesOffset();
        this.setCenter(childrenBox);
        this.setTop(relation);
        BorderPane.setAlignment(childrenBox, Pos.TOP_RIGHT);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        setMargin(relation, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));

    }



    private  void initAnchorPanesOffset() {
        AnchorPane.setLeftAnchor(relation, 10.0);
        AnchorPane.setRightAnchor(relation, 10.0);

        AnchorPane.setLeftAnchor(childrenBox, 10.0);
        AnchorPane.setRightAnchor(childrenBox, 10.0);

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
        initRelationPaneSizeListener();
        resizeRelation();
        calculateRelationElementsPosition();
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
            calculateRelationElementsPosition();
        });
    }


    private void initRelationPaneSizeListener() {

        relation.prefWidthProperty().bindBidirectional(childrenBox.prefWidthProperty());

        relation.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (relationTypeElement != null) {
                relationTypeElement.setLayoutY((spouseCard.getHeight() - relationTypeElement.getHeight() - MARGIN_BOTTOM - PADDING_TOP) / 2);
                calculateRelationElementsPosition();
            }
        });

        relation.widthProperty().addListener((observable, oldValue, newValue) -> {
            calculateRelationElementsPosition();

        });

        spouseCard.layoutXProperty().addListener(observable -> {
            resizeRelation();
            setSpouseRelationreferencePosition();

        });

        spouseCard.layoutYProperty().addListener(observable -> {
            setSpouseRelationreferencePosition();
        });

        childrenConnector.getLine().startXProperty().addListener(c -> {
            calculateRelationElementsPosition();
            resizeRelation();
        });

        relationTypeElement.boundsInLocalProperty().addListener(c-> {
            calculateRelationElementsPosition();
            calculateThisRelationPosition();
            resizeRelation();
        });

        relationTypeElement.layoutYProperty().addListener((observable, oldValue, newValue) -> {
            calculateRelationElementsPosition();
            calculateThisRelationPosition();
            resizeRelation();
        });

        relationTypeElement.heightProperty().addListener(c -> {
            calculateThisRelationPosition();
        });
    }

    private void setSpouseRelationreferencePosition() {
        spouseRelationReference.setLayoutX(spouseCard.getLayoutX()+30);
        spouseRelationReference.setLayoutY(spouseCard.getLayoutY()-60);
    }

    private void calculateRelationElementsPosition() {
        if (children.size() > 0) {
            Line line = childrenConnector.getLine();
            relationTypeElement.setLayoutX(line.getStartX() - relationTypeElement.getWidth() / 2 - PADDING_LEFT);
            spouseCard.setLayoutX(relationTypeElement.getLayoutX() + 200);
            childrenConnector.connectRelationToChildren(relationTypeElement);
        } else {
            relationTypeElement.setLayoutX(100);
            spouseCard.setLayoutX(relationTypeElement.getLayoutX() + 200);
            relation.setPrefWidth(MINIMAL_RELATION_WIDTH);
        }
    }

    private void calculateThisRelationPosition() {
        thisRelationReference.setManaged(false);
        thisRelationReference.layoutXProperty().bind(relationTypeElement.layoutXProperty());
        thisRelationReference.layoutYProperty().bind(relationTypeElement.layoutYProperty().add(relationTypeElement.prefHeightProperty()));

    }

    private void resizeRelation() {
        if((spouseCard.getLayoutX() + spouseCard.getWidth()) > relation.getWidth()) {
            relation.setPrefWidth(MINIMAL_RELATION_WIDTH);
        }
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

    public ObjectProperty<Relation> spouseBornRelationProperty() {
        return spouseBornRelation;
    }

    public void setSpouseBornRelation(Relation spouseBornRelation) {
        this.spouseBornRelation.set(spouseBornRelation);
    }
}



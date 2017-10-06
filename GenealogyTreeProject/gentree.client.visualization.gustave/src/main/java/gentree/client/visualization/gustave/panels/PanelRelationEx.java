package gentree.client.visualization.gustave.panels;

import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationReference;
import gentree.client.visualization.elements.RelationTypeElement;
import gentree.client.visualization.gustave.connectors.ParentToChildrenConnector;
import gentree.client.visualization.gustave.connectors.SpouseExConnector;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.Getter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 * Class to visualization PanelRelation Ex in GenealogyTree
 */
public class PanelRelationEx extends SubRelationPane implements RelationPane {

    private final static double MARGIN_TOP = 0.0;
    private final static double MARGIN_LEFT = 0.0;
    private final static double MARGIN_RIGHT = 0.0;
    private final static double MARGIN_BOTTOM = 50.0;

    private final static double PADDING_TOP = 10.0;
    private final static double PADDING_LEFT = 10.0;
    private final static double PADDING_RIGHT = 10.0;
    private final static double PADDING_BOTTOM = 0.0;

    private final static double MINIMAL_RELATION_WIDTH = 450.0;

    private final static double SPACE_BETWEEN_OBJECTS = 100.0;

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
    private final SpouseExConnector spouseExConnector;
    private final RelationReference spouseRelationReference;
    private final RelationReference thisRelationReference;
    private final ObjectProperty<Relation> spouseBornRelation;

    private final DoubleProperty offset = new SimpleDoubleProperty(0.0);


    {
        relation = new Pane();
        spouseCard = new FamilyMember();
        relationTypeElement = new RelationTypeElement();
        relationType = new SimpleObjectProperty<>();
        spouse = new SimpleObjectProperty<>();
        thisRelation = new SimpleObjectProperty<>();
        children = FXCollections.observableArrayList();
        spouseExConnector = new SpouseExConnector(this);
        childrenConnector = new ParentToChildrenConnector(this);
        thisRelationReference = new RelationReference(RelationReference.RelationReferenceType.DSC);
        spouseBornRelation = new SimpleObjectProperty<>();
        spouseRelationReference = new RelationReference(RelationReference.RelationReferenceType.ASC);
    }

    public PanelRelationEx() {
        this(null, null, null);
    }

    public PanelRelationEx(Member spouse, Relation thisRelation) {
        this(spouse, thisRelation, null);
    }

    public PanelRelationEx(Member spouse, Relation thisRelation, SubBorderPane parent) {
        super();
        initPanes();
        initListeners();
        this.spouse.setValue(spouse);
        this.thisRelation.setValue(thisRelation);
        initBorder(Color.GREEN, this);
        initBorder(Color.FUCHSIA, relation);
        initBorder(Color.ORANGE, childrenBox);

        spouseCard.setOnMouseClicked(event -> {
            System.out.println("Pref Width: " + getPrefWidth());
            System.out.println("Offset" + offset.doubleValue());
            System.out.println("width : " + getWidth());
        });
    }


    private void initPanes() {
        initHbox();
        this.setCenter(childrenBox);
        this.setTop(relation);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        setMargin(relation, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));
        relation.setPrefHeight(RELATION_HEIGHT);
    }


    private void initAnchorPanesOffset() {
        relation.setMinWidth(MINIMAL_RELATION_WIDTH);
    }

    private void initHbox() {
        childrenBox.setSpacing(10);
        childrenBox.setAlignment(Pos.TOP_RIGHT);
       // childrenBox.prefWidthProperty().bind(prefWidthProperty());
        //relation.prefWidthProperty().bind(prefWidthProperty());
    }


    @Override
    public void addChild(PanelChild child) {
        children.add(child);
        child.setParentPane(this);
    }

    /*
        Listeners
     */


    private void initListeners() {
        initSpouseListener();
        initThisRelationListener();
        initChildrenListener();
        initElementPositionsListeners();
    }

    private void initThisRelationListener() {

        thisRelation.addListener((observable, oldValue, newValue) -> {
            relationTypeElement.setRelation(newValue);
            thisRelationReference.setRelation(newValue);
        });
    }


    private void initSpouseListener() {
        spouse.addListener((observable, oldValue, newValue) -> {
            relation.getChildren().removeAll();
            if (newValue != null) {
                spouseCard.setMember(newValue);
                relation.getChildren().addAll(spouseCard, relationTypeElement, spouseRelationReference, thisRelationReference);
            }
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
            // calculateRelationElementsPosition();
        });
    }

    private void initElementPositionsListeners() {

        offset.bind(Bindings.when(spouseCard.layoutXProperty().lessThan(0))
                .then(spouseCard.layoutXProperty().multiply(-1))
                .otherwise(0));

        relationTypeElement.layoutYProperty().bind(spouseCard.heightProperty().subtract(relationTypeElement.heightProperty()).divide(2));
        spouseCard.layoutXProperty().bind(relationTypeElement.layoutXProperty().subtract(SPACE_BETWEEN_OBJECTS).subtract(spouseCard.widthProperty()));

        thisRelationReference.layoutXProperty().bind(relationTypeElement.layoutXProperty().add(relationTypeElement.widthProperty().subtract(thisRelationReference.widthProperty()).divide(2)));
        thisRelationReference.layoutYProperty().bind(relationTypeElement.layoutYProperty().add(relationTypeElement.heightProperty()));

        relationTypeElement.layoutXProperty().bind(Bindings.when(Bindings.isNotEmpty(children))
                .then(childrenConnector.getLine().startXProperty()
                        .subtract(relationTypeElement.widthProperty().divide(2))
                        .subtract(PADDING_LEFT))
                .otherwise(spouseCard.widthProperty().add(SPACE_BETWEEN_OBJECTS).add(50)));
    }

    public final RelationTypeElement getRelationTypeElement() {
        return relationTypeElement;
    }

    @Override
    public Node getConnectionNode() {
        return relationTypeElement;
    }
}


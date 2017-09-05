package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.controllers.tree_elements.RelationReference;
import gentree.client.desktop.controllers.tree_elements.RelationTypeElement;
import gentree.client.desktop.controllers.tree_elements.connectors.ParentToChildrenConnector;
import gentree.client.desktop.controllers.tree_elements.connectors.SpouseExConnector;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.Getter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class PanelRelationEx extends SubRelationPane implements RelationPane {

    private final static double MARGIN_TOP = 0.0;
    private final static double MARGIN_LEFT = 20.0;
    private final static double MARGIN_RIGHT = 0.0;
    private final static double MARGIN_BOTTOM = 50.0;

    private final static double PADDING_TOP = 10.0;
    private final static double PADDING_LEFT = 10.0;
    private final static double PADDING_RIGHT = 10.0;
    private final static double PADDING_BOTTOM = 0.0;

    private final static double MINIMAL_RELATION_WIDTH = 450.0;
    private final static double SPACE_BEETWEN_OBJECTS = 100.0;

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
    private final SpouseExConnector spouseExConnector;
    private final RelationReference spouseRelationReference;
    private final RelationReference thisRelationReference;
    private final ObjectProperty<Relation> spouseBornRelation;


    {
        relation = new AnchorPane();
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
        this.relation.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }


    private void initPanes() {
        relation.resize(MINIMAL_RELATION_WIDTH, RELATION_HEIGHT);
        initHbox();
        this.setCenter(childrenBox);
        this.setTop(relation);
        relation.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        setMargin(relation, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));
    }

    private void initHbox() {
        childrenBox.setSpacing(10);
        childrenBox.setAlignment(Pos.TOP_RIGHT);
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
        initRelationTypeListener();
        initRelationElementsPositionListener();
        calculateRelationElementsPosition();
    }

    private void initThisRelationListener() {

        thisRelation.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                relationType.bind(newValue.typeProperty());
            } else {
                relationType.unbind();
            }
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

    private void initRelationTypeListener() {
        relationType.addListener((observable, oldValue, newValue) -> {
            relationTypeElement.setType(newValue == null ? RelationType.NEUTRAL : newValue);
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

    private void initRelationElementsPositionListener() {

        relation.prefWidthProperty().bindBidirectional(childrenBox.prefWidthProperty());

        relationTypeElement.boundsInLocalProperty().addListener(c -> {
            relationTypeElement.setLayoutY((spouseCard.getHeight() - relationTypeElement.getHeight()) / 2);
            calculateRelationElementsPosition();
            calculateThisRelationPosition();

        });

        spouseCard.boundsInLocalProperty().addListener(c -> {
            relationTypeElement.setLayoutY((spouseCard.getHeight() - relationTypeElement.getHeight()) / 2);
            calculateRelationElementsPosition();
            calculateThisRelationPosition();
        });


        relation.boundsInParentProperty().addListener(c -> {
            calculateRelationElementsPosition();
            calculateThisRelationPosition();
        });

        relation.boundsInLocalProperty().addListener(c -> {
            calculateRelationElementsPosition();
            calculateThisRelationPosition();
        });


        childrenConnector.getLine().boundsInLocalProperty().addListener(c -> {
            calculateRelationElementsPosition();
        });


        spouseCard.layoutXProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() < 0)
            {
                Double expectedValue = relation.getWidth() + newValue.doubleValue() * (-1) + 100;
                relation.resize(expectedValue, relation.getHeight());
                calculateRelationElementsPosition();
            }
        });

        childrenBox.prefWidthProperty().addListener(observable -> {
            calculateRelationElementsPosition();
        });

        childrenBox.widthProperty().addListener(observable -> {
            calculateRelationElementsPosition();
        });
    }

    private void calculateRelationElementsPosition() {
        if (children.size() > 0) {
            Line line = childrenConnector.getLine();
            Double offsetRelationType = line.getEndX() - relationTypeElement.getWidth() / 2.0 - MARGIN_LEFT - PADDING_LEFT;
            Double offsetSpouseCard = offsetRelationType - spouseCard.getWidth() - SPACE_BEETWEN_OBJECTS;

            relationTypeElement.setLayoutX(offsetRelationType);
            spouseCard.setLayoutX(offsetSpouseCard);

            childrenConnector.connectRelationToChildren(relationTypeElement);

        } else {
            relationTypeElement.setLayoutX(spouseCard.getWidth() + SPACE_BEETWEN_OBJECTS);
            relation.setPrefWidth(MINIMAL_RELATION_WIDTH);
        }
    }

    private void calculateThisRelationPosition() {

        thisRelationReference.setLayoutX(relationTypeElement.getLayoutX());
        thisRelationReference.setLayoutY(relationTypeElement.getLayoutY() + relationTypeElement.getHeight());

    }
}


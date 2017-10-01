package gentree.client.visualization.gustave.panels;

import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationTypeElement;
import gentree.client.visualization.gustave.connectors.ParentToChildrenConnector;
import gentree.client.visualization.gustave.connectors.SpouseExConnector;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.visualization.elements.RelationReference;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
    }


    private void initPanes() {
        setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        //computePrefWidth(MINIMAL_RELATION_WIDTH);
        //this.relation.resize(MINIMAL_RELATION_WIDTH, RELATION_HEIGHT);
        //resize(MINIMAL_RELATION_WIDTH, RELATION_HEIGHT);
        initHbox();
       // initAnchorPanesOffset();
        this.setCenter(childrenBox);
        this.setTop(relation);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        setMargin(relation, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));
    }


    private void initAnchorPanesOffset() {
        AnchorPane.setLeftAnchor(relation, 10.0);
        AnchorPane.setRightAnchor(relation, 10.0);

        AnchorPane.setLeftAnchor(childrenBox, 10.0);
        AnchorPane.setRightAnchor(childrenBox, 10.0);



    }

    private void initHbox() {
        childrenBox.setSpacing(10);
        childrenBox.setAlignment(Pos.TOP_RIGHT);
        childrenBox.prefWidthProperty().bind(prefWidthProperty());
        relation.prefWidthProperty().bind(prefWidthProperty());
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
        initRelationElementsPositionListener();
        calculateRelationElementsPosition();
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
            calculateRelationElementsPosition();
        });
    }

    private void initRelationElementsPositionListener() {

        relationTypeElement.boundsInLocalProperty().addListener(c -> {
            relationTypeElement.setLayoutY((spouseCard.getHeight() - relationTypeElement.getHeight()) / 2);
            calculateRelationElementsPosition();

        });

        calculateThisRelationPosition();

        spouseCard.boundsInLocalProperty().addListener(c -> {
            relationTypeElement.setLayoutY((spouseCard.getHeight() - relationTypeElement.getHeight()) / 2);
            calculateRelationElementsPosition();
        });


        childrenBox.boundsInLocalProperty().addListener(c -> {
            calculateRelationElementsPosition();
        });

        childrenBox.boundsInParentProperty().addListener(c -> {
            calculateRelationElementsPosition();
        });


        childrenConnector.getLine().boundsInLocalProperty().addListener(c -> {
            calculateRelationElementsPosition();
        });


        childrenBox.prefWidthProperty().addListener(observable -> {
            calculateRelationElementsPosition();
        });

        childrenBox.widthProperty().addListener(observable -> {
            calculateRelationElementsPosition();
        });

        prefWidthProperty().addListener(observable -> {
            calculateRelationElementsPosition();
        });

        widthProperty().addListener(observable -> {
            calculateRelationElementsPosition();
        });
    }

    private void calculateRelationElementsPosition() {
        if (children.size() > 0) {
            Line line = childrenConnector.getLine();
            Double offsetRelationType = line.getEndX() - relationTypeElement.getWidth() / 2.0 - MARGIN_LEFT - PADDING_LEFT;
            Double offsetSpouse = offsetRelationType - SPACE_BETWEEN_OBJECTS - 200;

            if (offsetSpouse < 0) {
                System.out.println("Offest Spouse " + offsetSpouse);
                System.out.println("Current prefWidth " + getPrefWidth());
                Double x = getWidth() - offsetSpouse + 50;
                System.out.println("New pref width " + x);
                this.setPrefWidth(x);

            } else {
                relationTypeElement.setLayoutX(offsetRelationType);
                spouseCard.setLayoutX(offsetSpouse);
            }

            childrenConnector.connectRelationToChildren(relationTypeElement);

        } else {
            relationTypeElement.setLayoutX(spouseCard.getWidth() + SPACE_BETWEEN_OBJECTS);
            this.setPrefWidth(MINIMAL_RELATION_WIDTH);
        }
    }

    private void calculateThisRelationPosition() {
        thisRelationReference.setManaged(false);

        thisRelationReference.layoutXProperty().bind(relationTypeElement.layoutXProperty().add(20));
        thisRelationReference.layoutYProperty().bind(relationTypeElement.layoutYProperty().add(relationTypeElement.heightProperty()).add(30));
      /*  thisRelationReference.setLayoutX(relationTypeElement.getLayoutX());
        thisRelationReference.setLayoutY(relationTypeElement.getLayoutY() + relationTypeElement.getHeight());*/

    }
}


package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.controllers.tree_elements.RelationTypeElement;
import gentree.client.desktop.controllers.tree_elements.connectors.RelationConnector;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.enums.RelationType;
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
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import lombok.Getter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class PanelRelationCurrent extends SubBorderPane implements RelationPane {

    private final static double MARGIN_TOP = 0.0;
    private final static double MARGIN_LEFT = 0.0;
    private final static double MARGIN_RIGHT = 0.0;
    private final static double MARGIN_BOTTOM = 50.0;

    private final static double PADDING_TOP = 10.0;
    private final static double PADDING_LEFT = 10.0;
    private final static double PADDING_RIGHT = 10.0;
    private final static double PADDING_BOTTOM = 0.0;

    private final static double MINIMAL_RELATION_WIDTH = 550.0;

    @Getter
    private final AnchorPane relation;
    @Getter
    private final HBox childrenBox;

    private final FamilyMember spouseCard;
    private final RelationTypeElement relationTypeElement;


    private final ObjectProperty<RelationType> relationType;
    private final ObjectProperty<Member> spouse;
    private final ObservableList<PanelChild> children;
    private final RelationConnector childrenConnector;


    {
        relation = new AnchorPane();
        childrenBox = new HBox();
        spouseCard = new FamilyMember();
        relationTypeElement = new RelationTypeElement();
        relationType = new SimpleObjectProperty<>();
        spouse = new SimpleObjectProperty<>();
        children = FXCollections.observableArrayList();
        childrenConnector = new RelationConnector(this);
    }

    public PanelRelationCurrent() {
        this(null, RelationType.NEUTRAL, null);
    }

    public PanelRelationCurrent(Member spouse, RelationType type) {
        this(spouse, type, null);
    }

    public PanelRelationCurrent(Member spouse, RelationType type, SubBorderPane parent) {
        initPanes();
        initListeners();
        this.spouse.setValue(spouse);
        this.relationType.setValue(type);
    }


    private void initPanes() {
        initHbox();
        this.setCenter(childrenBox);
        this.setTop(relation);
        BorderPane.setAlignment(childrenBox, Pos.TOP_RIGHT);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));

        setMargin(relation, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));

    }

    private void initHbox() {
        childrenBox.setSpacing(10);
    }

    /*
        Listeners
     */

    private void initListeners() {
        initSpouseListener();
        initChildrenListener();
        initRelationTypeListener();
        initRelationPaneSizeListener();
        resizeRelation();
    }


    private void initSpouseListener() {
        spouse.addListener((observable, oldValue, newValue) -> {
            relation.getChildren().removeAll();
            if (newValue != null) {
                spouseCard.setMember(newValue);
                relation.getChildren().addAll(relationTypeElement, spouseCard);
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
        });
    }


    private void initRelationPaneSizeListener() {

        relation.prefWidthProperty().bindBidirectional(childrenBox.prefWidthProperty());

        relation.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (relationTypeElement != null) {
                relationTypeElement.setLayoutY((newValue.doubleValue() - relationTypeElement.getHeight() - MARGIN_BOTTOM - PADDING_TOP) / 2);
                calculateRelationElementsPosition();
            }
        });

        relation.widthProperty().addListener((observable, oldValue, newValue) -> {
            calculateRelationElementsPosition();

        });

        spouseCard.layoutXProperty().addListener(observable -> {
            resizeRelation();
        });

        childrenConnector.getLine().startXProperty().addListener(c -> {
            calculateRelationElementsPosition();
            resizeRelation();
        });

        relationTypeElement.boundsInLocalProperty().addListener(c-> {
            calculateRelationElementsPosition();
            resizeRelation();
        });

        relationTypeElement.layoutYProperty().addListener((observable, oldValue, newValue) -> {
            calculateRelationElementsPosition();
            resizeRelation();
        });
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

        if (b != null) {
            System.out.println(b);
        }
        return b == null ? null : new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY() + b.getHeight());
    }
}



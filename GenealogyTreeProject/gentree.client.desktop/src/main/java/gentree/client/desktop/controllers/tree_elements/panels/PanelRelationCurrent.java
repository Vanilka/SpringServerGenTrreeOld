package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.controllers.tree_elements.RelationTypeElement;
import gentree.client.desktop.controllers.tree_elements.connectors.RelationCurrentConnector;
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
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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
    private final static double PADDING_BOTTOM = 10.0;

    @Getter
    private final AnchorPane relation;
    private final HBox childrenBox;

    private final FamilyMember spouseCard;
    private final RelationTypeElement relationTypeElement;


    private final ObjectProperty<RelationType> relationType;
    private final ObjectProperty<Member> spouse;
    private final ObservableList<PanelChild> children;
    private final RelationCurrentConnector childrenConnector;


    {
        relation = new AnchorPane();
        childrenBox = new HBox();
        spouseCard = new FamilyMember();
        relationTypeElement = new RelationTypeElement();
        relationType = new SimpleObjectProperty<>();
        spouse = new SimpleObjectProperty<>();
        children = FXCollections.observableArrayList();
        childrenConnector = new RelationCurrentConnector(this);
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
        this.initBorder(Color.GREEN, this);
        this.initBorder(Color.CHOCOLATE, relation);
        this.initBorder(Color.DARKMAGENTA, relationTypeElement);
    }


    private void initPanes() {
        initHbox();
        this.setCenter(childrenBox);
        this.setTop(relation);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));

        setMargin(relation, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));

    }

    private void initHbox() {
        childrenBox.setSpacing(10);
        initBorder(Color.DARKMAGENTA, childrenBox);
    }

    /*
        Listeners
     */

    private void initListeners() {
        initSpouseListener();
        initChildrenListener();
        initRelationTypeListener();
        initRelationPaneSizeListener();
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

        relation.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (relationTypeElement != null) {
                relationTypeElement.setLayoutY((newValue.doubleValue() - relationTypeElement.getHeight() - MARGIN_BOTTOM - PADDING_TOP) / 2);
            }
        });


      childrenConnector.middleProperty().addListener((observable, oldValue, newValue) -> {
            relationTypeElement.setLayoutX(newValue.getX() - relationTypeElement.getWidth()/2);
            spouseCard.setLayoutX(relationTypeElement.getLayoutX() + 200);
            childrenConnector.connectNodeWithMiddle(relationTypeElement);
        });

        relationTypeElement.widthProperty().addListener((observable, oldValue, newValue) -> {
            relationTypeElement.setLayoutX(childrenConnector.getMiddle().getX() -relationTypeElement.getWidth()/2);
            spouseCard.setLayoutX(relationTypeElement.getLayoutX() + 200);
            childrenConnector.connectNodeWithMiddle(relationTypeElement);
        });
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



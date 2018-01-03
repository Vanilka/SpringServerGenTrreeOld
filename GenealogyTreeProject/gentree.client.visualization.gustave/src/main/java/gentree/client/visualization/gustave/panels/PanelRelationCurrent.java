package gentree.client.visualization.gustave.panels;

import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationReference;
import gentree.client.visualization.elements.RelationTypeElement;
import gentree.client.visualization.elements.configuration.AutoCleanable;
import gentree.client.visualization.gustave.connectors.ParentToChildrenConnector;
import gentree.common.configuration.enums.RelationType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import lombok.Getter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class PanelRelationCurrent extends SubRelationPane implements RelationPane {

    private final static double MARGIN_TOP = 0.0;
    private final static double MARGIN_LEFT = 0.0;
    private final static double MARGIN_RIGHT = 0.0;
    private final static double MARGIN_BOTTOM = 20.0;

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
    private final ObservableList<PanelChild> children;
    private final RelationReference thisRelationReference;
    private final RelationReference spouseRelationReference;


    private final ObjectProperty<Relation> spouseBornRelation;
    private final ObjectProperty<RelationType> relationType;
    private final ObjectProperty<Member> spouse;
    private final ObjectProperty<Relation> thisRelation;
    private ParentToChildrenConnector childrenConnector;
    private ListChangeListener<? super PanelChild> childrenListListener = this::childrenListChanged;
    private ChangeListener<? super Relation> spouseBornRelationListener = this::spouseBordRelationChanged;
    private ChangeListener<? super Relation> thisRelationListener = this::thisRelationChanged;
    private ChangeListener<? super Member> spouseListener = this::spouseChanged;

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
        super();
        initPanes();
        initListeners();
        this.spouse.setValue(spouse);
        this.thisRelation.setValue(thisRelation);
        this.spouseBornRelation.setValue(spouseBorn);
    }

    public PanelRelationCurrent(Member spouse, Relation thisRelation, Relation spouseBorn, SubBorderPane parent) {
        this(spouse, thisRelation, spouseBorn);
    }


    private void initPanes() {
        initHbox();
        initAnchorPanesOffset();
        this.setCenter(childrenBox);
        this.setTop(relation);
        BorderPane.setAlignment(childrenBox, Pos.TOP_RIGHT);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        setMargin(relation, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));


        relation.setMinHeight(RELATION_HEIGHT);
        relation.setPrefHeight(RELATION_HEIGHT);
        relation.setMinHeight(RELATION_HEIGHT);


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
        spouse.addListener(spouseListener);
        thisRelation.addListener(thisRelationListener);
        spouseBornRelation.addListener(spouseBornRelationListener);
        children.addListener(childrenListListener);
        initElementsPositionListeners();
    }

    private void initElementsPositionListeners() {

        /*
         *  Init bindings
         */

        childrenBox.prefWidthProperty().bind(relation.widthProperty());

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

    private void cleanListeners() {
        spouse.removeListener(spouseListener);
        thisRelation.removeListener(thisRelationListener);
        spouseBornRelation.removeListener(spouseBornRelationListener);
        children.removeListener(childrenListListener);
        cleanElementsPositionListener();
    }

    private void cleanElementsPositionListener() {
        childrenBox.prefWidthProperty().unbind();
        relationTypeElement.layoutYProperty().unbind();
        spouseCard.layoutXProperty().unbind();
        thisRelationReference.layoutXProperty().unbind();
        thisRelationReference.layoutYProperty().unbind();
        spouseRelationReference.layoutXProperty().unbind();
        spouseRelationReference.layoutYProperty().unbind();
        relationTypeElement.layoutXProperty().unbind();
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

    private void setElementsNull() {
        relationTypeElement.setRelation(null);
        children.clear();
        childrenConnector = null;
        thisRelation.setValue(null);
        spouse.setValue(null);

    }

    @Override
    public void clean() {
        super.clean();
        cleanListeners();

        relationTypeElement.clean();
        children.forEach(PanelChild::clean);
        childrenConnector.clean();
        thisRelationReference.clean();
        spouseRelationReference.clean();

        setElementsNull();

        childrenListListener = null;
        spouseBornRelationListener = null;
        thisRelationListener = null;
        spouseListener = null;
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


    @Override
    protected double computePrefWidth(double height) {
        if (children.isEmpty()) return super.computePrefWidth(height);

        Double offset = 0.0;
        Double maxRelationWidth = spouseCard.getLayoutX() + spouseCard.getWidth();
        if (maxRelationWidth > relation.getWidth()) {
            offset = maxRelationWidth - relation.getWidth() + 20;
        }
        return super.computePrefWidth(height) + offset;
    }

    private void childrenListChanged(ListChangeListener.Change<? extends PanelChild> c) {
        //TODO listeners
        while (c.next()) {
            if (c.wasAdded()) {
                childrenBox.getChildren().addAll(c.getAddedSubList());
                c.getAddedSubList().forEach(panelChild -> {
                    panelChild.setParentPane(this);
                    childrenConnector.addPanelChild(panelChild);
                });
            } else if (c.wasRemoved()) {
                childrenBox.getChildren().removeAll(c.getRemoved());
                c.getRemoved().forEach(panelChild -> {
                    childrenConnector.removePanelChild(panelChild);
                    panelChild.clean();
                });
            }
        }
    }

    private void spouseBordRelationChanged(ObservableValue<? extends Relation> observable, Relation oldValue, Relation newValue) {
        spouseRelationReference.setRelation(newValue);
    }

    private void thisRelationChanged(ObservableValue<? extends Relation> observable, Relation oldValue, Relation newValue) {
        relationTypeElement.setRelation(newValue);
        thisRelationReference.setRelation(newValue);
    }

    private void spouseChanged(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
        relation.getChildren().removeAll();
        if (newValue != null) {
            spouseCard.setMember(newValue);
            relation.getChildren().addAll(relationTypeElement, spouseCard, spouseRelationReference, thisRelationReference);
        }
    }


}

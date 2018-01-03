package gentree.client.visualization.gustave.panels;

import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationReference;
import gentree.client.visualization.elements.RelationTypeElement;
import gentree.client.visualization.gustave.connectors.ParentToChildrenConnector;
import gentree.client.visualization.gustave.connectors.SpouseExConnector;
import gentree.common.configuration.enums.RelationType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 * Class to visualization PanelRelation Ex in GenealogyTree
 */
public class PanelRelationEx extends SubRelationPane implements RelationPane {

    private final static double MARGIN_TOP = 0.0;
    private final static double MARGIN_LEFT = 0.0;
    private final static double MARGIN_RIGHT = 0.0;
    private final static double MARGIN_BOTTOM = 20.0;

    private final static double PADDING_TOP = 10.0;
    private final static double PADDING_LEFT = 10.0;
    private final static double PADDING_RIGHT = 10.0;
    private final static double PADDING_BOTTOM = 0.0;

    private final static double MINIMAL_RELATION_WIDTH = 450.0;

    private final static double SPACE_BETWEEN_OBJECTS = 100.0;

    private final Pane relation;

    /*
     * CLEANABLE
     */
    @Getter
    private final FamilyMember spouseCard;

    @Getter
    private final RelationTypeElement relationTypeElement;

    private final ObservableList<PanelChild> children;
    private final ParentToChildrenConnector childrenConnector;
    private final SpouseExConnector spouseExConnector;
    private final RelationReference spouseRelationReference;
    private final RelationReference thisRelationReference;

    /* ******************** */

    private final ObjectProperty<Member> spouse;
    private final ObjectProperty<Relation> thisRelation;
    private final ObjectProperty<Relation> spouseBornRelation;
    private final ObjectProperty<RelationType> relationType;
    private ChangeListener<? super Member> spouseListener = this::spuseChanged;
    private ChangeListener<? super Relation> spouseBornRelationListener = this::spouseBornRelationChanged;
    private ChangeListener<? super Relation> thisRelationListener = this::thisRelationChanged;
    private ListChangeListener<? super PanelChild> childrenListListener = this::childrenListChange;

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
        this(null, null);
    }

    public PanelRelationEx(Member spouse, Relation thisRelation, Relation spouseBornRelation) {
        super();
        initPanes();
        initListeners();
        this.spouse.setValue(spouse);
        this.thisRelation.setValue(thisRelation);
        this.spouseBornRelation.setValue(spouseBornRelation);
    }

    public PanelRelationEx(Member spouse, Relation thisRelation) {
        this(spouse, thisRelation, null);
    }

    private void initPanes() {
        initHbox();
        this.setCenter(childrenBox);
        this.setTop(relation);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        setMargin(relation, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));

        relation.setMinHeight(RELATION_HEIGHT);
        relation.setPrefHeight(RELATION_HEIGHT);
        relation.setMinHeight(RELATION_HEIGHT);
    }

    private void initAnchorPanesOffset() {
        relation.setMinWidth(MINIMAL_RELATION_WIDTH);
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
        spouse.addListener(spouseListener);
        thisRelation.addListener(thisRelationListener);
        spouseBornRelation.addListener(spouseBornRelationListener);
        children.addListener(childrenListListener);
        initElementPositionsListeners();
    }

    private void initElementPositionsListeners() {

        childrenBox.prefWidthProperty().bind(relation.widthProperty());

        relationTypeElement.layoutYProperty().bind(spouseCard.heightProperty().subtract(relationTypeElement.heightProperty()).divide(2));
        spouseCard.layoutXProperty().bind(relationTypeElement.layoutXProperty().subtract(SPACE_BETWEEN_OBJECTS).subtract(spouseCard.widthProperty()));

        thisRelationReference.layoutXProperty().bind(relationTypeElement.layoutXProperty().add(relationTypeElement.widthProperty().subtract(thisRelationReference.widthProperty()).divide(2)));
        thisRelationReference.layoutYProperty().bind(relationTypeElement.layoutYProperty().add(relationTypeElement.heightProperty()));

        spouseRelationReference.layoutXProperty().bind(spouseCard.layoutXProperty().add(spouseCard.widthProperty().subtract(spouseRelationReference.widthProperty()).divide(2)));
        spouseRelationReference.layoutYProperty().bind(spouseCard.layoutYProperty().subtract(spouseRelationReference.heightProperty()));

        relationTypeElement.layoutXProperty().bind(Bindings.when(Bindings.isNotEmpty(children))
                .then(childrenConnector.getLine().startXProperty()
                        .subtract(relationTypeElement.widthProperty().divide(2))
                        .subtract(PADDING_LEFT))
                .otherwise(spouseCard.widthProperty().add(SPACE_BETWEEN_OBJECTS).add(50)));
    }

    private void cleanElementPositionListeners() {
        childrenBox.prefWidthProperty().unbind();
        relationTypeElement.layoutYProperty().unbind();
        spouseCard.layoutXProperty().unbind();
        thisRelationReference.layoutXProperty().unbind();
        thisRelationReference.layoutYProperty().unbind();
        spouseRelationReference.layoutXProperty().unbind();
        spouseRelationReference.layoutYProperty().unbind();
        relationTypeElement.layoutXProperty().unbind();
    }

    private void cleanListeners() {
        spouse.removeListener(spouseListener);
        thisRelation.removeListener(thisRelationListener);
        spouseBornRelation.removeListener(spouseBornRelationListener);
        children.removeListener(childrenListListener);
        cleanElementPositionListeners();
    }

    private void setElementsNull() {
        spouse.setValue(null);
        thisRelation.setValue(null);
        spouseBornRelation.setValue(null);
        relationType.setValue(null);
    }

    @Override
    public void clean() {
        super.clean();
        cleanListeners();

        spouseCard.clean();
        relationTypeElement.clean();
        children.forEach(PanelChild::clean);
        childrenConnector.clean();
        spouseExConnector.clean();
        spouseRelationReference.clean();
        thisRelationReference.clean();

        setElementsNull();

        childrenListListener = null;
        spouseListener = null;
        spouseBornRelationListener = null;
        thisRelationListener = null;


    }


    public final RelationTypeElement getRelationTypeElement() {
        return relationTypeElement;
    }

    @Override
    public Node getConnectionNode() {
        return relationTypeElement;
    }


    @Override
    protected double computeMinWidth(double height) {
        if (children.isEmpty()) return super.computePrefWidth(height);

        Double offset = 0.0;
        if (spouseCard.getLayoutX() < 0) {
            offset = (spouseCard.getLayoutX() * (-1)) + 20;
        }

        return super.computeMinWidth(height) + offset;
    }


    private void childrenListChange(ListChangeListener.Change<? extends PanelChild> c) {
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

    private void spuseChanged(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
        relation.getChildren().removeAll();
        if (newValue != null) {
            spouseCard.setMember(newValue);
            relation.getChildren().addAll(spouseCard, relationTypeElement, spouseRelationReference, thisRelationReference);
        }
    }

    private void spouseBornRelationChanged(ObservableValue<? extends Relation> observable, Relation oldValue, Relation newValue) {
        spouseRelationReference.setRelation(newValue);
    }

    private void thisRelationChanged(ObservableValue<? extends Relation> observable, Relation oldValue, Relation newValue) {
        relationTypeElement.setRelation(newValue);
        thisRelationReference.setRelation(newValue);
    }
}


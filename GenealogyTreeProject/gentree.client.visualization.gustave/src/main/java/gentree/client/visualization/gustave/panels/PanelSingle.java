package gentree.client.visualization.gustave.panels;

import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.visualization.elements.FamilyGroup;
import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationReference;
import gentree.client.visualization.gustave.connectors.ParentToChildrenConnector;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
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
public class PanelSingle extends SubRelationPane implements RelationPane {

    private final static double MARGIN_TOP = 0.0;
    private final static double MARGIN_LEFT = 0.0;
    private final static double MARGIN_RIGHT = 0.0;
    private final static double MARGIN_BOTTOM = 20.0;

    private final static double PADDING_TOP = 10.0;
    private final static double PADDING_LEFT = 10.0;
    private final static double PADDING_RIGHT = 10.0;
    private final static double PADDING_BOTTOM = 0.0;

    private final Pane pane;

    /*  ***************
        Cleanable
     ***************  */
    private final FamilyMember member;
    private final ObjectProperty<Relation> thisRelation;
    private final ObservableList<PanelChild> childrenPanels;
    private final ParentToChildrenConnector childrenConnector;
    private final RelationReference thisRelationReference;

    /* *************** */

    private ListChangeListener<? super PanelChild> childrenListListener = this::childrenListChanged;
    private ChangeListener<? super Relation> thisRelationListener = this::thisRelationChanged;


    /*
        Initialization
     */

    {
        pane = new Pane();
        member = new FamilyMember();
        thisRelation = new SimpleObjectProperty<>();
        thisRelationReference = new RelationReference(RelationReference.RelationReferenceType.DSC);
        childrenPanels = FXCollections.observableArrayList();
        childrenConnector = new ParentToChildrenConnector(this);

    }

    public PanelSingle() {
        this(null, null);
    }

    public PanelSingle(Member m) {
        this(m, null);
    }

    public PanelSingle(Member m, Relation thisRelation) {
        super();
        init();
        this.member.setMember(m);
        this.thisRelation.setValue(thisRelation);
    }


    /*
        Init Panes
     */

    private void init() {
        initPane();
        initListeners();
        initHbox();
        setCenter(childrenBox);
        setTop(pane);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        setMargin(pane, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));

    }

    private void initPane() {
        minWidth(USE_PREF_SIZE);
        maxWidth(USE_PREF_SIZE);
        pane.setPrefSize(180, RELATION_HEIGHT);
        pane.getChildren().addAll(member, thisRelationReference);

        pane.setMinHeight(RELATION_HEIGHT);
        pane.setPrefHeight(RELATION_HEIGHT);
        pane.setMinHeight(RELATION_HEIGHT);
    }

    private void initHbox() {
        childrenBox.setSpacing(10);
    }

    private void initListeners() {
        thisRelation.addListener(thisRelationListener);
        childrenPanels.addListener(childrenListListener);

        initRelationElementsPositionListener();

    }

    private void initRelationElementsPositionListener() {

        thisRelationReference.layoutXProperty().bind(member.layoutXProperty().add((member.widthProperty().subtract(thisRelationReference.widthProperty())).divide(2)));
        thisRelationReference.layoutYProperty().bind(member.layoutYProperty().add(member.heightProperty()));

        member.layoutXProperty().bind(Bindings.when(Bindings.isEmpty(childrenPanels))
                .then(pane.widthProperty().subtract(member.widthProperty()).divide(2))
                .otherwise(childrenConnector.getLine().startXProperty().subtract(member.widthProperty().divide(2)).subtract(PADDING_RIGHT)));
    }

    private void cleanRelationElementsPositionListener() {
        thisRelationReference.layoutXProperty().unbind();
        thisRelationReference.layoutYProperty().unbind();
        member.layoutXProperty().unbind();
    }

    private void cleanListeners() {
        thisRelation.removeListener(thisRelationListener);
        childrenPanels.removeListener(childrenListListener);
        cleanRelationElementsPositionListener();
    }


    public void addMember(Member m) {
        if (m != null) {
            member.setMember(m);
        }
    }

    @Override
    public void clean() {
        super.clean();
        cleanListeners();
        member.clean();
        childrenPanels.forEach(PanelChild::clean);
        childrenConnector.clean();
        thisRelationReference.clean();

        childrenListListener = null;
        thisRelationListener = null;

    }


    @Override
    public void addChild(PanelChild child) {
        childrenPanels.add(child);
        child.setParentPane(this);
    }

    @Override
    public Node getConnectionNode() {
        return member;
    }

    private void childrenListChanged(ListChangeListener.Change<? extends PanelChild> c) {
        while (c.next()) {
            if (c.wasAdded()) {
                childrenBox.getChildren().addAll(c.getAddedSubList());
                c.getAddedSubList().forEach(panelChild -> {
                    panelChild.setParentPane(this);
                    childrenConnector.addPanelChild(panelChild);
                });
            }
            if (c.wasRemoved()) {
                childrenBox.getChildren().removeAll(c.getRemoved());
                c.getRemoved().forEach(panelChild -> {
                    childrenConnector.removePanelChild(panelChild);
                    panelChild.clean();
                });
            }
        }
    }

    private void thisRelationChanged(ObservableValue<? extends Relation> observable, Relation oldValue, Relation newValue) {
        thisRelationReference.setRelation(newValue);
    }

}

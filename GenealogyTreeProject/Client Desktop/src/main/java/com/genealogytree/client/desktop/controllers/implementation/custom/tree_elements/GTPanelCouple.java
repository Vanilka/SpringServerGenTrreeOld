package com.genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanel;
import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanelSim;
import com.genealogytree.client.desktop.controllers.implementation.custom.connectors.GTConnectorChildren;
import com.genealogytree.client.desktop.controllers.implementation.custom.connectors.GTConnectorSpouse;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 29/03/2017.
 *
 * Top Hbox contains leaf, relationType, spouse
 * Left
 * Center
 * Right
 * Bottom  Children
 *
 */
@Getter
@Setter
public class GTPanelCouple extends GTPanelCurrent implements GTPanelSim, GTPanel {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private HBox relation;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private AnchorPane leafPane;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private AnchorPane spousePane;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private StackPane relationTypePane;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private HBox childrenHbox;


    private GTRelationReference relationRef;



    private ObservableList<GTPanelChild> childrenPanelList;
    private ObjectProperty<GTLeaf> leaf;
    private ObjectProperty<GTLeaf> spouse;
    private ObjectProperty<GTRelationType> relationType;
    private BooleanProperty isActive;
    private GTConnectorSpouse connectorSpouse;
    private List<GTConnectorChildren> connectorChildrenList;


    {
        init();
    }



    public GTPanelCouple(GTLeaf leaf, GTLeaf spouse) {

        this(leaf, spouse, new GTRelationType(),null);
    }

    public GTPanelCouple(GTLeaf leaf, GTLeaf spouse, GTRelationType relationType) {

        this(leaf, spouse, relationType,null);
    }


    public GTPanelCouple(GTLeaf leaf, GTLeaf spouse, GTRelationType type, ObservableList<GTPanelChild> childrenPanelList) {

        autoresize();
        setLeaf(leaf);
        setSpouse(spouse);
        setRelationType(type);

        if (childrenPanelList != null) {
            this.childrenPanelList = childrenPanelList;
        }
        setCenter(childrenHbox);
        setTop(relation);
    }


    @Override
    public void setGTPanelChild(GTPanelChild panelChild) {
        childrenPanelList.add(panelChild);
    }

    @Override
    public void setGTPanelChildList(ObservableList<GTPanelChild> panelChildList) {
        childrenPanelList.addAll(panelChildList);
    }

    private void initListeners() {
        childrenPanelList.addListener((ListChangeListener<? super GTPanelChild>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    childrenHbox.getChildren().addAll(c.getAddedSubList());

                    c.getAddedSubList().forEach(child -> {

                    });
                } else if (c.wasRemoved()) {
                    childrenHbox.getChildren().removeAll(c.getRemoved());
                } else {
                }
            }
        });

        leaf.addListener((observable, oldValue, newValue) -> {
            leafPane.getChildren().clear();
            leafPane.getChildren().addAll(newValue);
        });

        spouse.addListener((observable, oldValue, newValue) -> {

            if (oldValue != null) {
                spousePane.getChildren().remove(oldValue);

            }

            if (newValue == null) {
                spousePane.getChildren().clear();
            } else {
                spousePane.getChildren().add(newValue);
            }

            relationsElementsLocate();
        });


        relationType.addListener((observable, oldValue, newValue) -> {
            if(newValue == null ) {
                relationTypePane.getChildren().clear();
            } else {
                relationTypePane.getChildren().add(newValue);
            }
        });

    }


    private void init() {
        this.relationType = new SimpleObjectProperty<>();
        this.relationRef = new GTRelationReference();
        this.childrenPanelList = FXCollections.observableArrayList();
        this.leaf = new SimpleObjectProperty<>();
        this.spouse = new SimpleObjectProperty<>();
        this.isActive = new SimpleBooleanProperty(true);
        initNodes();
    }

    private void initNodes() {
        this.childrenHbox = new HBox();
        this.spousePane = new AnchorPane();
        this.relationTypePane = new StackPane();
        this.leafPane = new AnchorPane();
        this.relation = new HBox();
        initListeners();
        childrenHbox.setAlignment(Pos.CENTER);
        childrenHbox.setSpacing(50);
        relation.getChildren().addAll(leafPane, relationTypePane, spousePane);

        // Hide spousePane if spouse leaf is null
        BooleanBinding visibleBinding = Bindings.createBooleanBinding(() -> (spouse.getValue() != null), spouse);
        spousePane.visibleProperty().bind(visibleBinding);

        relationPaneLayout();

    }

    private void relationsElementsLocate() {
        relation.getChildren().clear();
        relation.getChildren().add(leafPane);

        if (spouse != null) {
            relation.getChildren().addAll(relationTypePane, spousePane);
        }
    }

    private void relationPaneLayout() {
        relation.setAlignment(Pos.CENTER);
        relation.setSpacing(40);
    }

    private void autoresize() {
        this.prefWidthProperty().bind(childrenHbox.widthProperty());
        childrenHbox.maxHeight(Double.MAX_VALUE);
        childrenHbox.maxWidth(Double.MAX_VALUE);
    }


    public void setLeaf(GTLeaf leaf) {
        this.leaf.set(leaf);
    }

    public void setSpouse(GTLeaf spouse) {
        this.spouse.set(spouse);
    }

    public void setRelationType(GTRelationType relationType) {
        this.relationType.set(relationType);
    }

    public void addPanelChild(GTPanelChild child) {
        this.childrenPanelList.add(child);
    }

    public List<GTLeaf> getLeafs() {
        List<GTLeaf> result = new ArrayList<>();

        childrenPanelList.forEach(p -> result.add(p.getLeaf()));
        return result;
    }

    public void setIsActive(boolean isActive) {
        this.isActive.set(isActive);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GTPanelCouple{");
        sb.append("childrenPanelList=").append(childrenPanelList);
        sb.append(", leaf=").append(leaf);
        sb.append(", spouse=").append(spouse);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public ReadOnlyObjectProperty<GTLeaf> returnLeaf() {
        return leaf;
    }
}

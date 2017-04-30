package com.genealogytree.client.desktop.controllers.implementation.custom;

import com.genealogytree.client.desktop.domain.GTX_Relation;
import com.genealogytree.domain.enums.RelationType;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 29/03/2017.
 */
@Getter
@Setter
public class GTPanelSim extends BorderPane {

    private HBox relation;
    private AnchorPane leafPane;
    private AnchorPane spousePane;
    private GTRelationReference relationRef;
    private GTRelationType relationType;
    private HBox box;


    private ObservableList<GTPanelChild> childrenPanel;
    private ObjectProperty<GTLeaf> leaf;
    private ObjectProperty<GTLeaf> spouse;
    private GTConnectorSpouse connectorSpouse;
    private List<GTConnectorChildren> connectorChildrenList;


    {
        init();

    }


    public GTPanelSim() {
        this(null, null , null);

    }


    public GTPanelSim(GTLeaf leaf) {
        this(leaf, null, null);
    }
    public GTPanelSim(GTLeaf leaf, GTLeaf spouse, ObservableList<GTPanelChild> childrenPanel) {

        autoresize();

        setLeaf(leaf);
        setSpouse(spouse);

        if(childrenPanel != null) {
            this.childrenPanel = childrenPanel;
        }
        setCenter(box);
        setTop(relation);
    }


    private void initListeners() {
        childrenPanel.addListener((ListChangeListener<GTPanelChild>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    box.getChildren().addAll(c.getAddedSubList());
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
    }


    private void init() {
        this.relationType = new GTRelationType();
        this.relationRef = new GTRelationReference();
        this.childrenPanel = FXCollections.observableArrayList();
        this.leaf = new SimpleObjectProperty<>();
        this.spouse = new SimpleObjectProperty<>();
        initNodes();
    }

    private void initNodes() {
        this.box = new HBox();
        this.spousePane = new AnchorPane();
        this.leafPane = new AnchorPane();
        this.relation = new HBox();
        initListeners();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50);
        relation.getChildren().addAll(leafPane);

        // Hide spousePane if spouse leaf is null
        BooleanBinding visibleBinding = Bindings.createBooleanBinding(() -> (spouse.getValue() != null), spouse);
        spousePane.visibleProperty().bind(visibleBinding);

        relationPaneLayout();

    }
    private void relationsElementsLocate() {
        relation.getChildren().clear();
        relation.getChildren().add(leafPane);

        if(spouse != null) {
            relation.getChildren().addAll(relationType, spousePane);
        }
    }

    private void relationPaneLayout() {
        relation.setAlignment(Pos.CENTER);
        relation.setSpacing(40);
    }

    private void autoresize() {
        this.prefWidthProperty().bind(box.widthProperty());
        box.maxHeight(Double.MAX_VALUE);
        box.maxWidth(Double.MAX_VALUE);
    }


    public void setLeaf(GTLeaf leaf) {
        this.leaf.set(leaf);
    }

    public void setSpouse(GTLeaf spouse) {
        this.spouse.set(spouse);
    }

    public void addPanelChild(GTPanelChild child) {
        this.childrenPanel.add(child);
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GTPanelSim{");
        sb.append("childrenPanel=").append(childrenPanel);
        sb.append(", leaf=").append(leaf);
        sb.append(", spouse=").append(spouse);
        sb.append('}');
        return sb.toString();
    }
}

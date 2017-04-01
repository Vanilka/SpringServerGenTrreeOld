package com.genealogytree.client.desktop.controllers.implementation.custom;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 29/03/2017.
 */
@Getter
@Setter
public class GTPanelSim extends BorderPane {

    private AnchorPane relation;
    private AnchorPane leafPane;
    private AnchorPane spousePane;
    private HBox box;


    private ObservableList<GTPanelChild> childrenPanel;
    private ObjectProperty<GTLeaf> leaf;
    private ObjectProperty<GTLeaf> spouse;

    {
        init();
        initListeners();
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
    }


    private void initListeners() {
        childrenPanel.addListener((ListChangeListener<GTPanelChild>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    System.out.println("child added");
                    box.getChildren().addAll(c.getAddedSubList());
                }
            }
        });


        leaf.addListener((observable, oldValue, newValue) -> {
            System.out.println("leaf");
            leafPane.getChildren().clear();
            leafPane.getChildren().addAll(newValue);
        });

        spouse.addListener((observable, oldValue, newValue) -> {
            System.out.println("spouse");

            if (oldValue != null) {
                spousePane.getChildren().remove(oldValue);
            }

            if (newValue == null) {
                spousePane.getChildren().clear();
            } else {
                spousePane.getChildren().add(newValue);
            }
        });
    }


    private void init() {
        this.childrenPanel = FXCollections.observableArrayList();
        this.leaf = new SimpleObjectProperty<>();
        this.spouse = new SimpleObjectProperty<>();
        initNodes();
    }

    private void initNodes() {
        this.box = new HBox();
        this.spousePane = new AnchorPane();
        this.leafPane = new AnchorPane();
        this.relation = new AnchorPane();

        relation.getChildren().addAll(leafPane, spousePane);

        // Hide spousePane if spouse leaf is null
        BooleanBinding visibleBinding = Bindings.createBooleanBinding(() -> (spouse.getValue() != null), spouse);
        spousePane.visibleProperty().bind(visibleBinding);
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

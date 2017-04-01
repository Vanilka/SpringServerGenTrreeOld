package com.genealogytree.client.desktop.controllers.implementation.custom;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 30/03/2017.
 */
@Getter
@Setter
public class GTPanelChild extends BorderPane {

    private StackPane pane;
    private ObjectProperty<GTLeaf> leaf;
    private ObservableList<GTPanelSim> panels;

    {
        init();
    }

    public GTPanelChild(GTLeaf leaf) {
        this(leaf, null);

    }

    public GTPanelChild(GTLeaf leaf, ObservableList<GTPanelSim> panels) {
        this.leaf.setValue(leaf);

        if (panels != null && !panels.isEmpty()) {
            this.panels.addAll(panels);
        }

        bpTestInit(pane);
        setCenter(pane);
    }


    private void init() {
        pane = new StackPane();
        leaf = new SimpleObjectProperty<>();
        panels = FXCollections.observableArrayList();
        initListeners();


    }

    private void initListeners() {

        // GTPanel observer
        panels.addListener((ListChangeListener<GTPanelSim>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    System.out.println("added Panel");
                }
            }
        });

        BooleanBinding panelEmpty = Bindings.createBooleanBinding(() -> (panels == null || panels.isEmpty()), panels);

        panelEmpty.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                pane.getChildren().add(leaf.get());
                System.out.println("panel empty");
            } else {
                System.out.println("panels not empty");
            }
        });


        // Leaf observer
        leaf.addListener((observable, oldValue, newValue) -> {
            if (panelEmpty.get()) {
                pane.getChildren().remove(oldValue);
                pane.getChildren().add(newValue);
            }
        });
    }


    private void bpTestInit(StackPane pane) {
        pane.setAlignment(Pos.CENTER);
        pane.setBorder(new Border(new BorderStroke(Color.BLUE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public ObjectProperty<GTLeaf> leafProperty() {
        return leaf;
    }

    public GTLeaf getLeaf() {
        return leaf.get();
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GTPanelChild{");
        sb.append("leaf=").append(leaf);
        sb.append(", panels=").append(panels);
        sb.append(", leafProperty=").append(leafProperty());
        sb.append('}');
        return sb.toString();
    }
}



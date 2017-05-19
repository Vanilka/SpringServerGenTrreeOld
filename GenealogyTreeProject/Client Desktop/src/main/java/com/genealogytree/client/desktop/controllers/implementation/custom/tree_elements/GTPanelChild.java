package com.genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanel;
import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanelSim;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 30/03/2017.
 * An graphical object representing the child relation id GenealogyTree.
 * For Each Child in relation the GTPanelChild is generated
 * @author Martyna SZYMKOWIAK
 *
 * Top
 * Center HboxPane
 * Right panelCurrent
 * Left
 *
 */
@Getter
@Setter
public class GTPanelChild extends BorderPane implements GTPanel {


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private HBox pane;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private BorderPane paneCurrent;


    private ObjectProperty<GTLeaf> leaf;
    private ObservableList<GTPanelEx> panels;
    private ObjectProperty<GTPanelCurrent> panelCurrent;

    private GTPanel parentPanel;

    private Bounds boundToParent;

    {
        init();
        setPadding(new Insets(100, 0, 0, 0));


    }

    public GTPanelChild(GTLeaf leaf) {
        this(leaf, null);

    }

    public GTPanelChild(GTLeaf leaf, GTPanelSim parent) {
        viewInit(pane);
        viewInit(paneCurrent);
        this.leaf.setValue(leaf);


        if (panels != null && !panels.isEmpty()) {
            this.panels.addAll(panels);
       }

        setCenter(pane);
        setRight(paneCurrent);
    }



    private void init() {
        pane = new HBox();
        paneCurrent = new BorderPane();
        leaf = new SimpleObjectProperty<>();
        panelCurrent = new SimpleObjectProperty<>();
        panels = FXCollections.observableArrayList();
        pane.getChildren().addAll(panels);
        initListeners();
        paneLayout();

    }

    private void initListeners() {

        //
        panels.addListener((ListChangeListener<GTPanelEx>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    System.out.println("added Panel");
                    pane.getChildren().addAll(c.getAddedSubList());
                }
            }
        });

        panelCurrent.addListener((observable, oldValue, newValue) -> {
            if(oldValue != null) {
                System.out.println("OLD VALUE WAS NULL");
                paneCurrent.getChildren().remove(oldValue);
            }

            if(newValue != null) {
                System.out.println("THIS IS NEW VALUE");
                paneCurrent.setCenter(newValue);
            }
        });



        BooleanBinding panelEmpty = Bindings.createBooleanBinding(() -> (panels == null || panels.isEmpty()) || panelCurrent == null, panels, panelCurrent);

        panelEmpty.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
              //DO SOMETHING
            }
        });

    }


    private void viewInit(HBox pane) {
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setSpacing(50);
        pane.setBorder(new Border(new BorderStroke(Color.BLUE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    private void viewInit(BorderPane pane) {
        pane.setBorder(new Border(new BorderStroke(Color.DARKCYAN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public ObjectProperty<GTLeaf> leafProperty() {
        return leaf;
    }

    public GTLeaf getLeaf() {
        return leaf.get();
    }

    private void paneLayout() {


    }

    public ObjectProperty<GTPanelCurrent> panelCurrentProperty() {
        return panelCurrent;
    }

    public void setPanelCurrent(GTPanelCurrent panelCurrent) {
        this.panelCurrent.set(panelCurrent);
    }

    public ReadOnlyObjectProperty<GTLeaf> returnLeaf() {
        return panelCurrent.get().returnLeaf();
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



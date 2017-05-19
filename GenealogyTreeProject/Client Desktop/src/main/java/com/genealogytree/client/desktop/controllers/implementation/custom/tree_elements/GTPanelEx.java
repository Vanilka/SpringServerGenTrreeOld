package com.genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanel;
import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanelSim;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 14/05/2017.
 */
@Getter
@Setter
public class GTPanelEx extends BorderPane implements GTPanelSim, GTPanel {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private HBox box;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private AnchorPane spousePane;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private StackPane relationPane;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private HBox childrenbox;


    private ObjectProperty<GTLeaf> leaf;
    private ObjectProperty<GTLeaf> spouse;
    private ObjectProperty<GTRelationType> gtRelationType;
    private ObservableList<GTPanelChild> childrenPanelList;

    private GTPanel parentPanel;

    public GTPanelEx() {
        this(null, null, null, null);
    }

    public GTPanelEx(GTLeaf leaf, GTLeaf spouse, GTRelationType relationType, GTPanel parent) {
        init();
        this.parentPanel = parent;
        this.leaf.setValue(leaf);
        this.spouse.setValue(spouse);
        this.gtRelationType.setValue(relationType);
    }


    @Override
    public void setGTPanelChild(GTPanelChild panelChild) {
        childrenPanelList.add(panelChild);
    }

    @Override
    public void setGTPanelChildList(ObservableList<GTPanelChild> panelChildList) {
        childrenPanelList.addAll(panelChildList);
    }


    private void init() {
        initNodes();
        leaf = new SimpleObjectProperty<>();
        spouse = new SimpleObjectProperty<>();
        gtRelationType = new SimpleObjectProperty<>();
        childrenPanelList = FXCollections.observableArrayList();
        box.getChildren().addAll(spousePane, relationPane);
        box.setAlignment(Pos.CENTER_LEFT);
        initListeners();
    }

    private void initNodes() {
        initHbox();
        initRelationStackPane();
        spousePane = new AnchorPane();
        setTop(box);
        autoresize();
    }

    private void initListeners() {
        spouse.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                spousePane.getChildren().clear();
            }

            if (newValue != null) {
                spousePane.getChildren().addAll(newValue);
            } else {
                relationPane.getChildren().clear();
            }
        });

        gtRelationType.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                relationPane.getChildren().clear();
            }
            if (newValue != null) {
                relationPane.getChildren().add(newValue);
            } else {
                relationPane.getChildren().clear();
            }
        });

        childrenPanelList.addListener((ListChangeListener<? super GTPanelChild>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    childrenbox.getChildren().addAll(c.getAddedSubList());
                } else if (c.wasRemoved()) {
                    childrenbox.getChildren().removeAll(c.getRemoved());
                } else {

                }
            }
        });
    }

    private void initHbox() {
        box = new HBox();
        box.setSpacing(50);
    }

    private void initRelationStackPane() {
        relationPane = new StackPane();
        relationPane.setAlignment(Pos.CENTER);
    }


    private void autoresize() {
        this.prefWidthProperty().bind(box.widthProperty());
        box.maxHeight(Double.MAX_VALUE);
        box.maxWidth(Double.MAX_VALUE);
    }


}

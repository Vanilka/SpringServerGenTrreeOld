package com.genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanel;
import com.genealogytree.client.desktop.controllers.implementation.custom.GTPanelSim;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 14/05/2017.
 */
@Getter
@Setter
public class GTPanelCurrent extends GTPanelCommon implements GTPanelSim {

    protected GTPanel parentPanel;

    @Override
    public void setGTPanelChild(GTPanelChild panelChild) {

    }

    @Override
    public void setGTPanelChildList(ObservableList<GTPanelChild> panelChildList) {

    }

    public ReadOnlyObjectProperty<GTLeaf> returnLeaf() {
        return null;
    }




}

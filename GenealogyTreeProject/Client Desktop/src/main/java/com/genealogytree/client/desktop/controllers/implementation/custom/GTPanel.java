package com.genealogytree.client.desktop.controllers.implementation.custom;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Martyna SZYMKOWIAK on 14/05/2017.
 */
public interface GTPanel {

    GTPanel getParentPanel();

    void setParentPanel(GTPanel parentPanel);


}

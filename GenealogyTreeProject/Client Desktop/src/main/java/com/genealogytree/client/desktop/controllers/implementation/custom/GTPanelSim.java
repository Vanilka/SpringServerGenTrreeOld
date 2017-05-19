package com.genealogytree.client.desktop.controllers.implementation.custom;

import com.genealogytree.client.desktop.controllers.implementation.custom.tree_elements.GTLeaf;
import com.genealogytree.client.desktop.controllers.implementation.custom.tree_elements.GTPanelChild;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Martyna SZYMKOWIAK on 14/05/2017.
 */
public interface GTPanelSim {



    void setGTPanelChild(GTPanelChild panelChild);

    void setGTPanelChildList(ObservableList<GTPanelChild> panelChildList);


}

package com.genealogytree.application;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;

/**
 * Created by vanilka on 22/11/2016.
 */
public interface FXMLTabController extends FXMLPaneController {


    Tab getTab();

    void setTab(Tab tab);

    JFXTabPane getTabPane();

    void setTabPane(JFXTabPane tabPane);

    void setTabAndTPane(JFXTabPane tabPane, Tab tab);

}

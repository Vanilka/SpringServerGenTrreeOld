/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.client.desktop.controllers.implementation;

import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.controllers.FXMLPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author vanilka
 */
public class PaneMainWindowController implements Initializable, FXMLPane {

    private static final Logger LOG = LogManager.getLogger(PaneMainWindowController.class);
    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();

    @FXML
    private ResourceBundle languageBundle;

    @FXML
    private BorderPane rootWindow;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.info("Initialisation :  " + this.toString());
        this.languageBundle = rb;

    }

    public BorderPane getRootWindow() {
        return this.rootWindow;
    }

    public void setRootWindow(BorderPane rootWindow) {
        this.rootWindow = rootWindow;
    }

}

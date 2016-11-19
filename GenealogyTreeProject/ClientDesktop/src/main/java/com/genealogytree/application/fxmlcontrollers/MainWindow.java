/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
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
public class MainWindow implements Initializable, FXMLController {

    private static final Logger LOG = LogManager.getLogger(MainWindow.class);
    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private ResourceBundle languageBundle;

    @FXML
    private BorderPane rootWindow;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());
        this.languageBundle = rb;

    }

    public BorderPane getRootWindow() {
        return this.rootWindow;
    }

    public void setRootWindow(BorderPane rootWindow) {
        this.rootWindow = rootWindow;
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }

    public void setContext(GenealogyTreeContext context) {
        this.context = context;
    }
}

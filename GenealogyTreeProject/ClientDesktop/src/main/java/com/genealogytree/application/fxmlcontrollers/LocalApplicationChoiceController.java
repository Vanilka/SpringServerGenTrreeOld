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
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
public class LocalApplicationChoiceController implements Initializable, FXMLController {

    private static final Logger LOG = LogManager.getLogger(LocalApplicationChoiceController.class);
    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private Button buttonik;
    @FXML
    private Pane mainPane;

    @FXML
    private ResourceBundle languageBundle;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());
        this.languageBundle = rb;
    }

    @FXML
    public void selectLocalApplication() {
        System.out.println("LocalApplication was be selected.");
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
    }
}

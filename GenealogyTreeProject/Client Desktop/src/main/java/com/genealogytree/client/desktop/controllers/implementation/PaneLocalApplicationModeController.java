/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.client.desktop.controllers.implementation;


import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.configuration.enums.FXMLFile;
import com.genealogytree.client.desktop.controllers.FXMLPane;
import com.genealogytree.client.desktop.service.implementation.LocalFamilyService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class PaneLocalApplicationModeController implements Initializable, FXMLPane {

    private static final Logger LOG = LogManager.getLogger(PaneLocalApplicationModeController.class);
    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();

    @FXML
    private Button buttonik;

    @FXML
    private Pane mainPane;

    @FXML
    private Label localApplicationLabel;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LOG.info("Initialisation :  " + this.toString());
        this.languageBundle.setValue(rb);
        addLanguageListener();
    }

    @FXML
    public void selectLocalApplication() {
        this.context.setService(new LocalFamilyService());
        sc.showNewDialog(new DialogNewProjectController(), FXMLFile.NEW_PROJECT_DIALOG);
    }

    /*
     * LISTEN LANGUAGE CHANGES
    */
    private void addLanguageListener() {
        this.languageBundle.addListener(new ChangeListener<ResourceBundle>() {
            @Override
            public void changed(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue,
                                ResourceBundle newValue) {
                reloadElements();
            }
        });
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        this.localApplicationLabel.setText(getValueFromKey("application_choice_local").toUpperCase());
    }


    /*
     * GETTERS AND SETTERS
     */


}


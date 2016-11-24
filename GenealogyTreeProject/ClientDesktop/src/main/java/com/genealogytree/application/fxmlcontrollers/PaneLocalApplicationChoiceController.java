/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLPaneController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.configuration.FXMLFiles;
import com.genealogytree.services.implementation.GTFamilyServiceOffline;
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
public class PaneLocalApplicationChoiceController implements Initializable, FXMLPaneController {

    private static final Logger LOG = LogManager.getLogger(PaneLocalApplicationChoiceController.class);
    private ScreenManager manager;
    private GenealogyTreeContext context;

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
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());

        this.languageBundle.setValue(rb);
    }

    @FXML
    public void selectLocalApplication() {
        this.context.setFamilyService(new GTFamilyServiceOffline());
        this.manager.showNewDialog(new DialogNewProjectController(), FXMLFiles.NEW_PROJECT_DIALOG.toString());
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

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
    }

}


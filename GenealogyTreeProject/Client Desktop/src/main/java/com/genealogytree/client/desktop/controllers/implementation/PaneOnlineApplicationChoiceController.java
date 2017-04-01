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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
public class PaneOnlineApplicationChoiceController implements Initializable, FXMLPane {

    private static final Logger LOG = LogManager.getLogger(PaneOnlineApplicationChoiceController.class);
    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private Label onlineApplicationLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.info("Initialisation :  " + this.toString());
        this.languageBundle.setValue(rb);
        addLanguageListener();
    }

    @FXML
    public void selectOnlineApplication() {
        this.sc.loadFxml(new PaneLogonWindowController(), this.sc.getRootBorderPane(), FXMLFile.LOGON_WINDOW, ScreenManager.Where.CENTER);
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
        this.onlineApplicationLabel.setText(getValueFromKey("application_choice_online").toUpperCase());
    }

    /*
     * GETTERS AND SETTERS
     */
}


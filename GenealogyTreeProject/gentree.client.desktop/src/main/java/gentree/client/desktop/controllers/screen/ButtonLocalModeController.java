/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.Keys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLPane;
import gentree.client.desktop.service.implementation.GenTreeLocalService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
@Log4j2
public class ButtonLocalModeController implements Initializable, FXMLPane, FXMLController {

    //TODO  LOCAL / ONLINE MODE IMAGES
    //TODO check possiblity to remove listeners
    @FXML
    private Pane MAIN_PANE;

    @FXML
    private Label LOCAL_APPLICATION_LABEL;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();


    private ChangeListener<ResourceBundle> bundleChangeListener = this::bundleChange;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);

        this.languageBundle.setValue(rb);
        addLanguageListener();

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    @FXML
    public void selectLocalApplication() {
        context.setService(new GenTreeLocalService());
        sm.showNewDialog(new DialogOpenProjectController(), FilesFXML.OPEN_PROJECT_DIALOG);
    }

    /*
     * LISTEN LANGUAGE CHANGES
     */
    private void addLanguageListener() {
        this.languageBundle.addListener(bundleChangeListener);
    }

    private void cleanListeners() {
        this.languageBundle.removeListener(bundleChangeListener);
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        this.LOCAL_APPLICATION_LABEL.setText(getValueFromKey(Keys.APPLICATION_CHOICE_LOCAL).toUpperCase());
    }

    private void bundleChange(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        reloadElements();
    }


    /*
     * GETTERS AND SETTERS
     */


}


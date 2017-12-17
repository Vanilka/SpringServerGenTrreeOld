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
import gentree.client.desktop.service.ScreenManager;
import gentree.client.desktop.service.implementation.GenTreeOnlineService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
@Log4j2
public class ButtonOnlineModeController implements Initializable, FXMLPane, FXMLController {

    //TODO check possiblity to remove listeners

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private Label ONLINE_APPLICATION_LABEL;

    private ChangeListener<ResourceBundle> bundleChangeListener = this::languageChanged;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(rb);
        addLanguageListener();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    @FXML
    public void selectOnlineApplication() {
        context.setService(new GenTreeOnlineService());
        this.sm.loadFxml(new ScreenLoginRegisterController(), this.sm.getMainWindowBorderPane(), FilesFXML.SCREEN_LOGON_REGISTER_FXML, ScreenManager.Where.CENTER);
    }

    /*
     * LISTEN LANGUAGE CHANGES
     */


    private void addLanguageListener() {
        this.languageBundle.addListener(bundleChangeListener);
    }

    private void removListeners() {
        this.languageBundle.removeListener(bundleChangeListener);
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        this.ONLINE_APPLICATION_LABEL.setText(getValueFromKey(Keys.APPLICATION_CHOICE_ONLINE).toUpperCase());
    }

    private void languageChanged(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        reloadElements();
    }

    /*
     * GETTERS AND SETTERS
     */
}


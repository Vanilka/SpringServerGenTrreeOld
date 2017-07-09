/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configurations.messages.Keys;
import gentree.client.desktop.configurations.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLPane;
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



    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private Label onlineApplicationLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(rb);
        addLanguageListener();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    @FXML
    public void selectOnlineApplication() {
        //this.sm.loadFxml(new PaneLogonWindowController(), this.sc.getRootBorderPane(), FXMLFile.LOGON_WINDOW, ScreenManager.Where.CENTER);
    }

    /*
     * LISTEN LANGUAGE CHANGES
     */
    private void addLanguageListener() {
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        this.onlineApplicationLabel.setText(getValueFromKey(Keys.APPLICATION_CHOICE_ONLINE).toUpperCase());
    }

    /*
     * GETTERS AND SETTERS
     */
}


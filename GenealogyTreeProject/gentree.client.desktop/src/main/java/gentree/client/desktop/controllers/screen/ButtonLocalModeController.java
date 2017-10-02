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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        this.localApplicationLabel.setText(getValueFromKey(Keys.APPLICATION_CHOICE_LOCAL).toUpperCase());
    }


    /*
     * GETTERS AND SETTERS
     */


}


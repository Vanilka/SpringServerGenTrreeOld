package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXToggleButton;
import gentree.client.desktop.configurations.GenTreeDefaultProperties;
import gentree.client.desktop.configurations.GenTreeProperties;
import gentree.client.desktop.configurations.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 30/07/2017.
 */

@Log4j2
public class DialogAppPropertiesTreeController implements Initializable, FXMLController, FXMLAnchorPane {

    @Getter
    private HashMap<String, String> properties;

    @FXML
    private JFXToggleButton toggleAllowedHomo;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        initListeners();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }




    private void populateProperties() {
        toggleAllowedHomo.setSelected(Boolean.valueOf(properties.get(GenTreeDefaultProperties.PARAM_DEFAULT_ALLOW_HOMO)));
    }

    /*
        Listeners
     */
    private void initListeners() {
        initToggleAllowedHomoListener();
    }

    private void initToggleAllowedHomoListener() {
        toggleAllowedHomo.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                toggleAllowedHomo.setText("ALLOWED");
            } else {
                toggleAllowedHomo.setText("NOT ALLOWED");
            }
            properties.replace(GenTreeDefaultProperties.PARAM_DEFAULT_ALLOW_HOMO, newValue.toString());
        });
    }


    /*
        SETTER
     */

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
        populateProperties();
    }
}
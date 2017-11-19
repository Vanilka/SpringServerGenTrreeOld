package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXToggleButton;
import gentree.client.desktop.configuration.enums.PropertiesKeys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 30/07/2017.
 */

@Log4j2
public class DialogAppPropertiesTreeController implements Initializable, FXMLController, FXMLAnchorPane {

    @Getter
    private HashMap<String, String> properties;

    @FXML
    private JFXToggleButton TOGGLE_ALLOWED_HOMO;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private ChangeListener<? super Boolean> toggleHomoListener = this::toogleHomoChange;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        initListeners();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }


    private void populateProperties() {
        TOGGLE_ALLOWED_HOMO.setSelected(Boolean.valueOf(properties.get(PropertiesKeys.PARAM_DEFAULT_ALLOW_HOMO)));
    }

    /*
        Listeners
     */
    private void initListeners() {
        TOGGLE_ALLOWED_HOMO.selectedProperty().addListener(toggleHomoListener);
    }

    public void cleanListeners() {
        TOGGLE_ALLOWED_HOMO.selectedProperty().removeListener(toggleHomoListener);
    }

    private void toogleHomoChange(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            TOGGLE_ALLOWED_HOMO.setText("ALLOWED");
        } else {
            TOGGLE_ALLOWED_HOMO.setText("NOT ALLOWED");
        }
        properties.replace(PropertiesKeys.PARAM_DEFAULT_ALLOW_HOMO, newValue.toString());
    }

    /*
        SETTER
     */

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
        populateProperties();
    }

}
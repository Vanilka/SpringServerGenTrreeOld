package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.configuration.Realm;
import gentree.client.desktop.configuration.RealmConfig;
import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.service.ScreenManager;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 30/07/2017.
 */

@Log4j2
public class DialogAppPropertiesOnlineController implements Initializable, FXMLController, FXMLAnchorPane {

    private final ScreenManager sm = ScreenManager.INSTANCE;

    @Getter
    private final RealmConfig realmConfig = GenTreeProperties.INSTANCE.getRealmConfig().copy();

    @FXML
    private ListView<Realm> REALM_LIST;

    @FXML
    private JFXButton NEW_REALM_BUTTON;

    @FXML
    private JFXButton EDIT_BUTTON;

    @FXML
    private JFXButton DELETE_BUTTON;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();


    @FXML
    private void addNewRealm(ActionEvent event) {

        sm.showNewDialog(new DialogAddRealmController(), realmConfig.getRealms(), FilesFXML.DIALOG_ADD_REALM);
    }

    @FXML
    private void deleteRealm(ActionEvent event) {
        REALM_LIST.getItems().remove(REALM_LIST.getSelectionModel().getSelectedItem());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);

        populateRealmList();
        initButtonDisableListener();

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    public void cleanListeners() {

    }

    private void initButtonDisableListener() {
        BooleanBinding binding = Bindings.createBooleanBinding(
                () -> REALM_LIST.getSelectionModel().getSelectedItem() == null,
                REALM_LIST.getSelectionModel().selectedItemProperty());
        EDIT_BUTTON.disableProperty().bind(binding);
        DELETE_BUTTON.disableProperty().bind(binding);
    }


    private void populateRealmList() {
        REALM_LIST.setCellFactory(sm.getCustomRealmListCell());
        REALM_LIST.setItems(realmConfig.getRealms());
    }

}
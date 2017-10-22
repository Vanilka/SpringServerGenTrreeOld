package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.configuration.Realm;
import gentree.client.desktop.configuration.RealmConfig;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FilesFXML;
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

    @Getter
    private final RealmConfig realmConfig = GenTreeProperties.INSTANCE.getRealmConfig().copy();


    @FXML
    ListView<Realm> REALM_LIST;

    @FXML
    JFXButton NEW_REALM;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();



    @FXML
    public void addNewRealm(ActionEvent event) {

        sm.showNewDialog(new DialogAddRealmController(), realmConfig.getRealms(), FilesFXML.DIALOG_ADD_REALM);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);

        populateRealmList();

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }


    private void  populateRealmList() {
        REALM_LIST.setItems(realmConfig.getRealms());
    }

}
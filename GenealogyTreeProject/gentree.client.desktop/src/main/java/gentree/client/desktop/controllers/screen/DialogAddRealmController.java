package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import gentree.client.desktop.configuration.Realm;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLDialogWithRealmListControl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */
@Log4j2
public class DialogAddRealmController implements Initializable, FXMLDialogWithRealmListControl {


    private ObservableList<Realm> realmList;

    @FXML
    private JFXButton BUTTON_CONFIRM;

    @FXML
    private JFXButton BUTTON_CANCEL;

    @FXML
    private JFXButton TEST_CONNECTION;

    @FXML
    private TextField SERVER_ADDRESS_FIELD;

    @FXML
    private TextField SERVER_NAME_FIELD;

    private Stage stage;
    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private void cancel() {
        this.stage.close();
    }

    @FXML
    private void confirm() {
        this.stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        BUTTON_CONFIRM.setDisable(true);
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setList(ObservableList<Realm> list) {
        this.realmList = list;
        BUTTON_CONFIRM.setDisable(false);
    }

}

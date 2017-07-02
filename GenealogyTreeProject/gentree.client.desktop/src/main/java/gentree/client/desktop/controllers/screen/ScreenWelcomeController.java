package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configurations.enums.FilesFXML;
import gentree.client.desktop.configurations.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
@Log4j2
public class ScreenWelcomeController implements Initializable, FXMLController, FXMLAnchorPane {

    @FXML
    private Pane localProjectPane;

    @FXML
    private Pane onlineProjectPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        initOnlineProjectPane();
        initLocalProjectPane();
        setListeners();
        addTopOffsetListener();
        localProjectPane.resize(300, 400);
        onlineProjectPane.resize(300, 400);
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    public void addTopOffsetListener() {

        this.mainAnchorPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double y = (newValue.doubleValue() - localProjectPane.getHeight()) / 2;
                localProjectPane.setLayoutY(y);
                onlineProjectPane.setLayoutY(y);
            }
        });
    }

    public void initLocalProjectPane() {
        sm.loadFxml(new ButtonLocalModeController(), localProjectPane,
                FilesFXML.LOCAL_APP_MODE);

    }

    public void initOnlineProjectPane() {
        sm.loadFxml(new ButtonOnlineModeController(), onlineProjectPane,
                FilesFXML.ONLINE_APP_MODE);
    }

    private void setListeners() {
        this.languageBundle.addListener(new ChangeListener<ResourceBundle>() {

            @Override
            public void changed(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue,
                                ResourceBundle newValue) {
                reloadElements();
            }
        });
    }

    /*
    * LISTEN LANGUAGE CHANGES
    */

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {

    }
    /*
     ON CLICK ACTIONS
     */



}

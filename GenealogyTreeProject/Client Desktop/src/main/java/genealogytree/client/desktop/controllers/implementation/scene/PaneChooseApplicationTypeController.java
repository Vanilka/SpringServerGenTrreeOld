/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealogytree.client.desktop.controllers.implementation.scene;

import genealogytree.client.desktop.configuration.ContextGT;
import genealogytree.client.desktop.configuration.ScreenManager;
import genealogytree.client.desktop.configuration.enums.FXMLFile;
import genealogytree.client.desktop.configuration.messages.LogMessages;
import genealogytree.client.desktop.controllers.FXMLPane;
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
 * FXML Controller class
 *
 * @author vanilka
 */
@Log4j2
public class PaneChooseApplicationTypeController implements Initializable, FXMLPane {

    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();

    @FXML
    private AnchorPane chooseAppType;
    @FXML
    private Pane localProjectPane;
    @FXML
    private Pane onlineProjectPane;
    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);

        this.languageBundle.setValue(rb);
        addTopOffsetListener();
        localProjectPane.resize(300, 400);
        onlineProjectPane.resize(300, 400);
        initLocalProjectPane();
        initOnlineProjectPane();
        setListeners();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    public void addTopOffsetListener() {

        this.chooseAppType.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double y = (newValue.doubleValue() - localProjectPane.getHeight()) / 2;
                localProjectPane.setLayoutY(y);
                onlineProjectPane.setLayoutY(y);
            }
        });
    }


    public void initLocalProjectPane() {
        this.sc.loadFxml(new PaneLocalApplicationModeController(), localProjectPane,
                FXMLFile.LOCAL_APP_MODE);

    }

    public void initOnlineProjectPane() {
        this.sc.loadFxml(new PaneOnlineApplicationChoiceController(), onlineProjectPane,
                FXMLFile.ONLINE_APP_MODE);
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

    private void reloadElements() {
        // Nothing to do
    }



}

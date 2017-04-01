package com.genealogytree.client.desktop.controllers.implementation;

import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.configuration.enums.FXMLFile;
import com.genealogytree.client.desktop.configuration.messages.LogMessages;
import com.genealogytree.client.desktop.controllers.FXMLDialogController;
import com.genealogytree.client.desktop.controllers.FXMLPane;
import com.genealogytree.client.desktop.domain.GTX_Family;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 19/11/2016.
 */
@Log4j2
public class DialogNewProjectController implements Initializable, FXMLPane, FXMLDialogController {

    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();


    @FXML
    private AnchorPane newProjectDialog;

    @FXML
    private AnchorPane newProjectDialogInternePane;

    @FXML
    private JFXButton confirmNewProject;

    @FXML
    private JFXButton cancelNewProject;

    @FXML
    private JFXTextField familyNameField;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(rb);

        addDisableButtonListener();
        addLanguageListener();

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);

    }


    @FXML
    public void cancel() {

        this.stage.close();
    }

    @FXML
    public void confrim() {
        this.context.getService().setCurrentFamily(new GTX_Family(this.familyNameField.getText().trim()));
        sc.loadFxml(new PaneMainApplicationWindowController(), sc.getRootBorderPane(), FXMLFile.MAIN_APPLICATION_WINDOW, ScreenManager.Where.CENTER);
        this.stage.close();

    }


    private void addDisableButtonListener() {
        BooleanBinding disableBinding = Bindings.createBooleanBinding(
                () -> this.familyNameField.getText().isEmpty(), this.familyNameField.textProperty());
        this.confirmNewProject.disableProperty().bind(disableBinding);
    }

    /*
     * LISTEN LANGUAGE CHANGES
     */
    private void addLanguageListener() {
        this.languageBundle.addListener(new ChangeListener<ResourceBundle>() {
            @Override
            public void changed(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue,
                                ResourceBundle newValue) {
                reloadElements();
            }
        });
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        // Nothing to do
    }

    /*
     * GETTERS AND SETTERS
     */


    public void setStage(Stage stage) {
        this.stage = stage;
    }

}

package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLPaneController;
import com.genealogytree.application.FXMLDialogController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.configuration.FXMLFiles;
import com.genealogytree.domain.GTX_Family;
import com.genealogytree.services.implementation.GTFamillyServiceOnline;
import com.genealogytree.services.implementation.GTFamilyServiceOffline;
import com.genealogytree.services.responses.FamilyResponse;
import com.genealogytree.services.responses.ServerResponse;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 19/11/2016.
 */
public class DialogNewProjectController implements Initializable, FXMLPaneController, FXMLDialogController {

    private static final Logger LOG = LogManager.getLogger(DialogNewProjectController.class);

    private ScreenManager manager;
    private GenealogyTreeContext context;


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
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());
        this.languageBundle.setValue(rb);

        addDisableButtonListener();

    }


    @FXML
    public void cancel() {
        this.stage.close();
    }

    @FXML
    public void confrim() {

        if (this.context.getFamilyService() instanceof GTFamillyServiceOnline) {
            ServerResponse response = this.context.getFamilyService().addNewProject(new GTX_Family(this.familyNameField.getText().trim()));
            if (response instanceof FamilyResponse) {
                System.out.println("utworzono nowy projekt");
            }
        } else if (this.context.getFamilyService() instanceof GTFamilyServiceOffline) {
            this.context.getFamilyService().setCurrentFamily(new GTX_Family(this.familyNameField.getText().trim()));
            this.manager.loadFxml(new PaneMainApplicationWindowController(), this.manager.getMainWindow().getRootWindow(), FXMLFiles.MAIN_APPLICATION_WINDOW.toString(), ScreenManager.Where.CENTER);
        }
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
    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }

    public void setStage(Stage stage) {
        this.stage=stage;
    }

}

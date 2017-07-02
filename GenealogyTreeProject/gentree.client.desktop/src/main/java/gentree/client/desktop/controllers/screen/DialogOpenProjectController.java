package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import gentree.client.desktop.configurations.enums.FilesFXML;
import gentree.client.desktop.configurations.messages.Keys;
import gentree.client.desktop.configurations.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogController;
import gentree.client.desktop.controllers.FXMLPane;
import gentree.client.desktop.domain.Familly;
import gentree.client.desktop.service.ScreenManager;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 19/11/2016.
 */
@Log4j2
public class DialogOpenProjectController implements Initializable, FXMLController, FXMLPane, FXMLDialogController {



    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private JFXTabPane tabPaneOpenProject;


    private Tab tabOpenNewProject;
    private Tab tabOpenExistingProject;

    private Stage stage;

    {
        tabOpenExistingProject = new Tab();
        tabOpenNewProject = new Tab();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(rb);
        initTabs();

        addSelectedTabListener();
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
    /*    context.getService().setCurrentFamilly(new Familly(this.familyNameField.getText().trim()));
        sm.loadFxml(new MainWindowController(), sm.getMainWindowBorderPane(), FilesFXML.SCREEN_MAIN_FXML, ScreenManager.Where.CENTER);
        */
        this.stage.close();

    }


    private void initTabs() {
        sm.loadFxml(new TabOpenNewProjectController(), tabPaneOpenProject, tabOpenNewProject, FilesFXML.TAB_OPEN_NEW_PROJECT_FXML, getValueFromKey(Keys.TAB_NEW_PROJECT));
        sm.loadFxml(new TabOpenExistingProjectController(), tabPaneOpenProject, tabOpenExistingProject, FilesFXML.TAB_OPEN_EXISTING_PROJECT_FXML, getValueFromKey(Keys.TAB_OPEN_PROJECT));
        tabPaneOpenProject.getSelectionModel().select(tabOpenNewProject);
    }

    private void addSelectedTabListener() {
        tabPaneOpenProject.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue.equals(tabOpenNewProject)) {
                System.out.println("new");
            } else if(newValue.equals(tabOpenExistingProject)) {
                System.out.println("existing");
            }
        });
    }

    private void addDisableButtonListener() {
/*        BooleanBinding disableBinding = Bindings.createBooleanBinding(
                () -> this.familyNameField.getText().isEmpty(), this.familyNameField.textProperty());
        this.confirmNewProject.disableProperty().bind(disableBinding);*/
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

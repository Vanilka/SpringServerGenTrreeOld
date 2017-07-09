package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import gentree.client.desktop.configurations.enums.FilesFXML;
import gentree.client.desktop.configurations.messages.Keys;
import gentree.client.desktop.configurations.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogController;
import gentree.client.desktop.controllers.FXMLPane;
import gentree.client.desktop.domain.Family;
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

    @FXML
    private JFXButton buttonConfirm;

    @FXML
    private JFXButton buttonCancel;

    /*
        Tab Open New Project
     */
    private Tab tabOpenNewProject;
    private TabOpenNewProjectController tabOpenNewProjectController;

    /*
        Tab Open Existing Project
     */
    private Tab tabOpenExistingProject;
    private TabOpenExistingProjectController tabOpenExistingProjectController;

    private Stage stage;

    {
        tabOpenExistingProject = new Tab();
        tabOpenNewProject = new Tab();

        tabOpenNewProjectController = new TabOpenNewProjectController();
        tabOpenExistingProjectController = new TabOpenExistingProjectController();
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
        if (tabPaneOpenProject.getSelectionModel().getSelectedItem().equals(tabOpenNewProject)) {
            context.getService().setCurrentFamily(new Family(tabOpenNewProjectController.getFamilyNameField().getText().trim()));
            sm.loadFxml(new ScreenMainController(), sm.getMainWindowBorderPane(), FilesFXML.SCREEN_MAIN_FXML, ScreenManager.Where.CENTER);
        }
        this.stage.close();

    }


    private void initTabs() {
        tabOpenNewProjectController = (TabOpenNewProjectController) sm.loadFxml(tabOpenNewProjectController, tabPaneOpenProject, tabOpenNewProject, FilesFXML.TAB_OPEN_NEW_PROJECT_FXML, getValueFromKey(Keys.TAB_NEW_PROJECT));
        tabOpenExistingProjectController = (TabOpenExistingProjectController) sm.loadFxml(tabOpenExistingProjectController, tabPaneOpenProject, tabOpenExistingProject, FilesFXML.TAB_OPEN_EXISTING_PROJECT_FXML, getValueFromKey(Keys.TAB_OPEN_PROJECT));
        tabPaneOpenProject.getSelectionModel().select(tabOpenNewProject);
    }

    private void addSelectedTabListener() {
        tabPaneOpenProject.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.equals(tabOpenNewProject)) {
                buttonConfirm.setText(getValueFromKey(Keys.CREATE));
            } else if (newValue.equals(tabOpenExistingProject)) {
                buttonConfirm.setText(getValueFromKey(Keys.OPEN));
            }
        });
    }

    private void addDisableButtonListener() {
        BooleanBinding disableBinding = Bindings.createBooleanBinding(
                () -> tabOpenNewProjectController.getFamilyNameField().getText().isEmpty(),
                tabOpenNewProjectController.getFamilyNameField().textProperty());
        this.buttonConfirm.disableProperty().bind(disableBinding);
    }

    /*
     * LISTEN LANGUAGE CHANGES
     */
    private void addLanguageListener() {
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
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

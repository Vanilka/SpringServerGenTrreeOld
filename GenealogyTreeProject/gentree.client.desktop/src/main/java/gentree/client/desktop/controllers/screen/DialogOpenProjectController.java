package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.Keys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogController;
import gentree.client.desktop.controllers.FXMLPane;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.service.ScreenManager;
import gentree.client.desktop.service.implementation.GenTreeLocalService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
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
    public void confirm() {
        if (tabPaneOpenProject.getSelectionModel().getSelectedItem().equals(tabOpenNewProject)) {
            actionNewProject();

        } else if (tabPaneOpenProject.getSelectionModel().getSelectedItem().equals(tabOpenExistingProject)) {
            actionOpenProject();
        } else {

            //To to show error
        }
        this.stage.close();

    }

    private void actionNewProject() {
        context.getService().createFamily(new Family(tabOpenNewProjectController.getFamilyNameField().getText().trim()));

        if (context.getService() instanceof GenTreeLocalService) {
            sm.loadFxml(new ScreenMainController(), sm.getMainWindowBorderPane(), FilesFXML.SCREEN_MAIN_FXML, ScreenManager.Where.CENTER);
        }
    }

    private void actionOpenProject() {
        Path path = tabOpenExistingProjectController.getSelectedFile();
        Family family = readFamilyFromXML(path.toFile());

        ((GenTreeLocalService) context.getService()).openProject(family, path.toFile().getName());
        sm.loadFxml(new ScreenMainController(), sm.getMainWindowBorderPane(), FilesFXML.SCREEN_MAIN_FXML, ScreenManager.Where.CENTER);
    }


    private Family readFamilyFromXML(File file) {
        Family customer = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Family.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            customer = (Family) jaxbUnmarshaller.unmarshal(file);
        } catch (Exception e) {
            log.error("ERROR");
            e.printStackTrace();
        }

        return customer;
    }

    /**
     * Initialization of Tabs </br>
     * If Service is GenTreeLocalService the Open existing tab will be initialized
     */
    private void initTabs() {

        tabOpenNewProjectController = (TabOpenNewProjectController) sm.loadFxml(tabOpenNewProjectController, tabPaneOpenProject, tabOpenNewProject, FilesFXML.TAB_OPEN_NEW_PROJECT_FXML, getValueFromKey(Keys.TAB_NEW_PROJECT));

        if (context.getService() instanceof GenTreeLocalService) {
            tabOpenExistingProjectController = (TabOpenExistingProjectController) sm.loadFxml(tabOpenExistingProjectController, tabPaneOpenProject, tabOpenExistingProject, FilesFXML.TAB_OPEN_EXISTING_PROJECT_FXML, getValueFromKey(Keys.TAB_OPEN_PROJECT));
        }

        tabPaneOpenProject.getSelectionModel().select(tabOpenNewProject);
    }

    /**
     * Initialization of Tab listener
     */
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
        BooleanBinding disableBinding;

        if (context.getService() instanceof GenTreeLocalService) {
            disableBinding = Bindings.createBooleanBinding(
                    () -> ((tabPaneOpenProject.getSelectionModel().getSelectedItem().equals(tabOpenNewProject)
                            && tabOpenNewProjectController.getFamilyNameField().getText().isEmpty())
                            ||
                            (tabPaneOpenProject.getSelectionModel().getSelectedItem().equals(tabOpenExistingProject)
                                    && tabOpenExistingProjectController.getProjectChooser().getSelectionModel().getSelectedItem() == null)),
                    tabOpenNewProjectController.getFamilyNameField().textProperty(),
                    tabOpenExistingProjectController.getProjectChooser().getSelectionModel().selectedIndexProperty(),
                    tabPaneOpenProject.getSelectionModel().selectedItemProperty());
        } else {
            disableBinding = Bindings.createBooleanBinding(
                    () -> (tabOpenNewProjectController.getFamilyNameField().getText().isEmpty()),
                    tabOpenNewProjectController.getFamilyNameField().textProperty());
        }
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

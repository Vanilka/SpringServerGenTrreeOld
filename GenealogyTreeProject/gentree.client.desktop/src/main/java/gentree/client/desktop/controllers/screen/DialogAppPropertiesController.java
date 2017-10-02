package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;

import java.net.URL;
import java.util.*;

/**
 * Created by Martyna SZYMKOWIAK on 17/07/2017.
 */

@Log4j2
public class DialogAppPropertiesController implements Initializable, FXMLController, FXMLDialogController {

    private final ToggleGroup group;
    private final HashMap<ToggleButton, AnchorPane> paneMap;
    private final HashMap<String, String> propertiesMap;
    @FXML
    public ToggleButton buttonOtherProperties;
    @FXML
    public ToggleButton buttonOnlineProperties;
    @FXML
    public ToggleButton buttonTreeProperties;
    @FXML
    public AnchorPane paneTreeProperties;
    @FXML
    public AnchorPane paneOnlineProperties;
    @FXML
    public AnchorPane paneOtherProperties;
    @FXML
    public JFXButton buttonCancel;
    @FXML
    public JFXButton buttonConfirm;
    @FXML
    private BorderPane contentPane;
    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private DialogAppPropertiesTreeController dialogAppPropertiesTreeController;
    private DialogAppPropertiesOnlineController dialogAppPropertiesOnlineController;
    private DialogAppPropertiesOtherController dialogAppPropertiesOtherController;

    private List<ToggleButton> toggleButtons;
    private Stage stage;

    {
        group = new ToggleGroup();
        toggleButtons = new ArrayList<>();
        paneMap = new HashMap<>();
        propertiesMap = new HashMap<>();
        populatePropertiesMap();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        populatePaneMap();
        initPanes();
        initToggleButtons();
        initListeners();
        initGroup();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }


    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    private void initToggleButtons() {
        toggleButtons = Arrays.asList(
                buttonOnlineProperties,
                buttonOtherProperties,
                buttonTreeProperties
        );
    }


    private void populatePaneMap() {
        paneMap.put(buttonOnlineProperties, paneOnlineProperties);
        paneMap.put(buttonOtherProperties, paneOtherProperties);
        paneMap.put(buttonTreeProperties, paneTreeProperties);
    }

    private void initPanes() {
        dialogAppPropertiesTreeController = (DialogAppPropertiesTreeController) sm.loadFxml(new DialogAppPropertiesTreeController(), paneTreeProperties, FilesFXML.DIALOG_APP_PROPERTIES_TREE);
        dialogAppPropertiesTreeController.setProperties(propertiesMap);
        dialogAppPropertiesOnlineController = (DialogAppPropertiesOnlineController) sm.loadFxml(new DialogAppPropertiesOnlineController(), paneOnlineProperties, FilesFXML.DIALOG_APP_PROPERTIES_ONLINE);
        dialogAppPropertiesOtherController = (DialogAppPropertiesOtherController) sm.loadFxml(new DialogAppPropertiesOtherController(), paneOtherProperties, FilesFXML.DIALOG_APP_PROPERTIES_OTHER);


    }

    private void initGroup() {
        toggleButtons.forEach(b -> b.setToggleGroup(group));
        group.selectToggle(buttonTreeProperties);
    }

    private void saveProperties() {
        Configuration config = GenTreeProperties.INSTANCE.getConfiguration();

        propertiesMap.forEach((k, v) -> {
            if (config.containsKey(k) && !config.getString(k).equals(v)) {
                config.setProperty(k, v);
            }
        });

        GenTreeProperties.INSTANCE.storeProperties();
    }

    private void populatePropertiesMap() {
        Configuration config = GenTreeProperties.INSTANCE.getConfiguration();
        Iterator<String> keys = config.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            propertiesMap.put(key, config.getString(key));
        }
    }

    /*
        Listeners
     */

    private void initListeners() {
        initActiveButtonListener();
    }

    private void initActiveButtonListener() {
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == null) {
                group.selectToggle(oldValue);
            }

            if (newValue != null && paneMap.containsKey(newValue)) {
                paneMap.get(newValue).setVisible(true);
                if (paneMap.containsKey(oldValue)) {
                    paneMap.get(oldValue).setVisible(false);

                }
            }
        });

    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }

    public void confirm(ActionEvent actionEvent) {
        saveProperties();
    }


}
package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.configuration.RealmConfig;
import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
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
    private final RealmConfig realmConfig = GenTreeProperties.INSTANCE.getRealmConfig();
    @FXML
    public ToggleButton BUTTON_OTHER_PROPERTIES;
    @FXML
    public ToggleButton BUTTON_ONLINE_PROPERTIES;
    @FXML
    public ToggleButton BUTTON_TREE_PROPERTIES;
    @FXML
    public AnchorPane PANE_TREE_PROPERTIES;
    @FXML
    public AnchorPane PANE_ONLINE_PROPERTIES;
    @FXML
    public AnchorPane PANE_OTHER_PROPERTIES;
    @FXML
    public JFXButton BUTTON_CANCEL;
    @FXML
    public JFXButton BUTTON_CONFIRM;
    @FXML
    private BorderPane CONTENT_PANE;
    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private DialogAppPropertiesTreeController dialogAppPropertiesTreeController;
    private DialogAppPropertiesOnlineController dialogAppPropertiesOnlineController;
    private DialogAppPropertiesOtherController dialogAppPropertiesOtherController;

    private List<ToggleButton> toggleButtons;
    private Stage stage;

    private ChangeListener<? super Toggle> selectedChangeListener = this::selectedChange;

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
                BUTTON_ONLINE_PROPERTIES,
                BUTTON_OTHER_PROPERTIES,
                BUTTON_TREE_PROPERTIES
        );
    }


    private void populatePaneMap() {
        paneMap.put(BUTTON_ONLINE_PROPERTIES, PANE_ONLINE_PROPERTIES);
        paneMap.put(BUTTON_OTHER_PROPERTIES, PANE_OTHER_PROPERTIES);
        paneMap.put(BUTTON_TREE_PROPERTIES, PANE_TREE_PROPERTIES);
    }

    private void initPanes() {
        dialogAppPropertiesTreeController = (DialogAppPropertiesTreeController) sm.loadFxml(new DialogAppPropertiesTreeController(), PANE_TREE_PROPERTIES, FilesFXML.DIALOG_APP_PROPERTIES_TREE);
        dialogAppPropertiesTreeController.setProperties(propertiesMap);
        dialogAppPropertiesOnlineController = (DialogAppPropertiesOnlineController) sm.loadFxml(new DialogAppPropertiesOnlineController(), PANE_ONLINE_PROPERTIES, FilesFXML.DIALOG_APP_PROPERTIES_ONLINE);
        dialogAppPropertiesOtherController = (DialogAppPropertiesOtherController) sm.loadFxml(new DialogAppPropertiesOtherController(), PANE_OTHER_PROPERTIES, FilesFXML.DIALOG_APP_PROPERTIES_OTHER);


    }

    private void initGroup() {
        toggleButtons.forEach(b -> b.setToggleGroup(group));
        group.selectToggle(BUTTON_TREE_PROPERTIES);
    }

    private void saveProperties() {
        Configuration config = GenTreeProperties.INSTANCE.getConfiguration();

        propertiesMap.forEach((k, v) -> {
            if (config.containsKey(k) && !config.getString(k).equals(v)) {
                config.setProperty(k, v);
            }
        });

        GenTreeProperties.INSTANCE.storeProperties();

        if (!GenTreeProperties.INSTANCE.getRealmConfig().equals(dialogAppPropertiesOnlineController.getRealmConfig())) {
            System.out.println("will store new properties");
            GenTreeProperties.INSTANCE.setRealmConfig(dialogAppPropertiesOnlineController.getRealmConfig());
            GenTreeProperties.INSTANCE.storeRealms();
        }
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
        group.selectedToggleProperty().addListener(selectedChangeListener);
    }

    private void cleanListeners() {
        group.selectedToggleProperty().removeListener(selectedChangeListener);
        dialogAppPropertiesOnlineController.cleanListeners();
        dialogAppPropertiesTreeController.cleanListeners();
        dialogAppPropertiesOtherController.cleanListeners();
    }

    private void selectedChange(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
        if (newValue == null) {
            group.selectToggle(oldValue);
        }

        if (newValue != null && paneMap.containsKey(newValue)) {
            paneMap.get(newValue).setVisible(true);
            if (paneMap.containsKey(oldValue)) {
                paneMap.get(oldValue).setVisible(false);

            }
        }
    }

    public void cancel(ActionEvent actionEvent) {
        cleanListeners();
        stage.close();
    }

    public void confirm(ActionEvent actionEvent) {
        cleanListeners();
        saveProperties();
    }


}
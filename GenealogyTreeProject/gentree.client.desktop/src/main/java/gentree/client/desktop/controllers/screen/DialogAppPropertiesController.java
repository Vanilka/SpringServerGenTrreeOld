package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import gentree.client.desktop.configurations.GenTreeProperties;
import gentree.client.desktop.configurations.enums.FilesFXML;
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
        GenTreeProperties.INSTANCE.getAppProperties().forEach((key, value) -> propertiesMap.put(key.toString(), value.toString()));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.languageBundle.setValue(resources);
        populateMap();
        initPanes();
        initToggleButtons();
        initListeners();
        initGroup();
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


    private void populateMap() {
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
        Properties p = GenTreeProperties.INSTANCE.getAppProperties();

        propertiesMap.forEach((k, v) -> {
            if(p.containsKey(k) && !p.getProperty(k).equals(v)) {
                p.setProperty(k, v);
            }
        });
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
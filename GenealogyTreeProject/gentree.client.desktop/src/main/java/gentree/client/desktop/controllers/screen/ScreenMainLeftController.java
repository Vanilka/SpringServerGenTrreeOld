package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXTabPane;
import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.Keys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */
@Log4j2
public class ScreenMainLeftController implements Initializable, FXMLController, FXMLAnchorPane {

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private JFXTabPane TAB_PANE_INFO;


    private Tab familyInfo;
    private Tab familyView;
    /*
        Controllers
     */
    private TabFamilyInfoController tabFamilyInfoController;
    private TabFamilyViewController tabFamilyViewController;

    private ChangeListener<? super ResourceBundle> languageListener = this::languageChange;

    {
        familyInfo = new Tab();
        familyView = new Tab();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        this.languageBundle.bind(context.getBundle());
        initPanes();
        addLanguageListener();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    private void initPanes() {
        tabFamilyInfoController = (TabFamilyInfoController) sm.loadFxml(
                new TabFamilyInfoController(),
                TAB_PANE_INFO,
                familyInfo,
                FilesFXML.SCREEN_MAIN_LEFT_FAMILY_INFO_FXML, getValueFromKey(Keys.TAB_FAMILY_INFO));
        tabFamilyInfoController.setScreenMainLeft(this);

        tabFamilyViewController = (TabFamilyViewController) sm.loadFxml(
                new TabFamilyViewController(),
                TAB_PANE_INFO,
                familyView,
                FilesFXML.SCREEN_MAIN_LEFT_FAMILY_VIEW_FXML, getValueFromKey(Keys.TAB_FAMILY_VIEW));
        tabFamilyViewController.setScreenMainLeft(this);

        TAB_PANE_INFO.getSelectionModel().select(familyInfo);

    }

    public void clean() {
        cleanListeners();
    }

    /*
     *   LISTEN LANGUAGE CHANGES
     */

    private void addLanguageListener() {
        this.languageBundle.addListener(languageListener);
    }

    private void cleanListeners() {
        this.languageBundle.removeListener(languageListener);
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        familyInfo.setText(getValueFromKey(Keys.TAB_FAMILY_INFO));
        familyView.setText(getValueFromKey(Keys.TAB_FAMILY_VIEW));

    }

    private void languageChange(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        reloadElements();
    }

}

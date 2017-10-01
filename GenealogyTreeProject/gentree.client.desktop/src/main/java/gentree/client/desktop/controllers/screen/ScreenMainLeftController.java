package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXTabPane;
import gentree.client.desktop.configuration.common.enums.FilesFXML;
import gentree.client.desktop.configuration.common.messages.Keys;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    private JFXTabPane tabPaneInfo;


    private Tab familyInfo;
    private Tab familyView;
    /*
        Controllers
     */
    private TabFamilyInfoController tabFamilyInfoController;
    private TabFamilyViewController tabFamilyViewController;

    {
        familyInfo = new Tab();
        familyView = new Tab();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.languageBundle.setValue(resources);
        initPanes();
    }

    private void initPanes() {
        tabFamilyInfoController = (TabFamilyInfoController) sm.loadFxml(
                new TabFamilyInfoController(),
                tabPaneInfo,
                familyInfo,
                FilesFXML.SCREEN_MAIN_LEFT_FAMILY_INFO_FXML, getValueFromKey(Keys.TAB_FAMILY_INFO));
        tabFamilyInfoController.setScreenMainLeft(this);

        tabFamilyViewController = (TabFamilyViewController) sm.loadFxml(
                new TabFamilyViewController(),
                tabPaneInfo,
                familyView,
                FilesFXML.SCREEN_MAIN_LEFT_FAMILY_VIEW_FXML, getValueFromKey(Keys.TAB_FAMILY_VIEW));
        tabFamilyViewController.setScreenMainLeft(this);

        tabPaneInfo.getSelectionModel().select(familyInfo);

    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }
}

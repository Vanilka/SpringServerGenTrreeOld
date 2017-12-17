package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.Keys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLTab;
import gentree.client.desktop.domain.Family;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */

@Log4j2
public class TabFamilyInfoController implements Initializable, FXMLController, FXMLTab {

    private final TabFamilyInfoController instance = this;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private AnchorPane SCREEN_MAIN_LEFT_FAMILY_INFO_PANE;

    @FXML
    private JFXTextField FAMILY_NAME_FIELD;

    @FXML
    private JFXTextField MEMBERS_COUNT_FIELD;

    @FXML
    private JFXButton MODIFY_NAME_BUTTON;

    @FXML
    private JFXButton ADD_MEMBER_BUTTON;

    @FXML
    private VBox CONTENT_VBOX;

    @Getter
    @Setter
    private ScreenMainLeftController screenMainLeft;

    @Getter
    @Setter
    private Tab tab;

    @Getter
    @Setter
    private JFXTabPane tabPane;

    private InvalidationListener invalidationListener = new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
            MEMBERS_COUNT_FIELD.setText("" + context.getService().getCurrentFamily().getMembers().size());
        }
    };
    private ChangeListener<? super ResourceBundle> languageListener = this::languageChange;
    private ChangeListener<? super Family> familyListener = this::familyListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        this.languageBundle.bind(context.getBundle());
        initGraphicalElements();

        loadFamily(context.getService().getCurrentFamily());
        addLanguageListener();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    @FXML
    private void openNewMemberDialog() {
        sm.showNewDialog(new DialogAddMemberController(), FilesFXML.ADD_MEMBER_DIALOG);
    }

    private void loadFamily(Family f) {
        FAMILY_NAME_FIELD.setText(f.getName());
        MEMBERS_COUNT_FIELD.setText("" + f.getMembers().size());
        f.getMembers().addListener(invalidationListener);
    }

    private void initGraphicalElements() {
        FAMILY_NAME_FIELD.setEditable(false);
        MEMBERS_COUNT_FIELD.setEditable(false);
        CONTENT_VBOX.setAlignment(Pos.TOP_CENTER);

    }

    private void addListener() {

    }

    private void cleanListener() {
        context.getService().currentFamilyPropertyI().removeListener(familyListener);
        this.languageBundle.removeListener(languageListener);

    }

    private void languageChange(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        reloadElements();
    }


    private void familyListener(ObservableValue<? extends Family> observable, Family oldValue, Family newValue) {
        if (newValue != null) {
            loadFamily(newValue);
        }

        if (oldValue != null) {
            oldValue.getMembers().removeListener(invalidationListener);
        }
    }

    /*
     *   LISTEN LANGUAGE CHANGES
     */

    private void addLanguageListener() {
        this.languageBundle.addListener(languageListener);
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        FAMILY_NAME_FIELD.setPromptText(getValueFromKey(Keys.PROJECT_MEMBER_NUMBER));
        MEMBERS_COUNT_FIELD.setPromptText(getValueFromKey(Keys.PROJECT_MEMBER_NUMBER));
        MODIFY_NAME_BUTTON.setText(getValueFromKey(Keys.MODIFY));
        ADD_MEMBER_BUTTON.setText(getValueFromKey(Keys.ADD_MEMBER));


    }

    @Override
    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tab = tab;
        this.tabPane = tabPane;
    }
}
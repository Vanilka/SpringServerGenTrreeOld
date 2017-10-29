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
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
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

    TabFamilyInfoController instance = this;

    @FXML
    private AnchorPane screenMainLeftFamilyInfoController;

    @FXML
    private JFXTextField familyName;

    @FXML
    private JFXTextField membersCount;

    @FXML
    private JFXButton modifyNameButton;

    @FXML
    private JFXButton addMemberButton;

    @FXML
    private VBox contentVBox;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

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
            membersCount.setText("" + context.getService().getCurrentFamily().getMembers().size());
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        this.languageBundle.bind(context.getBundle());
        initGraphicalElements();

        context.getService().currentFamilyPropertyI().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                loadFamily(newValue);
            }

            if(oldValue != null) {
                oldValue.getMembers().removeListener(invalidationListener);
            }
        });

        loadFamily(context.getService().getCurrentFamily());
        addLanguageListener();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    @FXML
    private void openNewMemberDialog() {
        sm.showNewDialog(new DialogAddMemberController(), FilesFXML.ADD_MEMBER_DIALOG);
    }

    private void loadFamily(Family f) {
        familyName.setText(f.getName());
        membersCount.setText("" + f.getMembers().size());
        f.getMembers().addListener(invalidationListener);
    }

    private void initGraphicalElements() {
        familyName.setEditable(false);
        membersCount.setEditable(false);
        contentVBox.setAlignment(Pos.TOP_CENTER);

    }

    private void addListener() {

    }

        /*
        *   LISTEN LANGUAGE CHANGES
        */

    private void addLanguageListener() {
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        familyName.setPromptText(getValueFromKey(Keys.PROJECT_MEMBER_NUMBER));
        membersCount.setPromptText(getValueFromKey(Keys.PROJECT_MEMBER_NUMBER));
        modifyNameButton.setText(getValueFromKey(Keys.MODIFY));
        addMemberButton.setText(getValueFromKey(Keys.ADD_MEMBER));


    }

    @Override
    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tab = tab;
        this.tabPane = tabPane;
    }


}
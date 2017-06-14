package genealogytree.client.desktop.controllers.implementation.scene;

import genealogytree.client.desktop.configuration.ContextGT;
import genealogytree.client.desktop.configuration.ScreenManager;
import genealogytree.client.desktop.configuration.enums.FXMLFile;
import genealogytree.client.desktop.configuration.messages.LogMessages;
import genealogytree.client.desktop.controllers.FXMLTab;

import genealogytree.client.desktop.service.responses.FamilyResponse;
import genealogytree.client.desktop.service.responses.ServiceResponse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 22/11/2016.
 */
@Log4j2
public class TabInfoProjectPaneController implements Initializable, FXMLTab {

    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();
    @FXML
    private AnchorPane infoProjectTab;

    @FXML
    private JFXButton addSimButton;

    @FXML
    private JFXButton addRelationButton;

    @FXML
    private JFXButton updateFamilyName;

    @FXML
    private JFXTextField projectNameLabel;

    @FXML
    private JFXTextField projectMemberNumber;

    @FXML
    private Label projectNameValueLabel;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private Tab tab;
    private JFXTabPane tabPane;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.projectMemberNumber.setEditable(false);
        this.projectNameLabel.setEditable(false);
        this.languageBundle.setValue(rb);
        setListener();
        addLanguageListener();
        bindNameLabel();

        projectMemberNumber.setText("" + context.getService().getCurrentFamily().getMembersList().size());
        context.getService().getCurrentFamily().getMembersList().addListener((InvalidationListener) observable -> projectMemberNumber.setText("" + context.getService().getCurrentFamily().getMembersList().size()));
        BooleanBinding disableBinding = Bindings.createBooleanBinding(() -> context.getService().getCurrentFamily().getMembersList().size() < 2, context.getService().getCurrentFamily().getMembersList());
        addRelationButton.disableProperty().bind(disableBinding);
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }


    @FXML
    public void addNewSim() {
        this.sc.loadFxml(new TabAddNewMemberPaneController(), this.tabPane, new Tab(), FXMLFile.TAB_ADD_NEW_MEMBER, getValueFromKey("addMember"));
    }

    @FXML
    public void addNewRelation() {
        this.sc.loadFxml(new TabAddNewRelationPaneController(), this.tabPane, new Tab(), FXMLFile.TAB_ADD_NEW_RELATION, getValueFromKey("addRelation"));
    }

    @FXML
    public void updateFamilyName() {
        if (this.projectNameLabel.isEditable() &&
                !this.projectNameLabel.getText().equals(
                        context.getService().getCurrentFamily().getName())) {
            ServiceResponse response = context.getService().updateFamilyName(this.projectNameLabel.getText());
            if (response instanceof FamilyResponse) {
                System.out.println("FamilyResponse");
            } else {
                System.out.println("Error response");

            }
        }
        this.projectNameLabel.setEditable(!this.projectNameLabel.isEditable());


    }

    private void setListener() {
        this.projectNameLabel.editableProperty().addListener(this::changed);
    }


    private void changed(ObservableValue<? extends Boolean> boolChange, Boolean oldValue, Boolean newValue) {
        if (newValue.booleanValue()) {
            this.updateFamilyName.setText(getValueFromKey("button_confirm"));
            unBindNameLabel();
        } else {
            this.updateFamilyName.setText(getValueFromKey("update_project_name"));
            bindNameLabel();
        }
    }


    private void bindNameLabel() {
       this.projectNameLabel.textProperty().bind(context.getService().getCurrentFamily().nameProperty());
    }

    private void unBindNameLabel() {
        this.projectNameLabel.textProperty().unbind();
    }


    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public JFXTabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(JFXTabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        this.tab = tab;
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
        this.addSimButton.setText(getValueFromKey("addMember"));
        this.addRelationButton.setText(getValueFromKey("addRelation"));
        this.projectNameLabel.setPromptText(getValueFromKey("project_name_label"));
        this.projectMemberNumber.setPromptText(getValueFromKey("project_member_number"));

        if (this.projectNameLabel.isEditable()) {
            this.updateFamilyName.setText(getValueFromKey("button_confirm"));
        } else {
            this.updateFamilyName.setText(getValueFromKey("update_project_name"));
        }
    }

    /*
     * GETTERS AND SETTERS
     */

    public void setContext() {
        addLanguageListener();
        bindNameLabel();

        projectMemberNumber.setText("" + context.getService().getCurrentFamily().getMembersList().size());
        context.getService().getCurrentFamily().getMembersList().addListener((InvalidationListener) observable -> projectMemberNumber.setText("" + context.getService().getCurrentFamily().getMembersList().size()));
        BooleanBinding disableBinding = Bindings.createBooleanBinding(() -> context.getService().getCurrentFamily().getMembersList().size() < 2, context.getService().getCurrentFamily().getMembersList());
        addRelationButton.disableProperty().bind(disableBinding);


    }
}

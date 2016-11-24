package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLPaneController;
import com.genealogytree.application.FXMLTabController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.domain.beans.MemberBean;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 22/11/2016.
 */
public class TabAddNewMemberPaneController implements Initializable, FXMLTabController {

    private static final Logger LOG = LogManager.getLogger(TabAddNewMemberPaneController.class);

    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private AnchorPane addNewMember;

    @FXML
    private JFXTextField simNameField;

    @FXML
    private JFXTextField simSurnameField;

    @FXML
    private JFXRadioButton simAgeBabyField;

    @FXML
    private JFXRadioButton simAgeChildField;

    @FXML
    private JFXRadioButton simAgeAdoField;

    @FXML
    private JFXRadioButton simAgeJAdultField;

    @FXML
    private JFXRadioButton simAgeAdultField;

    @FXML
    private JFXRadioButton simAgeSeniorField;

    @FXML
    private JFXButton addSimConfirmButton;

    @FXML
    private JFXButton addSimCancelButton;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private ToggleGroup toogleGroupAge;

    private Tab tab;
    private JFXTabPane tabPane;

    /**
     * Initializes the controller class.
     */

    {
        toogleGroupAge = new ToggleGroup();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());

        this.languageBundle.setValue(rb);

        createAgeToogleGroupe();
    }

    public void addSimConfirm() {

    }

    public void setAddSimCancel() {

        this.tabPane.getTabs().remove(tab);
    }

    private void createAgeToogleGroupe() {

        this.simAgeBabyField.setToggleGroup(this.toogleGroupAge);
        this.simAgeBabyField.setUserData(MemberBean.Age.BABY);

        this.simAgeChildField.setToggleGroup(this.toogleGroupAge);
        this.simAgeChildField.setUserData(MemberBean.Age.CHILD);

        this.simAgeAdoField.setToggleGroup(this.toogleGroupAge);
        this.simAgeAdoField.setUserData(MemberBean.Age.ADO);

        this.simAgeJAdultField.setToggleGroup(this.toogleGroupAge);
        this.simAgeJAdultField.setUserData(MemberBean.Age.YOUNG_ADULT);

        this.simAgeAdultField.setToggleGroup(this.toogleGroupAge);
        this.simAgeAdultField.setUserData(MemberBean.Age.ADULT);

        this.simAgeSeniorField.setToggleGroup(this.toogleGroupAge);
        this.simAgeSeniorField.setUserData(MemberBean.Age.SENIOR);

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

        this.simNameField.setPromptText(getValueFromKey("simName"));
        this.simSurnameField.setPromptText(getValueFromKey("simSurname"));

        this.simAgeBabyField.setText(getValueFromKey("simAgeBaby"));
        this.simAgeChildField.setText(getValueFromKey("simAgeChild"));
        this.simAgeAdoField.setText(getValueFromKey("simAgeAdo"));
        this.simAgeJAdultField.setText(getValueFromKey("simAgeJAdult"));
        this.simAgeAdultField.setText(getValueFromKey("simAgeAdult"));
        this.simAgeSeniorField.setText(getValueFromKey("simAgeSenior"));

        this.addSimConfirmButton.setText(getValueFromKey("addSimConfirm"));
        this.addSimCancelButton.setText(getValueFromKey("addSimCancel"));

    }

    /*
     * GETTERS AND SETTERS
     */
    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }
}

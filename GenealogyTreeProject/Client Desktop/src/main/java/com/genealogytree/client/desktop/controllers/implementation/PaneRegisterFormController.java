/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.client.desktop.controllers.implementation;

import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.controllers.FXMLPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * FXML Controller class
 *
 * @author mszymkow
 */
public class PaneRegisterFormController implements Initializable, FXMLPane {

    private static final Logger LOG = LogManager.getLogger(PaneRegisterFormController.class);

    private final String MANDATORY_MESSAGE = "This field cannot be empty";
    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();
    private PaneLogonWindowController logonWindow;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();
    @FXML
    private AnchorPane registerForm;
    @FXML
    private JFXButton buttonRegister;
    @FXML
    private JFXTextField mailBox;
    @FXML
    private JFXTextField loginField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXPasswordField passwordConfirmField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInfoLog("Initialisation:  " + this.toString());
        this.languageBundle.setValue(rb);

		/*
         * Add Listener to listen ServiceManager changes
		 */


		/*
         * Check condition to enable REGISTER button
		 */
        this.buttonRegister.setDisable(true);
        addRegisterButtonDisableCondition();
		/*
		 * Add Validation empty to text and password fields.
		 */
        addFieldsValidation();
    }

    private RequiredFieldValidator getMandatoryValidator() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage(MANDATORY_MESSAGE);
        return validator;
    }

    private void addFieldsValidation() {
        this.mailBox.getValidators().add(getMandatoryValidator());
        this.loginField.getValidators().add(getMandatoryValidator());

        this.passwordField.getValidators().add(getMandatoryValidator());
        this.passwordConfirmField.getValidators().add(getMandatoryValidator());

        this.mailBox.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                mailBox.validate();
            }
        });
        this.loginField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                loginField.validate();
            }
        });
        this.passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passwordField.validate();
            }
        });
        this.passwordConfirmField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passwordConfirmField.validate();
            }
        });

    }

    private void addRegisterButtonDisableCondition() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        BooleanBinding disableBinding = Bindings.createBooleanBinding(
                () -> this.loginField.getText().isEmpty() || this.passwordField.getText().isEmpty()
                        || this.mailBox.getText().isEmpty()
                        || !this.passwordConfirmField.getText().equals(passwordField.getText())
                        || !pattern.matcher(this.mailBox.getText()).matches(),
                this.mailBox.textProperty(), this.loginField.textProperty(), this.passwordField.textProperty(),
                this.passwordConfirmField.textProperty());
        this.buttonRegister.disableProperty().bind(disableBinding);
    }

    private void clearAllFields() {
        this.loginField.clear();
        this.passwordField.clear();
        this.passwordConfirmField.clear();
        this.mailBox.clear();
    }

    @FXML
    private void submitRegister() {

    }

    @FXML
    private void returnLogon() {
        clearAllFields();
        this.logonWindow.showLogonForm();
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
        this.mailBox.setPromptText(getValueFromKey("register_form_email"));
        this.loginField.setPromptText(getValueFromKey("register_form_login"));
        this.passwordField.setPromptText(getValueFromKey("register_form_password"));
        this.passwordConfirmField.setPromptText(getValueFromKey("register_form_password_confirm"));
    }

    /*
     * GETTERS AND SETTERS
     */

    public void setLogonWindow(PaneLogonWindowController logonWindow) {
        this.logonWindow = logonWindow;
    }

    private void setInfoLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.info(msg);
        System.out.println("INFO:  " + msg);
    }

    private void setErrorLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.error(msg);
        System.out.println("ERROR:  " + msg);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLPaneController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.configuration.FXMLFiles;
import com.genealogytree.services.implementation.GTUserServiceOnline;
import com.genealogytree.services.responses.ExceptionResponse;
import com.genealogytree.services.responses.ServerResponse;
import com.genealogytree.services.responses.UserResponse;
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
public class PaneRegisterFormController implements Initializable, FXMLPaneController {

    private static final Logger LOG = LogManager.getLogger(PaneRegisterFormController.class);

    private final String MANDATORY_MESSAGE = "This field cannot be empty";
    private ScreenManager manager;
    private ObjectProperty<GenealogyTreeContext> context = new SimpleObjectProperty<>();
    private GTUserServiceOnline serviceUser;
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
        this.context.addListener(new ChangeListener<GenealogyTreeContext>() {
            @Override
            public void changed(ObservableValue<? extends GenealogyTreeContext> observable,
                                GenealogyTreeContext oldValue, GenealogyTreeContext newValue) {
                serviceUser = newValue.getUserService();
            }
        });

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
        ServerResponse result = this.serviceUser.registerUser(this.mailBox.getText(), this.loginField.getText(),
                this.passwordField.getText());
        if (result instanceof UserResponse) {
            PaneRegisterConfirmationController confirmationController = (PaneRegisterConfirmationController) this.manager
                    .loadFxml(new PaneRegisterConfirmationController(), this.logonWindow.getRegisterForm(),
                            FXMLFiles.REGISTER_CONFIRMATION.toString());
            confirmationController.setLogonWindow(this.logonWindow);

            this.logonWindow.setCretentials(((UserResponse) result).getUser().getLogin(),
                    ((UserResponse) result).getUser().getPassword());

        } else if (result instanceof ExceptionResponse) {
            System.out.println("register incorrecte");
        } else {
            System.out.println("register status unknown");
        }
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
    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context.setValue(context);
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }

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

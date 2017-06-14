/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLPaneController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.application.ScreenManager.Where;
import com.genealogytree.configuration.FXMLFiles;
import com.genealogytree.services.responses.ServerResponse;
import com.genealogytree.services.responses.UserResponse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
public class PaneLogonFormController implements Initializable, FXMLPaneController {

    private static final Logger LOG = LogManager.getLogger(PaneLogonFormController.class);

    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private JFXTextField fieldLogin;
    @FXML
    private JFXPasswordField fieldPassword;
    @FXML
    private JFXButton buttonConnect;
    @FXML
    private Hyperlink linkForgetPassword;
    @FXML
    private Hyperlink linkRegister;
    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private PaneLogonWindowController logonWindow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LOG.info("Initialisation :  " + this.toString());

        this.languageBundle.setValue(rb);

		/*
         * Check condition to enable Connect button
		 */
        this.buttonConnect.setDisable(true);
        BooleanBinding disableBinding = Bindings.createBooleanBinding(
                () -> this.fieldLogin.getText().isEmpty() || this.fieldPassword.getText().isEmpty(),
                this.fieldLogin.textProperty(), this.fieldPassword.textProperty());
        this.buttonConnect.disableProperty().bind(disableBinding);
    }

    @FXML
    public void login() {
        ServerResponse result = this.context.getUserService().connect(this.fieldLogin.getText(), this.fieldPassword.getText());
        if (result instanceof UserResponse) {
            this.manager.loadFxml(new PaneWelcomeWindowController(), this.manager.getMainWindow().getRootWindow(), FXMLFiles.WELCOME_WINDOW.toString(), Where.CENTER);
        }
    }

    @FXML
    private void openRegisterForm() {
        this.logonWindow.loadRegisterForm();
        this.logonWindow.showRegisterForm();
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
        System.out.println("Change");
        this.fieldLogin.setPromptText(getValueFromKey("logon_form_login"));
        this.fieldPassword.setPromptText(getValueFromKey("logon_form_password"));
        this.linkForgetPassword.setText(getValueFromKey("logon_form_forgot_password"));
        this.linkRegister.setText(getValueFromKey("logon_form_register_link"));
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

    public void setCretentials(String login, String pass) {
        this.fieldLogin.setText(login);
        this.fieldPassword.setText(pass);
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
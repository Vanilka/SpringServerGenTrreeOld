/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.client.desktop.controllers.implementation.scene;

import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.configuration.messages.LogMessages;
import com.genealogytree.client.desktop.controllers.FXMLPane;
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
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
@Log4j2
public class PaneLogonFormController implements Initializable, FXMLPane {

    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();

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
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(rb);

		/*
         * Check condition to enable Connect button
		 */
        this.buttonConnect.setDisable(true);
        BooleanBinding disableBinding = Bindings.createBooleanBinding(
                () -> this.fieldLogin.getText().isEmpty() || this.fieldPassword.getText().isEmpty(),
                this.fieldLogin.textProperty(), this.fieldPassword.textProperty());
        this.buttonConnect.disableProperty().bind(disableBinding);
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    @FXML
    public void login() {
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



    public void setCretentials(String login, String pass) {
        this.fieldLogin.setText(login);
        this.fieldPassword.setText(pass);
    }

    public void setLogonWindow(PaneLogonWindowController logonWindow) {
        this.logonWindow = logonWindow;
    }

}

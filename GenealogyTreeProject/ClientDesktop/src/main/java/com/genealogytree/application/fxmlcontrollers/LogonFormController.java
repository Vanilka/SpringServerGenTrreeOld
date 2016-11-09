/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.application.ScreenManager.Where;
import com.genealogytree.configuration.FXMLFiles;
import com.genealogytree.configuration.traduction.Languages;
import com.genealogytree.services.responses.ServerResponse;
import com.genealogytree.services.responses.UserResponse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
public class LogonFormController implements Initializable, FXMLController {

	private static final Logger LOG = LogManager.getLogger(LogonFormController.class);

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

	private LogonWindowController logonWindow;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());

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
		ServerResponse result =this.context.getUserService().connect(this.fieldLogin.getText(), this.fieldPassword.getText());
		if(result instanceof UserResponse ) {
			this.manager.loadFxml(new MainApplicationWindowController(),  this.manager.getMainWindow().getRootWindow(), FXMLFiles.MAIN_APPLICATION_WINDOW.toString(), Where.CENTER);
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
	
	public void setCrenetrials(String login, String pass) {
		this.fieldLogin.setText(login);
		this.fieldPassword.setText(pass);
	}

	@Override
	public void setManager(ScreenManager manager) {
		this.manager = manager;
	}

	public void setLogonWindow(LogonWindowController logonWindow) {
		this.logonWindow = logonWindow;
	}

}

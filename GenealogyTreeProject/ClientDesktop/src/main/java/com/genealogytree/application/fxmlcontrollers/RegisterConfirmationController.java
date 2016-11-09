/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.configuration.traduction.Languages;
import com.genealogytree.services.GTUserService;
import com.genealogytree.services.responses.ExceptionResponse;
import com.genealogytree.services.responses.ServerResponse;
import com.genealogytree.services.responses.UserResponse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author mszymkow
 */
public class RegisterConfirmationController implements Initializable, FXMLController {
	private static final Logger LOG = LogManager.getLogger(RegisterConfirmationController.class);
	
	private ScreenManager manager;
	private ObjectProperty<GenealogyTreeContext> context = new SimpleObjectProperty<>();
	private GTUserService serviceUser;

	private LogonWindowController logonWindow;

	@FXML
	private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

	@FXML
	private AnchorPane registerConfirmation;

	@FXML
	private JFXButton buttonConfirm;
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());
		this.languageBundle.setValue(rb);

		/*
		 * Add Listener to listen context changes
		 */
		this.context.addListener(new ChangeListener<GenealogyTreeContext>() {
			@Override
			public void changed(ObservableValue<? extends GenealogyTreeContext> observable,
					GenealogyTreeContext oldValue, GenealogyTreeContext newValue) {
				serviceUser = newValue.getUserService();
			}
		});
	}

	@FXML
	private void returnLogon() {
		
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
      /*
       * TODO
       */
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

	public void setLogonWindow(LogonWindowController logonWindow) {
		this.logonWindow = logonWindow;
	}
}

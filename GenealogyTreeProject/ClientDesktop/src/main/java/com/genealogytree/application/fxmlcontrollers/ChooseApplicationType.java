/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application.fxmlcontrollers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.genealogytree.application.FXMLController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.configuration.FXMLFiles;
import com.genealogytree.configuration.traduction.Languages;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
public class ChooseApplicationType implements Initializable, FXMLController {

	private static final Logger LOG = LogManager.getLogger(ChooseApplicationType.class);
	private ScreenManager manager;
	private GenealogyTreeContext context;

	@FXML
	private AnchorPane chooseAppType;
	@FXML
	private Pane localProjectPane;
	@FXML
	private Pane onlineProjectPane;
	@FXML
	private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());
		this.languageBundle.setValue(rb);
		addTopOffsetListener();
	}

	public void addTopOffsetListener() {

		this.chooseAppType.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double y = (newValue.doubleValue() - localProjectPane.getHeight()) / 2;
				localProjectPane.setLayoutY(y);
				onlineProjectPane.setLayoutY(y);
			}
		});
	}

	@Override
	public void setManager(ScreenManager manager) {
		this.manager = manager;
		initLocalProjectPane();
		initOnlineProjectPane();
	}

	public void initLocalProjectPane() {
		this.manager.loadFxml(new LocalApplicationChoiceController(), localProjectPane,
				FXMLFiles.LOCAL_APPLICATION_CHOICE.toString());

	}

	public void initOnlineProjectPane() {
		this.manager.loadFxml(new OnlineApplicationChoiceController(), onlineProjectPane,
				FXMLFiles.ONLINE_APPLICATION_CHOICE.toString());
	}
	
	private void setListeners() {
		this.languageBundle.addListener(new ChangeListener<ResourceBundle>() {

			@Override
			public void changed(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue,
					ResourceBundle newValue) {
				reloadElements();
			}
		});
	}
	private void reloadElements() {
		// Nothing to do
	}

	@Override
	public void setContext(GenealogyTreeContext context) {
		this.context = context;
		this.languageBundle.bind(context.getBundleProperty());
		setListeners();
	}

}

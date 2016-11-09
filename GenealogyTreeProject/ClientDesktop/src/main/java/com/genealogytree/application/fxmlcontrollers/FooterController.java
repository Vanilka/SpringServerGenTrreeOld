/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application.fxmlcontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.genealogytree.application.FXMLController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
public class FooterController implements Initializable, FXMLController {

	private static final Logger LOG = LogManager.getLogger(FooterController.class);
	private ScreenManager manager;
	private GenealogyTreeContext context;

	@FXML
	private Label footerCopyright;

	@FXML
	private ResourceBundle languageBundle;

	/*
	 * Initializes the controller class.
	 */

	private void setCopyrightText(String s) {
		this.footerCopyright.setText(s);
	}

	@Override
	public void setManager(ScreenManager manager) {
		this.manager = manager;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());
		this.languageBundle = rb;
		setCopyrightText("Martyna SZYMKOWIAK 2016");
	}

	@Override
	public void setContext(GenealogyTreeContext context) {
		this.context = context;
	}
}

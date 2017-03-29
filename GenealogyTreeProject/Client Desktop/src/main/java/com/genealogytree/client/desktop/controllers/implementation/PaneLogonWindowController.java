/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.client.desktop.controllers.implementation;

import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.configuration.enums.FXMLFile;
import com.genealogytree.client.desktop.controllers.FXMLPane;
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

/**
 * FXML Controller class
 *
 * @author vanilka
 */
public class PaneLogonWindowController implements Initializable, FXMLPane {

    private static final Logger LOG = LogManager.getLogger(PaneLogonWindowController.class);

    private PaneLogonFormController logonFormImpl;
    private PaneRegisterFormController registerFormImpl;
    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();


    @FXML
    private AnchorPane logonWindowPane;

    @FXML
    private AnchorPane logonForm;

    @FXML
    private AnchorPane registerForm;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.info("Initialisation :  " + this.toString());

        this.languageBundle.setValue(rb);

        addTopOffsetListener(this.logonForm);
        addTopOffsetListener(this.registerForm);
        addLeftOffsetListener(this.registerForm);

        this.logonForm.resize(300, 400);
        this.registerForm.resize(400, 500);
        showLogonForm();
        this.logonFormImpl = (PaneLogonFormController) sc.loadFxml(this.logonFormImpl, this.logonForm,
                FXMLFile.LOGON_FORM);
        this.logonFormImpl.setLogonWindow(this);
    }

    public void addTopOffsetListener(AnchorPane pane) {
        this.logonWindowPane.heightProperty()
                .addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    double y = (newValue.doubleValue() - pane.getHeight()) / 2;
                    pane.setLayoutY(y);
                });
    }

    public void addLeftOffsetListener(AnchorPane pane) {
        this.logonWindowPane.widthProperty()
                .addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    double x = (newValue.doubleValue() - pane.getWidth()) / 2;
                    pane.setLayoutX(x);
                });
    }

    public void showRegisterForm() {
        this.registerForm.setVisible(true);
        this.logonForm.setVisible(false);
    }

    public void showLogonForm() {
        this.logonForm.setVisible(true);
        this.registerForm.setVisible(false);
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
        // Nothing to do
    }

    /*
     * GETTERS AND SETTERS
     */

    public void loadRegisterForm() {
        this.registerFormImpl = (PaneRegisterFormController) sc.loadFxml(this.registerFormImpl, this.registerForm,
                FXMLFile.REGISTER_FORM);
        this.registerFormImpl.setLogonWindow(this);
    }

    public void setManager(ScreenManager manager) {
    }

    public void setCretentials(String login, String pass) {
        this.logonFormImpl.setCretentials(login, pass);
    }

    public AnchorPane getRegisterForm() {
        return registerForm;
    }

    public void setRegisterForm(AnchorPane registerForm) {
        this.registerForm = registerForm;
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

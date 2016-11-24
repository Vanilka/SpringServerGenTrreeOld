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
import com.genealogytree.services.implementation.GTFamillyServiceOnline;
import com.genealogytree.services.implementation.GTUserServiceOnline;
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
public class PaneLogonWindowController implements Initializable, FXMLPaneController {

    private static final Logger LOG = LogManager.getLogger(PaneLogonWindowController.class);

    private ScreenManager manager;
    private PaneLogonFormController logonFormImpl;
    private PaneRegisterFormController registerFormImpl;
    private GenealogyTreeContext context;

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
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());

        this.languageBundle.setValue(rb);

        addTopOffsetListener(this.logonForm);
        addTopOffsetListener(this.registerForm);
        addLeftOffsetListener(this.registerForm);

        this.logonForm.resize(300, 400);
        this.registerForm.resize(400, 500);
        showLogonForm();
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
    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
        this.context.setFamilyService(new GTFamillyServiceOnline(this.context));
        this.context.setUserService(new GTUserServiceOnline(this.context));
    }

    public void loadRegisterForm() {
        this.registerFormImpl = (PaneRegisterFormController) this.manager.loadFxml(this.registerFormImpl, this.registerForm,
                FXMLFiles.REGISTER_FORM.toString());
        this.registerFormImpl.setLogonWindow(this);
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;

        this.logonFormImpl = (PaneLogonFormController) this.manager.loadFxml(this.logonFormImpl, this.logonForm,
                FXMLFiles.LOGON_FORM.toString());
        this.logonFormImpl.setLogonWindow(this);
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

}

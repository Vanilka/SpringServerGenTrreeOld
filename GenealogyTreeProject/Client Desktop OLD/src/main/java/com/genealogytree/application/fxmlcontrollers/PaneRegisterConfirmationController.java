/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealogytree.application.fxmlcontrollers;

import genealogytree.application.FXMLPaneController;
import genealogytree.application.GenealogyTreeContext;
import genealogytree.application.ScreenManager;
import genealogytree.services.implementation.GTUserServiceOnline;
import com.jfoenix.controls.JFXButton;
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
 * @author mszymkow
 */
public class PaneRegisterConfirmationController implements Initializable, FXMLPaneController {
    private static final Logger LOG = LogManager.getLogger(PaneRegisterConfirmationController.class);

    private ScreenManager manager;
    private ObjectProperty<GenealogyTreeContext> context = new SimpleObjectProperty<>();
    private GTUserServiceOnline serviceUser;

    private PaneLogonWindowController logonWindow;

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
        setInfoLog("Initialisation :  " + this.toString());
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

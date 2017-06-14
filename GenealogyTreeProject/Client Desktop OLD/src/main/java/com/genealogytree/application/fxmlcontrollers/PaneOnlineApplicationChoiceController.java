/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealogytree.application.fxmlcontrollers;

import genealogytree.application.FXMLPaneController;
import genealogytree.application.GenealogyTreeContext;
import genealogytree.application.ScreenManager;
import genealogytree.configuration.FXMLFiles;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
public class PaneOnlineApplicationChoiceController implements Initializable, FXMLPaneController {

    private static final Logger LOG = LogManager.getLogger(PaneOnlineApplicationChoiceController.class);
    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private Label onlineApplicationLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.info("Initialisation :  " + this.toString());
        this.languageBundle.setValue(rb);
    }

    @FXML
    public void selectOnlineApplication() {
        this.manager.loadFxml(new PaneLogonWindowController(), this.manager.getMainWindow().getRootWindow(), FXMLFiles.LOGON_WINDOW.toString(), ScreenManager.Where.CENTER);
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
        this.onlineApplicationLabel.setText(getValueFromKey("application_choice_online").toUpperCase());
    }

    /*
     * GETTERS AND SETTERS
     */
    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
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


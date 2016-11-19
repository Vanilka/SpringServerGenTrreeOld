package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
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

public class MainApplicationWindowController implements Initializable, FXMLController {

    private static final Logger LOG = LogManager.getLogger(MainApplicationWindowController.class);

    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private AnchorPane templateAnchorPane;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());

        this.languageBundle.setValue(rb);


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
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }
}

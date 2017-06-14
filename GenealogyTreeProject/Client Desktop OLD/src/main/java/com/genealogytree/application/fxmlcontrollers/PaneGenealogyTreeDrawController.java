package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLPaneController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.sources.templates.templates.TemplatePaneController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 03/01/2017.
 */
public class PaneGenealogyTreeDrawController extends AnchorPane implements Initializable, FXMLPaneController {

    private static final Logger LOG = LogManager.getLogger(TemplatePaneController.class);
    @FXML
    ScrollPane workAnchorPane;
    @FXML
    AnchorPane paneGenealogyTreeDraw;
    @FXML
    HBox projectContent;
    private ScreenManager manager;
    private GenealogyTreeContext context;
    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInfoLog("Initialisation :  " + this.toString());

        this.languageBundle.setValue(rb);
    }

    /*
    *
     */

    public void Test() {
        this.context.getFamilyService().currentFamilyProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {

                System.out.println("Family is null");
            } else {
                System.out.println(" Family is:" + newValue.toString());
            }
        });
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
        Test();
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
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

    public AnchorPane getPaneGenealogyTreeDraw() {
        return paneGenealogyTreeDraw;
    }
}
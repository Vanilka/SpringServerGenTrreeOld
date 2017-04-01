package com.genealogytree.client.desktop.controllers.implementation;

import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.configuration.messages.LogMessages;
import com.genealogytree.client.desktop.controllers.FXMLPane;
import com.genealogytree.client.desktop.controllers.implementation.custom.GTNode;
import com.genealogytree.client.desktop.domain.GTX_Family;
import com.genealogytree.client.desktop.service.GenTreeDrawingService;
import com.genealogytree.client.desktop.service.GenTreeDrawingServiceImpl;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 03/01/2017.
 */
@Log4j2
public class PaneGenealogyTreeDrawController extends AnchorPane implements Initializable, FXMLPane{

    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();

    @FXML
    ScrollPane workAnchorPane;

    @FXML
    AnchorPane paneGenealogyTreeDraw;
    @FXML
    HBox projectContent;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private GenTreeDrawingService drawingService;
    {
        drawingService = new GenTreeDrawingServiceImpl();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        sc.register(this);
        this.languageBundle.setValue(rb);
        addLanguageListener();
        initRelationListener();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);


    }

    /*

     */
    public void initRelationListener() {
        context.getService().getCurrentFamily().getGtx_relations().addListener((InvalidationListener) observable -> {
            // TODO  Redraw Tree
            projectContent.getChildren().clear();
            getCurrentFamilly().getGtx_relations()
                    .filtered(c-> c.getSimLeft() == null)
                    .filtered(c-> c.getSimRight() == null)
                    .forEach(c -> {
                        GTNode node = new GTNode(c);
                        drawingService.startDraw(node);
                        projectContent.getChildren().add(node);
                    });
        });




    }


    public WritableImage Image() {
        WritableImage image = projectContent.snapshot(new SnapshotParameters(), null);
        return image;
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

    public AnchorPane getPaneGenealogyTreeDraw() {
        return paneGenealogyTreeDraw;
    }

    public GTX_Family getCurrentFamilly() {
        return context.getService().getCurrentFamily();
    }
}

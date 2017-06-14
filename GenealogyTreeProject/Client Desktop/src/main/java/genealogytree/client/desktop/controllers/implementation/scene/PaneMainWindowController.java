/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealogytree.client.desktop.controllers.implementation.scene;

import genealogytree.client.desktop.configuration.ContextGT;
import genealogytree.client.desktop.configuration.ScreenManager;
import genealogytree.client.desktop.configuration.messages.LogMessages;
import genealogytree.client.desktop.controllers.FXMLPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author vanilka
 */
@Log4j2
public class PaneMainWindowController implements Initializable, FXMLPane {

    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();

    @FXML
    private ResourceBundle languageBundle;

    @FXML
    private BorderPane rootWindow;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle = rb;
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);

    }

    public BorderPane getRootWindow() {
        return this.rootWindow;
    }

    public void setRootWindow(BorderPane rootWindow) {
        this.rootWindow = rootWindow;
    }

}

package com.genealogytree.client.desktop.controllers.implementation.scene;

import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.configuration.messages.AppTitles;
import com.genealogytree.client.desktop.configuration.messages.LogMessages;
import com.genealogytree.client.desktop.controllers.FXMLAnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 19/03/2017.
 */
@Log4j2
public class FooterController implements Initializable, FXMLAnchorPane {

    public static final ScreenManager sc = ScreenManager.getInstance();

    @FXML
    private Label footerCopyright;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        initFooter();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    private void initFooter() {
        this.footerCopyright.setText(AppTitles.APP_FOOTER);
    }
}

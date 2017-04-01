package com.genealogytree.client.desktop.controllers.implementation;

import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.configuration.messages.LogMessages;
import com.genealogytree.client.desktop.controllers.FXMLBorderPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 19/03/2017.
 */
@Log4j2
public class MainScreen implements Initializable, FXMLBorderPane {

    private static final ScreenManager sm = ScreenManager.getInstance();

    @FXML
    @Setter
    @Getter
    private BorderPane rootBorderPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);

    }
}

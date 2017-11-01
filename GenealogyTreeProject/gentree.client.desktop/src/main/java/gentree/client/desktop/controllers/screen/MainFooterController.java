package gentree.client.desktop.controllers.screen;


import gentree.client.desktop.configuration.messages.AppTitles;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
public class MainFooterController implements Initializable, FXMLController, FXMLAnchorPane {

    @FXML
    private Label FOOTER_COPYRIGHTS;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        initFooter();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    private void initFooter() {
        this.FOOTER_COPYRIGHTS.setText(AppTitles.APP_FOOTER);
    }
}

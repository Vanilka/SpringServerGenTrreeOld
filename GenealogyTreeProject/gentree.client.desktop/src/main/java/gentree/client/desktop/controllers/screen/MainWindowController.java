package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
@Log4j2
public class MainWindowController implements Initializable, FXMLController, FXMLPane {


    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }
}

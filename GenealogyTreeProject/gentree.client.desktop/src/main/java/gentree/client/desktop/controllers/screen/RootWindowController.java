package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.controllers.FXMLBorderPane;
import gentree.client.desktop.controllers.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 10/07/2017.
 */

@Log4j2
public class RootWindowController implements Initializable, FXMLController, FXMLBorderPane {

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.languageBundle.setValue(resources);
    }
}
package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLTab;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */
@Getter
@Setter
public class TabOpenNewProjectController implements Initializable, FXMLController, FXMLTab {

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private JFXTextField familyNameField;

    private Tab tab;
    private JFXTabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        languageBundle.setValue(resources);
    }

    @Override
    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        this.tab = tab;
    }


}

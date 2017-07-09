package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.service.GenTreeDrawingService;
import gentree.client.desktop.service.implementation.GenTreeDrawingServiceImpl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */
@Log4j2
public class ScreenMainRightController implements Initializable, FXMLController, FXMLAnchorPane {

    @FXML
    private ScrollPane treeDrawScrollPane;

    @FXML
    private AnchorPane treeDrawAnchorPane;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @Getter
    private GenTreeDrawingService drawingService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.languageBundle.setValue(resources);
        sm.register(this);
        drawingService = new GenTreeDrawingServiceImpl();
        treeDrawScrollPane.setFitToHeight(true);
        treeDrawScrollPane.setFitToWidth(true);
        this.languageBundle.setValue(resources);
    }


    public WritableImage Image() {
        WritableImage image = treeDrawAnchorPane.snapshot(new SnapshotParameters(), null);
        return image;
    }

    /*
    *   LISTEN LANGUAGE CHANGES
    */
    private void addLanguageListener() {
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        // Nothing to do
    }
}

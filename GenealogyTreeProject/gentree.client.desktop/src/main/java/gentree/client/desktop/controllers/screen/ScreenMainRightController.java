package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.service.GenTreeDrawingService;
import gentree.client.visualization.service.implementation.GenTreeDrawingServiceImpl;
import gentree.client.visualization.service.implementation.GenTreeImageGenerator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */
@Log4j2
public class ScreenMainRightController extends AnchorPane implements Initializable, FXMLController, FXMLAnchorPane {

    private final GenTreeImageGenerator imageGenerator = GenTreeImageGenerator.INSTANCE;
    @FXML
    private ScrollPane TREE_DRAW_SCROLL_PANE;

    @FXML
    private AnchorPane TREE_DRAW_ANCHOR_PANE;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private HBox GEN_TREE_CONTENT;

    @Getter
    private GenTreeDrawingService drawingService;
    private ChangeListener<? super ResourceBundle> languageListener = this::languageChange;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);

        this.languageBundle.setValue(resources);
        sm.register(this);

        drawingService = new GenTreeDrawingServiceImpl(GEN_TREE_CONTENT);
        sm.register(drawingService);

        initRelationListener();
        redrawTree();

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }


    public WritableImage Image() {

        return imageGenerator.doScreen(TREE_DRAW_ANCHOR_PANE, context.getService().getCurrentFamily().getName());
    }

    public void redrawTree() {
        System.out.println(drawingService + "  From Screen Main Right");
        drawingService.startDraw();
    }


    public void initRelationListener() {
/*        context.getService().getCurrentFamily().getRelations().addListener((InvalidationListener) c -> {
            // TODO  Redraw Tree optimalization
            redrawTree();
        });*/
    }

    /*
     *   LISTEN LANGUAGE CHANGES
     */
    private void addLanguageListener() {
        this.languageBundle.addListener(languageListener);
    }

    private void cleanListeners() {
        this.languageBundle.removeListener(languageListener);
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        // Nothing to do
    }

    private void languageChange(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        reloadElements();
    }
}

package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */
@Log4j2
public class ScreenLoginRegisterController implements Initializable, FXMLController, FXMLAnchorPane {

    @FXML
    private AnchorPane SCREEN_LOGON_REGISTER;

    @FXML
    private AnchorPane LOGON_FORM;

    @FXML
    private AnchorPane REGISTER_FORM;

    private PaneLogonController logonController;


    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);

        this.languageBundle.setValue(resources);
        initLogonForm();
        addTopOffsetListener(this.LOGON_FORM);
        addTopOffsetListener(this.REGISTER_FORM);
        addLeftOffsetListener(this.REGISTER_FORM);
        this.LOGON_FORM.resize(300, 400);
        this.REGISTER_FORM.resize(400, 500);

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }


    private void initLogonForm() {
        logonController = (PaneLogonController) sm.loadFxml(new PaneLogonController(), LOGON_FORM, FilesFXML.PANE_LOGON_FXML);
    }

    public void addTopOffsetListener(AnchorPane pane) {
        // pane.layoutYProperty().bind(SCREEN_LOGON_REGISTER.heightProperty().subtract(pane.heightProperty().divide(2)));
    }

    public void addLeftOffsetListener(AnchorPane pane) {
        //pane.layoutXProperty().bind(SCREEN_LOGON_REGISTER.widthProperty().subtract(pane.widthProperty().divide(2)));
    }

    public void cleanListeners() {
        cleanBinding(this.LOGON_FORM);
        cleanBinding(this.REGISTER_FORM);
    }

    public void cleanBinding(AnchorPane pane) {
        //  pane.layoutYProperty().unbind();
        //    pane.layoutXProperty().unbind();
    }
}

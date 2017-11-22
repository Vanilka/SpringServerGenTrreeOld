package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */

@Log4j2
public class ScreenMainController implements Initializable, FXMLController, FXMLAnchorPane {

    @FXML
    @Getter
    @Setter
    private AnchorPane screenMainAnchorPane;

    @FXML
    private AnchorPane SCREEN_MAIN_LEFT;

    @FXML
    private AnchorPane SCREEN_MAIN_RIGHT;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    @Getter
    @Setter
    private BorderPane rootBorderPane;

    private ScreenMainLeftController screenMainLeftController;
    private ScreenMainRightController screenMainRightController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        initPanes();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    private void initPanes() {
        sm.register(this);
        screenMainLeftController = (ScreenMainLeftController) sm.loadFxml(new ScreenMainLeftController(), SCREEN_MAIN_LEFT, FilesFXML.SCREEN_MAIN_LEFT_FXML);
        screenMainRightController = (ScreenMainRightController) sm.loadFxml(new ScreenMainRightController(), SCREEN_MAIN_RIGHT, FilesFXML.SCREEN_MAIN_RIGHT_FXML);
    }


    public void showInfoSim(Member member) {
        removerOthersInfoRelationsOrSim();
        SCREEN_MAIN_LEFT.getChildren().forEach(n -> n.setVisible(false));
        PaneShowInfoSim paneShowInfoSim = (PaneShowInfoSim) sm.loadAdditionalFxmltoAnchorPane(
                new PaneShowInfoSim(), SCREEN_MAIN_LEFT, FilesFXML.PANE_SHOW_INFO_MEMBER_FXML);
        paneShowInfoSim.setMember(member);
    }

    public void showInfoRelation(Relation relation) {
        removerOthersInfoRelationsOrSim();
        SCREEN_MAIN_LEFT.getChildren().forEach(n -> n.setVisible(false));
        PaneShowInfoRelation paneShowInfoRelation = (PaneShowInfoRelation) sm.loadAdditionalFxmltoAnchorPane(
                new PaneShowInfoSim(), SCREEN_MAIN_LEFT, FilesFXML.PANE_SHOW_INFO_RELATION_FXML);
        paneShowInfoRelation.setRelation(relation);
    }

    private void removerOthersInfoRelationsOrSim() {
        SCREEN_MAIN_LEFT.getChildren().removeIf(n -> n.getId().equals("paneShowInfoRelation"));
        SCREEN_MAIN_LEFT.getChildren().removeIf(n -> n.getId().equals("paneShowInfoSim"));
    }


    public void removeInfoPanel(Node node) {
        SCREEN_MAIN_LEFT.getChildren().remove(node);
        SCREEN_MAIN_LEFT.getChildren().forEach(n -> n.setVisible(true));
    }


}

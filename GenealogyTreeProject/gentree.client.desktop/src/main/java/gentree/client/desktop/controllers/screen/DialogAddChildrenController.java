package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogWithMemberController;
import gentree.client.desktop.domain.Member;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 07/08/2017.
 */

@Log4j2
public class DialogAddChildrenController implements Initializable, FXMLController, FXMLDialogWithMemberController {

    private ObjectProperty<Member> member;


    private Stage stage;
    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    {
        member = new SimpleObjectProperty<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.languageBundle.setValue(resources);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setMember(Member m) {
        this.member.setValue(m);
    }
}
package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import gentree.client.desktop.configurations.GenTreeDefaultProperties;
import gentree.client.desktop.configurations.GenTreeProperties;
import gentree.client.desktop.configurations.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogWithMemberController;
import gentree.client.desktop.controllers.tree_elements.MemberCard;
import gentree.client.desktop.domain.Member;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 30/07/2017.
 */

@Log4j2
public class DialogAddSpouseController implements Initializable, FXMLController, FXMLDialogWithMemberController {

    private final Properties properties = GenTreeProperties.INSTANCE.getAppProperties();
    private final ObjectProperty<Member> member;
    private final ObjectProperty<Member> spouse;
    private final List<Member> spouseList;

    private final MemberCard memberCard;
    private final MemberCard spouseCard;

    @FXML
    private AnchorPane currentMemberPane;

    @FXML
    private AnchorPane spousePane;

    @FXML
    private JFXButton chooseSpouse;

    @FXML
    private JFXCheckBox checkBoxHomoAllowed;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private Stage stage;

    {
        member = new SimpleObjectProperty<>();
        spouse = new SimpleObjectProperty<>();
        spouseList = new ArrayList<>();

        memberCard = new MemberCard();
        spouseCard = new MemberCard();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        initPanes();
        initListeners();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }


    @FXML
    public void confirm(ActionEvent actionEvent) {
        stage.close();
    }

    private void initPanes() {
        currentMemberPane.getChildren().add(memberCard);
        spousePane.getChildren().add(spouseCard);

        checkBoxHomoAllowed.setSelected(Boolean.valueOf(properties.getProperty(GenTreeDefaultProperties.PARAM_DEFAULT_ALLOW_HOMO)));
    }

    private void populateSpouseList(Member m) {
        spouseList.clear();

        if(m != null) {

        }
    }


    /*
        Listeners
     */

    private void initListeners() {
        initMemberListener();
    }

    private void initMemberListener() {
        member.addListener((observable, oldValue, newValue) -> {
            memberCard.setMember(newValue);
            populateSpouseList(newValue);
        });
    }



    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setMember(Member m) {
        this.member.set(m);
    }

    @FXML
    public void openChoosingSpouse(ActionEvent actionEvent) {

    }
}
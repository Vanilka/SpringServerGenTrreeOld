package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.enums.PropertiesKeys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogWithMemberController;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.visualization.elements.MemberCard;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.RelationType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Martyna SZYMKOWIAK on 30/07/2017.
 */

@Log4j2
public class DialogAddSpouseController implements Initializable, FXMLController, FXMLDialogWithMemberController {

    private final Configuration config = GenTreeProperties.INSTANCE.getConfiguration();
    private final ObjectProperty<Member> member;
    private final ObjectProperty<Member> spouse;

    private final MemberCard memberCard;
    private final MemberCard spouseCard;

    @FXML
    public ComboBox<RelationType> RELATION_TYPE_COMBO_BOX;

    @FXML
    private AnchorPane CURRENT_MEMBER_PANE;

    @FXML
    private AnchorPane SPOUSE_PANE;

    @FXML
    private JFXButton BUTTON_CHOOSE_SPOUSE;

    @FXML
    private JFXCheckBox CHECK_BOX_SET_CURRENT;

    @FXML
    private JFXCheckBox CHECK_BOX_HOMO_ALLOWED;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private Stage stage;
    private ChangeListener<Member> memberListener = this::memberListener;
    private ChangeListener<Member> spouseListener = this::spouseListener;
    private ChangeListener<? super RelationType> relationTypeListener = this::relationTypeChanged;

    {
        member = new SimpleObjectProperty<>();
        spouse = new SimpleObjectProperty<>();

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
        cleanListener();
    }


    @FXML
    public void confirm(ActionEvent actionEvent) {

        context.getService().addRelation(member.get(), spouse.get(), RELATION_TYPE_COMBO_BOX.getValue(), CHECK_BOX_SET_CURRENT.isSelected());
        stage.close();
        cleanListener();
    }

    @FXML
    public void openChoosingSpouse(ActionEvent actionEvent) {
        List<Member> spouseList = generateSpouseList();

        Member m = sm.showNewDialog(new DialogChooseMemberController(), member.get(), spouseList, FilesFXML.DIALOG_CHOOSE_MEMBER);
        if (m != null) spouse.set(m);
    }


    private List<Member> generateSpouseList() {
        List<Member> list = context.getService().getCurrentFamily().getMembers().stream()
                .filter(mbr -> !mbr.equals(member.get()))
                .filter(mbr -> mbr.getGender() != returnGenderDenied())
                .filter(mbr -> !isAscOf(member.get(), mbr))
                .filter(mbr -> !isDescOf(member.get(), mbr))
                .collect(Collectors.toList());
        try {
            Relation born = context.getService().getCurrentFamily().findBornRelation(member.get());

            for (Member sibling : born.getChildren()) {
                list = list.stream().filter(p -> !p.equals(sibling)).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
/*
        list = removeAscends(list, member.get());
        list = removeDescends(list, member.get());
*/
        return list;
    }


    /**
     * Verify that Sim provided is Ascendant of grain
     *
     * @param grain
     * @param sim
     * @return
     */
    private boolean isAscOf(Member grain, Member sim) {
        return sim != null && context.getService().isAscOf(grain, sim);
    }


    /**
     * Verify that Sim provided is Descendant of grain
     *
     * @param grain
     * @param sim
     * @return
     */
    private boolean isDescOf(Member grain, Member sim) {
        return sim != null && context.getService().isDescOf(grain, sim);
    }


    /*
        Init Methods
     */

    private void initPanes() {
        CURRENT_MEMBER_PANE.getChildren().add(memberCard);
        SPOUSE_PANE.getChildren().add(spouseCard);
        CHECK_BOX_HOMO_ALLOWED.setSelected(config.getBoolean(PropertiesKeys.PARAM_DEFAULT_ALLOW_HOMO));
        initRelationTypeComboBox();
        CHECK_BOX_SET_CURRENT.disableProperty()
                .bind(Bindings.createBooleanBinding((
                                () -> RELATION_TYPE_COMBO_BOX.getValue().equals(RelationType.NEUTRAL)),
                        RELATION_TYPE_COMBO_BOX.valueProperty()));
        RELATION_TYPE_COMBO_BOX.valueProperty().addListener(relationTypeListener);
    }

    private void initRelationTypeComboBox() {
        RELATION_TYPE_COMBO_BOX.setCellFactory(sm.getCustomRelationListCell());
        RELATION_TYPE_COMBO_BOX.setButtonCell(sm.getCustomRelationListCell().call(null));
        RELATION_TYPE_COMBO_BOX.getItems().addAll(RelationType.values());
        RELATION_TYPE_COMBO_BOX.getSelectionModel().select(RelationType.NEUTRAL);
        RELATION_TYPE_COMBO_BOX.setDisable(true);
    }

    private Gender returnGenderDenied() {
        if (CHECK_BOX_HOMO_ALLOWED.isSelected()) {
            return null;
        }
        return member.get().getGender();

    }
    /*
        Listeners
     */

    private void initListeners() {
        member.addListener(memberListener);
        spouse.addListener(spouseListener);

    }

    private void cleanListener() {
        member.removeListener(memberListener);
        spouse.removeListener(spouseListener);
        RELATION_TYPE_COMBO_BOX.valueProperty().removeListener(relationTypeListener);
        CHECK_BOX_SET_CURRENT.disableProperty().unbind();
    }

    private void memberListener(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
        memberCard.setMember(newValue);
    }

    private void spouseListener(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
        spouseCard.setMember(newValue);
        if (newValue == null) {
            RELATION_TYPE_COMBO_BOX.getSelectionModel().select(RelationType.NEUTRAL);
            RELATION_TYPE_COMBO_BOX.setDisable(true);
        } else {
            RELATION_TYPE_COMBO_BOX.setDisable(false);
        }
    }


    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setFather(Member m) {
        this.member.set(m);
    }

    private void relationTypeChanged(ObservableValue<? extends RelationType> observable, RelationType oldValue, RelationType newValue) {
        if (newValue.equals(RelationType.NEUTRAL)) {
            CHECK_BOX_SET_CURRENT.setSelected(false);
        }
    }
}
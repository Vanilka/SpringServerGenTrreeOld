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
import gentree.exception.NotUniqueBornRelationException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    public ComboBox<RelationType> relationTypeComboBox;

    @FXML
    private AnchorPane currentMemberPane;

    @FXML
    private AnchorPane spousePane;

    @FXML
    private JFXButton chooseSpouse;

    @FXML
    private JFXCheckBox checkBoxSetCurrent;

    @FXML
    private JFXCheckBox checkBoxHomoAllowed;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private Stage stage;

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
    }


    @FXML
    public void confirm(ActionEvent actionEvent) {

        context.getService().addRelation(member.get(), spouse.get(), relationTypeComboBox.getValue(), checkBoxSetCurrent.isSelected());
        stage.close();
    }

    @FXML
    public void openChoosingSpouse(ActionEvent actionEvent) {
        List<Member> spouseList = generateSpouseList();

        Member m = sm.showNewDialog(new DialogChooseMemberController(), member.get(), spouseList, FilesFXML.DIALOG_CHOOSE_MEMBER);
        if (m != null) spouse.set(m);
    }


    private ObservableList<Member> generateSpouseList() {
        ObservableList<Member> list = context.getService().getCurrentFamily().getMembers()
                .filtered(mbr -> !mbr.equals(member.get()))
                .filtered(mbr -> mbr.getGender() != returnGenderDenied())
                .filtered(mbr -> !isAscOf(member.get(), mbr))
                .filtered(mbr -> !isDescOf(member.get(), mbr));

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

    @Deprecated
    private ObservableList<Member> removeAscends(ObservableList<Member> list, Member m) {

        try {
            Relation bornRelation = context.getService().getCurrentFamily().findBornRelation(m);
            /*
                Remove Sibling
             */
            for (Member sibbling : bornRelation.getChildren()) {
                list = list.filtered(p -> !p.equals(m));
            }
            /*
                RemoveLeft
             */
            if (bornRelation.getLeft() != null) {
                list = list.filtered(p -> !p.equals(bornRelation.getLeft()));
                list = removeAscends(list, bornRelation.getLeft());
            }
            /*
                Remove Rightrs
             */
            if (bornRelation.getRight() != null) {
                list = list.filtered(p -> !p.equals(bornRelation.getRight()));
                list = removeAscends(list, bornRelation.getRight());
            }
        } catch (NotUniqueBornRelationException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    @Deprecated
    private ObservableList<Member> removeDescends(ObservableList<Member> list, Member m) {
        /*
            Find relations that M is father or mother
         */
        List<Relation> relations = context.getService().getCurrentFamily().getRelations()
                .filtered(p -> (p.getRight() != null && p.getRight().equals(m))
                        || (p.getLeft() != null && p.getLeft().equals(m)));

        for (Relation r : relations) {
            if (r.getChildren() != null && r.getChildren().size() > 0) {
                for (Member child : r.getChildren()) {
                    list = list.filtered(q -> !q.equals(child));
                    removeDescends(list, child);
                }
            }
        }
        return list;
    }

    /*
        Init Methods
     */

    private void initPanes() {
        currentMemberPane.getChildren().add(memberCard);
        spousePane.getChildren().add(spouseCard);
        checkBoxHomoAllowed.setSelected(config.getBoolean(PropertiesKeys.PARAM_DEFAULT_ALLOW_HOMO));
        initRelationTypeComboBox();
        checkBoxSetCurrent.disableProperty()
                .bind(Bindings.createBooleanBinding((
                                () -> relationTypeComboBox.getValue().equals(RelationType.NEUTRAL)),
                        relationTypeComboBox.valueProperty()));
        relationTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(RelationType.NEUTRAL)) {
                checkBoxSetCurrent.setSelected(false);
            }
        });
    }

    private void initRelationTypeComboBox() {
        relationTypeComboBox.setCellFactory(sm.getCustomRelationListCell());
        relationTypeComboBox.setButtonCell(sm.getCustomRelationListCell().call(null));
        relationTypeComboBox.getItems().addAll(RelationType.values());
        relationTypeComboBox.getSelectionModel().select(RelationType.NEUTRAL);
        relationTypeComboBox.setDisable(true);
    }

    private Gender returnGenderDenied() {
        if (checkBoxHomoAllowed.isSelected()) {
            return null;
        }
        return member.get().getGender();

    }
    /*
        Listeners
     */

    private void initListeners() {
        initMemberListener();
        initSpouseListener();
    }

    private void initMemberListener() {
        member.addListener((observable, oldValue, newValue) -> {
            memberCard.setMember(newValue);
        });
    }

    private void initSpouseListener() {
        spouse.addListener((observable, oldValue, newValue) -> {
            spouseCard.setMember(newValue);
            if (newValue == null) {
                relationTypeComboBox.getSelectionModel().select(RelationType.NEUTRAL);
                relationTypeComboBox.setDisable(true);
            } else {
                relationTypeComboBox.setDisable(false);
            }
        });
    }


    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setFather(Member m) {
        this.member.set(m);
    }
}
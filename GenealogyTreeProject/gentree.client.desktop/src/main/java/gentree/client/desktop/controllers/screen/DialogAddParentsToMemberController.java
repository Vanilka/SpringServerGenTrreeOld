package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import gentree.client.desktop.configuration.enums.FilesFXML;
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
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */

@Log4j2
public class DialogAddParentsToMemberController implements Initializable, FXMLController, FXMLDialogWithMemberController {

    private final ObjectProperty<Member> member;
    private final ObjectProperty<Member> mother;
    private final ObjectProperty<Member> father;
    private final ObjectProperty<Relation> existingRelation;
    private final ObjectProperty<Relation> currentBornRelation;
    private final MemberCard currentMemberCard;
    private final MemberCard motherCard;
    private final MemberCard fatherCard;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();
    @FXML
    private AnchorPane CURRENT_MEMBER_PANE;
    @FXML
    private AnchorPane MOTHER_PANE;
    @FXML
    private AnchorPane FATHER_PANE;
    @FXML
    private AnchorPane RELATION_TYPE_PANE;
    @FXML
    private JFXButton CHOOSE_MOTHER_BUTTON;
    @FXML
    private JFXButton CHOOSE_FATHER_BUTTON;
    @FXML
    private JFXButton BUTTON_CONFIRM;
    @FXML
    private JFXButton BUTTON_CANCEL;

    private Stage stage;

    private ChangeListener<Member> memberChangeListener = this::memberChanged;
    private ChangeListener<Member> fatherChangeListener = this::fatherChange;
    private ChangeListener<Member> motherChangeListener = this::motherChanged;
    private ChangeListener<Relation> bornRelationListener = this::bornChanged;

    {
        currentMemberCard = new MemberCard();
        motherCard = new MemberCard();
        fatherCard = new MemberCard();
        currentBornRelation = new SimpleObjectProperty<>();
        existingRelation = new SimpleObjectProperty<>();
        member = new SimpleObjectProperty<>();
        mother = new SimpleObjectProperty<>();
        father = new SimpleObjectProperty<>();
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
    public void openChoosingMother() {

        List<Member> list = generateList(getMember(), Gender.M);
        Member m = sm.showNewDialog(new DialogChooseMemberController(), getMember(), list, FilesFXML.DIALOG_CHOOSE_MEMBER);
        if (m != null) mother.set(m);

    }

    @FXML
    public void openChoosingFather() {

        List<Member> list = generateList(getMember(), Gender.F);
        Member f = sm.showNewDialog(new DialogChooseMemberController(), getMember(), list, FilesFXML.DIALOG_CHOOSE_MEMBER);
        if (f != null) father.set(f);

    }

    @FXML
    public void removeMother(ActionEvent actionEvent) {
        mother.setValue(null);
    }


    @FXML
    public void removeFather(ActionEvent actionEvent) {
        father.setValue(null);
    }


    @FXML
    public void cancel() {
        cleanListeners();
        stage.close();
    }


    @FXML
    public void confirm() {

        if (currentBornRelation.get().getChildren().size() == 1 && existingRelation.get() == null) {
            currentBornRelation.get().setLeft(mother.get());
            currentBornRelation.get().setRight(father.get());
            context.getService().updateRelation(currentBornRelation.get());
        } else {
            context.getService().moveChildFromRelation(member.get(), currentBornRelation.get(),
                    existingRelation.get() == null
                            ? new Relation(mother.get(), father.get(), RelationType.NEUTRAL, true, null)
                            : existingRelation.get());
        }

        cleanListeners();
        stage.close();

    }

    private void initPanes() {
        CURRENT_MEMBER_PANE.getChildren().add(currentMemberCard);
        MOTHER_PANE.getChildren().add(motherCard);
        FATHER_PANE.getChildren().add(fatherCard);
    }

    private void populateParentFields() {
        this.mother.set(currentBornRelation.get().getLeft());
        this.father.set(currentBornRelation.get().getRight());
    }

    /**
     * Function Genereting list of availible sims
     *
     * @param member
     * @param gender
     * @return
     */
    private List<Member> generateList(Member member, Gender gender) {
        List<Member> list = context.getService().getCurrentFamily().getMembers().stream()
                .filter(m -> !m.equals(member))
                .filter(m -> m.getGender() != gender)
                .collect(Collectors.toList());

        /*
            Protection to not set my child as my parent
         */
        list = removeDescends(list, member);
        // list = removeAscends(list, member);

        /*
            Remove siblings
         */
        try {
            Relation born = context.getService().getCurrentFamily().findBornRelation(member);

            for (Member sibling : born.getChildren()) {
                list = list.stream().filter(p -> !p.equals(sibling)).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }


    private List<Member> removeDescends(List<Member> list, Member m) {
        /*
            Find relations that M is father or mother
         */
        List<Relation> relations = context.getService().getCurrentFamily().getRelations()
                .stream()
                .filter(p -> (p.getRight() != null && p.getRight().equals(m))
                        || (p.getLeft() != null && p.getLeft().equals(m))).collect(Collectors.toList());

        for (Relation r : relations) {
            if (r.getChildren() != null && r.getChildren().size() > 0) {
                for (Member child : r.getChildren()) {
                    list = list.stream().filter(q -> !q.equals(child)).collect(Collectors.toList());
                    removeDescends(list, child);
                }
            }
        }
        return list;
    }

    /*
        Listeners
     */

    private void initListeners() {

        member.addListener(memberChangeListener);
        currentBornRelation.addListener(bornRelationListener);
        mother.addListener(motherChangeListener);
        father.addListener(fatherChangeListener);
    }

    private void cleanListeners() {
        member.removeListener(memberChangeListener);
        currentBornRelation.removeListener(bornRelationListener);
        mother.removeListener(motherChangeListener);
        father.removeListener(fatherChangeListener);
    }


    private void initButtonDisableListener() {

        BooleanBinding disableBinding = Bindings.equal(father, currentBornRelation.get().rightProperty())
                .and(Bindings.equal(mother, currentBornRelation.get().leftProperty()));

        this.BUTTON_CONFIRM.disableProperty().bind(disableBinding);
    }

    private void findRelation(Member mother, Member father) {
        existingRelation.setValue(context.getService().findRelation(mother, father));
    }


    /*
        GETTERS
     */

    public ObjectProperty<Member> memberProperty() {
        return member;
    }

    public Member getMember() {
        return member.get();

    }

    /*
        SETTERS
     */
    public void setFather(Member member) {
        this.member.set(member);
        try {
            setCurrentBornRelation(context.getService().getCurrentFamily().findBornRelation(member));
        } catch (NotUniqueBornRelationException e) {
            log.error(LogMessages.MSG_ERROR_BORN, member);
        }

    }

    public void setCurrentBornRelation(Relation currentBornRelation) {
        this.currentBornRelation.set(currentBornRelation);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void memberChanged(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
        if (newValue != null) {
            currentMemberCard.setMember(newValue);
        }
    }

    private void bornChanged(ObservableValue<? extends Relation> observable, Relation oldValue, Relation newValue) {
        if (newValue != null) {
            populateParentFields();
            initButtonDisableListener();
        }
    }

    private void motherChanged(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {

        motherCard.setMember(newValue);
        findRelation(newValue, father.get());
    }

    private void fatherChange(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
        fatherCard.setMember(newValue);
        findRelation(mother.get(), newValue);
    }
}
package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import gentree.client.desktop.configurations.enums.FilesFXML;
import gentree.client.desktop.configurations.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogWithMemberController;
import gentree.client.desktop.controllers.tree_elements.MemberCard;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.Gender;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.desktop.exception.NotUniqueBornRelationException;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.rmi.Remote;
import java.util.List;
import java.util.ResourceBundle;

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
    private AnchorPane currentMember;
    @FXML
    private AnchorPane motherPane;
    @FXML
    private AnchorPane fatherPane;
    @FXML
    private AnchorPane relationTypePane;
    @FXML
    private JFXButton chooseMother;
    @FXML
    private JFXButton chooseFather;
    @FXML
    private JFXButton buttonConfirm;
    @FXML
    private JFXButton buttonCancel;

    private Stage stage;

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

        stage.close();

    }

    private void initPanes() {
        currentMember.getChildren().add(currentMemberCard);
        motherPane.getChildren().add(motherCard);
        fatherPane.getChildren().add(fatherCard);
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
        ObservableList<Member> list = context.getService().getCurrentFamily().getMembers()
                .filtered(m -> !m.equals(member))
                .filtered(m -> m.getGender() != gender);

        /*
            Protection to not set my child as my parent
         */
        list = removeDescends(list, member);
       // list = removeAscends(list, member);

        return list;
    }


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
            if(bornRelation.getLeft() != null) {
                list = list.filtered(p -> !p.equals(bornRelation.getLeft()));
                list = removeAscends(list, bornRelation.getLeft());
            }

            /*
                Remove Rightrs
             */
            if(bornRelation.getRight() != null) {
                list = list.filtered(p -> !p.equals(bornRelation.getRight()));
                list = removeAscends(list, bornRelation.getRight());
            }



        } catch (NotUniqueBornRelationException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }


        return list;

    }

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
        Listeners
     */

    private void initListeners() {
        initMemberListener();
        initBornRelationListener();
        initFatherListener();
        initMotherListener();
    }


    private void initMemberListener() {
        member.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentMemberCard.setMember(newValue);
            }
        });
    }

    private void initBornRelationListener() {
        currentBornRelation.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateParentFields();
                initButtonDisableListener();
            }
        });
    }

    private void initMotherListener() {
        mother.addListener((observable, oldValue, newValue) -> {
            motherCard.setMember(newValue);
            System.out.println(newValue);
            System.out.println(father.get());
            findRelation(newValue, father.get());

        });
    }

    private void initFatherListener() {
        father.addListener((observable, oldValue, newValue) -> {
            fatherCard.setMember(newValue);
            System.out.println(newValue);
            System.out.println(mother.get());
            findRelation(mother.get(), newValue);
        });
    }


    private void initButtonDisableListener() {

        BooleanBinding disableBinding = Bindings.equal(father, currentBornRelation.get().rightProperty())
                .and(Bindings.equal(mother, currentBornRelation.get().leftProperty()));

        this.buttonConfirm.disableProperty().bind(disableBinding);
    }

    private void findRelation(Member mother, Member father) {
        existingRelation.setValue(context.getService().findRelation(mother, father));
        System.out.println("Existing born relation " + existingRelation);
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
    @Override
    public void setMember(Member member) {
        this.member.set(member);
        try {
            setCurrentBornRelation(context.getService().getCurrentFamily().findBornRelation(member));
        } catch (NotUniqueBornRelationException e) {
            log.error("ERROR NOT UNIQUE BORN");
        }

    }

    public void setCurrentBornRelation(Relation currentBornRelation) {
        this.currentBornRelation.set(currentBornRelation);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
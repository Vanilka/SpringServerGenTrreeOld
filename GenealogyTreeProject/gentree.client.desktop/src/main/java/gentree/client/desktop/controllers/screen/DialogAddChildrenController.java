package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogWithRelationController;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.visualization.elements.FamilyMemberCard;
import gentree.client.visualization.elements.MemberCard;
import gentree.client.visualization.elements.RelationTypeCard;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 07/08/2017.
 */

@Log4j2
public class DialogAddChildrenController implements Initializable, FXMLController, FXMLDialogWithRelationController {


    private final MemberCard motherCard;
    private final MemberCard fatherCard;
    private final RelationTypeCard relationType;
    private final ObjectProperty<Relation> relation;
    private final ObservableList<Member> childrenList;

    @FXML
    private AnchorPane motherPane;
    @FXML
    private AnchorPane fatherPane;
    @FXML
    private AnchorPane relationTypePane;
    @FXML
    private FlowPane childrenFlowPane;
    @FXML
    private JFXButton buttonConfirm;
    @FXML
    private JFXButton buttonCancel;

    private Button addChildButton;
    private Stage stage;
    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    {
        addChildButton = new Button();
        childrenList = FXCollections.observableArrayList();
        relation = new SimpleObjectProperty<>();
        relationType = new RelationTypeCard();
        motherCard = new MemberCard();
        fatherCard = new MemberCard();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        initPanes();
        initListeners();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    private void initListeners() {
        initRelationListener();
    }

    private void initRelationListener() {
        relation.addListener((observable, oldValue, newValue) -> {
            childrenList.clear();
            motherCard.setMember(newValue == null ? null : newValue.getLeft());
            fatherCard.setMember(newValue == null ? null : newValue.getRight());
            relationType.setRelation(newValue);
            if (newValue != null) {
                newValue.getChildren().forEach(this::addChildToPane);
            }
        });
    }


    private void initPanes() {
        initAddChildButton();
        childrenFlowPane.getChildren().add(addChildButton);
        motherPane.getChildren().add(motherCard);
        fatherPane.getChildren().add(fatherCard);
        relationTypePane.getChildren().add(relationType);
    }

    private void addChildToPane(Member m) {
        if (m != null) {
            FamilyMemberCard memberCard = new FamilyMemberCard(m);
            childrenFlowPane.getChildren().add(memberCard);
            childrenList.add(m);
        }
    }

    private void initAddChildButton() {
        addChildButton.resize(100, 157);
        addChildButton.setMinSize(100, 157);
        addChildButton.setPrefSize(100, 157);
        addChildButton.setMaxSize(100, 157);
        addChildButton.getStylesheets().addAll(this.getClass().getResource("/layout/style/gentreestyle.css").toExternalForm());
        addChildButton.getStyleClass().add("addElementButton");
        addChildButton.setOnAction(this::openChildChoice);

    }

    private void openChildChoice(ActionEvent event) {

        Member child = sm.showNewDialog(new DialogChooseMemberController(), null, generateList(), FilesFXML.DIALOG_CHOOSE_MEMBER);
        if (child != null) addChildToPane(child);

    }

    private List<Member> generateList() {
        return context.getService().findAllRootMembers()
                .filtered(c -> !c.equals(relation.get().getLeft()))
                .filtered(c -> !c.equals(relation.get().getRight()))
                .filtered(c -> !childrenList.contains(c))
                .filtered(c -> !isAscOf(relation.get().getLeft(), c))
                .filtered(c -> !isAscOf(relation.get().getRight(), c));
    }

    /**
     * Verify if parameter sim is Ascendant of parameter grain
     *
     * @param grain
     * @param sim
     * @return
     */
    private boolean isAscOf(Member grain, Member sim) {
        return sim != null && context.getService().isAscOf(grain, sim);
    }


    @FXML
    public void cancel() {
        stage.close();
    }


    @FXML
    public void confirm() {
        ServiceResponse response = context.getService().moveChildrenToNewRelation(relation.get(), childrenList);

        if (response.getStatus() == ServiceResponse.ResponseStatus.OK) {
            stage.close();
        } else {
            //TODO show error
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setRelation(Relation relation) {
        this.relation.setValue(relation);
    }
}
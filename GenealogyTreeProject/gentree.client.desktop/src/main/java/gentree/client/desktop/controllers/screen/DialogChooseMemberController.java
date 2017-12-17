package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXTextField;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogReturningMember;
import gentree.client.desktop.domain.Member;
import gentree.common.configuration.enums.Age;
import gentree.common.configuration.enums.Gender;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 23/07/2017.
 */

@Log4j2
public class DialogChooseMemberController implements Initializable, FXMLController, FXMLDialogReturningMember {

    private final ObservableList<Member> memberList;
    private final ObjectProperty<Member> member = new SimpleObjectProperty<>();

    @FXML
    private TableView<Member> MEMBER_TABLE;
    @FXML
    private TableColumn<Member, String> MEMBER_PHOTO_COLUMN;
    @FXML
    private TableColumn<Member, String> MEMBER_NAME_COLUMN;
    @FXML
    private TableColumn<Member, String> MEMBER_SURNAME_COLUMN;
    @FXML
    private TableColumn<Member, Age> MEMBER_AGE_COLUMN;
    @FXML
    private TableColumn<Member, Gender> MEMBER_GENDER_COLUMN;
    @FXML
    private ImageView PHOTO_IMV;
    @FXML
    private JFXTextField SIM_NAME_FIELD;
    @FXML
    private JFXTextField SIM_SURNAME_FIELD;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private ObjectProperty<Member> selectedMember;
    private Member result;

    private Stage stage;

    @Setter
    private Gender notAllowedGender;

    private ChangeListener<? super Member> selectionMemberListener = this::selectionChange;
    private ChangeListener<? super Member> memberChangeListener = this::memberChange;


    {
        memberList = FXCollections.observableArrayList();
        selectedMember = new SimpleObjectProperty<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        this.MEMBER_PHOTO_COLUMN.setCellFactory(initPhotoCellFactory());
        this.MEMBER_GENDER_COLUMN.setCellFactory(initGenderCellFactory());
        this.MEMBER_AGE_COLUMN.setCellFactory(initAgeCellFactory());
        setCellValueFactory();
        initSelectedMemberListener();
        MEMBER_TABLE.setItems(memberList);
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);

    }

    @Override
    public void setStage(Stage stage) {

        this.stage = stage;

    }

    @FXML
    public void confirm() {
        result = selectedMember.getValue();
        stage.close();
        cleanListeners();

    }

    @FXML
    public void cancel() {

        stage.close();
        cleanListeners();
    }

    /*
        Listeners
     */

/*    private void initMemberListener() {
        member.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                memberList.clear();
            } else {
                memberList.clear();
                List<Member> list = context.getService().getCurrentFamily().getMembers()
                        .filtered(m -> !m.equals(member.get()))
                        .filtered(m -> m.getGender() != notAllowedGender);
                memberList.addAll(list);
            }
        });
    }*/

    private void initSelectedMemberListener() {
        MEMBER_TABLE.getSelectionModel().selectedItemProperty().addListener(selectionMemberListener);
        selectedMember.addListener(memberChangeListener);
    }

    private void cleanListeners() {
        MEMBER_TABLE.getSelectionModel().selectedItemProperty().removeListener(selectionMemberListener);
        selectedMember.removeListener(memberChangeListener);
    }

    private void selectionChange(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
        if (newValue != null) {
            selectedMember.setValue(newValue);
        }
    }

    private void memberChange(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
        if (newValue != null) {
            PHOTO_IMV.setImage(new Image(newValue.getPhoto()));
            SIM_NAME_FIELD.setText(newValue.getName());
            SIM_SURNAME_FIELD.setText(newValue.getSurname());
        }
    }


    private void setCellValueFactory() {
        this.MEMBER_PHOTO_COLUMN.setCellValueFactory(sm.getPhotoValueFactory());
        this.MEMBER_NAME_COLUMN.setCellValueFactory(data -> data.getValue().nameProperty());
        this.MEMBER_SURNAME_COLUMN.setCellValueFactory(data -> data.getValue().surnameProperty());
        this.MEMBER_AGE_COLUMN.setCellValueFactory(data -> data.getValue().ageProperty());
        this.MEMBER_GENDER_COLUMN.setCellValueFactory(data -> data.getValue().genderProperty());
    }


    private Callback<TableColumn<Member, String>, TableCell<Member, String>> initPhotoCellFactory() {
        Callback<TableColumn<Member, String>,
                TableCell<Member, String>> callback = new Callback<TableColumn<Member, String>, TableCell<Member, String>>() {
            @Override
            public TableCell<Member, String> call(TableColumn<Member, String> param) {
                TableCell<Member, String> cell = new TableCell<Member, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        ImageView imageview = new ImageView();
                        if (item != null) {
                            imageview.setImage(new Image(item));
                            imageview.setFitHeight(80);
                            imageview.setFitWidth(60);
                            setGraphic(imageview);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        };

        return callback;
    }

    private Callback<TableColumn<Member, Gender>, TableCell<Member, Gender>> initGenderCellFactory() {
        Callback<TableColumn<Member, Gender>, TableCell<Member, Gender>> callback = new Callback<TableColumn<Member, Gender>, TableCell<Member, Gender>>() {
            @Override
            public TableCell<Member, Gender> call(TableColumn<Member, Gender> param) {

                TableCell<Member, Gender> cell = new TableCell<Member, Gender>() {
                    @Override
                    protected void updateItem(Gender item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.toString());
                        }
                    }
                };
                return cell;
            }
        };

        return callback;
    }

    private Callback<TableColumn<Member, Age>, TableCell<Member, Age>> initAgeCellFactory() {
        Callback<TableColumn<Member, Age>, TableCell<Member, Age>> callback = new Callback<TableColumn<Member, Age>, TableCell<Member, Age>>() {
            @Override
            public TableCell<Member, Age> call(TableColumn<Member, Age> param) {

                TableCell<Member, Age> cell = new TableCell<Member, Age>() {
                    @Override
                    protected void updateItem(Age item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.toString());
                        }
                    }
                };
                return cell;
            }
        };

        return callback;
    }


    @Override
    public void setMember(Member m) {
        member.set(m);
    }

    @Override
    public Member getResult() {
        return result;
    }

    @Override
    public void setMemberList(List<Member> list) {
        memberList.addAll(list);
    }

}
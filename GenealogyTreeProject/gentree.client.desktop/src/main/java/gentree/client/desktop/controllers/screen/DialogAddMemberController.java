package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogController;
import gentree.client.desktop.controllers.FXMLPane;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.visualization.elements.configuration.ImageFiles;
import gentree.common.configuration.enums.Age;
import gentree.common.configuration.enums.DeathCauses;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.Race;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 03/07/2017.
 */

@Log4j2
public class DialogAddMemberController implements Initializable, FXMLController, FXMLPane, FXMLDialogController {


    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private AnchorPane DEATH_CAUSE_PANE;

    @FXML
    private JFXTextField SIM_NAME_FIELD;

    @FXML
    private JFXTextField SIM_SURNAME_FIELD;

    @FXML
    private JFXTextField SIM_BORNNAME_FIELD;

    @FXML
    private ImageView PHOTO_IMV;

    @FXML
    private JFXRadioButton SIM_SEX_MALE_CHOICE;

    @FXML
    private JFXRadioButton SIM_SEX_FEMALE_CHOICE;

    @FXML
    private ComboBox<Age> AGE_COMBO_BOX;

    @FXML
    private ComboBox<Race> RACE_COMBO_BOX;

    @FXML
    private ComboBox<DeathCauses> DEATH_CAUSE_COMBO_BOX;

    @FXML
    private JFXToggleButton ALIVE_TOGGLE_BUTTON;

    @FXML
    private JFXButton BUTTON_CANCEL;

    @FXML
    private JFXButton CONFIRM_BUTTON;

    private Stage stage;
    private ToggleGroup toggleGroupSex;
    private String path;

    private ChangeListener<Boolean> toogleSelectedListener = this::selectedChanged;
    private ChangeListener<Toggle> sexChangeListener = this::sexChanged;
    private ChangeListener<ResourceBundle> languageListener = this::languageChange;

    {
        toggleGroupSex = new ToggleGroup();
        path = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        PHOTO_IMV.setImage(new Image(ImageFiles.GENERIC_MALE.toString()));
        initListeners();
        createSexToogleGroupe();
        populateAgeComboBox();
        populateRaceComboBox();
        populateDeathCauseComboBox();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }


    public void cancel() {
        cleanListeners();
        this.stage.close();
    }

    public void confirm() {

        Member member = new Member(this.SIM_NAME_FIELD.getText(), this.SIM_SURNAME_FIELD.getText(), this.SIM_BORNNAME_FIELD.getText(), path,
                AGE_COMBO_BOX.getSelectionModel().getSelectedItem(),
                (Gender) toggleGroupSex.getSelectedToggle().getUserData(),
                RACE_COMBO_BOX.getSelectionModel().getSelectedItem(),
                ALIVE_TOGGLE_BUTTON.isSelected(),
                ALIVE_TOGGLE_BUTTON.isSelected() ? null : DEATH_CAUSE_COMBO_BOX.getSelectionModel().getSelectedItem());

        ServiceResponse response = context.getService().addMember(member);
        System.out.println(response);

        if (response.getStatus().equals(ServiceResponse.ResponseStatus.OK)) {
            cleanListeners();
            this.stage.close();
        }
    }


    public void choosePhoto(MouseEvent event) {
        if (event.getClickCount() == 2) {
            path = sm.setImageIntoImageView(PHOTO_IMV);
        }
    }


    private void createSexToogleGroupe() {
        this.SIM_SEX_MALE_CHOICE.setToggleGroup(this.toggleGroupSex);
        this.SIM_SEX_MALE_CHOICE.setUserData(Gender.M);
        this.SIM_SEX_FEMALE_CHOICE.setToggleGroup(this.toggleGroupSex);
        this.SIM_SEX_FEMALE_CHOICE.setUserData(Gender.F);
        this.toggleGroupSex.selectToggle(SIM_SEX_MALE_CHOICE);
    }

    private void populateAgeComboBox() {
        AGE_COMBO_BOX.getItems().addAll(Age.values());
        AGE_COMBO_BOX.getSelectionModel().select(Age.YOUNG_ADULT);
    }

    private void populateRaceComboBox() {
        RACE_COMBO_BOX.getItems().addAll(Race.values());
        RACE_COMBO_BOX.getSelectionModel().select(Race.HUMAIN);
    }

    private void populateDeathCauseComboBox() {
        DEATH_CAUSE_COMBO_BOX.getItems().addAll(DeathCauses.values());
        DEATH_CAUSE_COMBO_BOX.getSelectionModel().select(DeathCauses.NATURAL);
    }


    /*
        INIT LISTENERS
     */

    private void initListeners() {
        this.languageBundle.addListener(this::languageChange);
        this.ALIVE_TOGGLE_BUTTON.selectedProperty().addListener(toogleSelectedListener);
        this.ALIVE_TOGGLE_BUTTON.setSelected(true);
        this.toggleGroupSex.selectedToggleProperty().addListener(sexChangeListener);
    }

    private void cleanListeners() {
        ALIVE_TOGGLE_BUTTON.selectedProperty().removeListener(toogleSelectedListener);
        toggleGroupSex.selectedToggleProperty().removeListener(sexChangeListener);
        languageBundle.removeListener(languageListener);
    }

    /*
     *  LISTEN LANGUAGE CHANGES
     */


    private void selectedChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            ALIVE_TOGGLE_BUTTON.setText("Alive");
            DEATH_CAUSE_PANE.setVisible(false);

        } else {
            ALIVE_TOGGLE_BUTTON.setText("Mort");
            DEATH_CAUSE_PANE.setVisible(true);
        }
    }

    private void sexChanged(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
        if (path == null || path.trim().equals("")) {
            if (newValue.getUserData().equals(Gender.M)) {
                PHOTO_IMV.setImage(new Image(ImageFiles.GENERIC_MALE.toString()));
            } else {
                PHOTO_IMV.setImage(new Image(ImageFiles.GENERIC_FEMALE.toString()));
            }
        }
    }

    private void languageChange(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        reloadElements();
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        // Nothing to do
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

}

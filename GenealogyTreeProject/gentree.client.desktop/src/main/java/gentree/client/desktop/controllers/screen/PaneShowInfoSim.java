package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import gentree.client.desktop.configuration.messages.Keys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.domain.Member;
import gentree.client.visualization.controls.HeaderPane;
import gentree.common.configuration.enums.Age;
import gentree.common.configuration.enums.DeathCauses;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.Race;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 10/07/2017.
 */

@Log4j2
public class PaneShowInfoSim extends Pane implements Initializable, FXMLController, FXMLAnchorPane {

    private final ObjectProperty<Member> member;
    private final BooleanProperty modifiable;

    @FXML
    private final ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    @Getter
    @Setter
    private AnchorPane paneShowInfoSim;
    @FXML
    private AnchorPane DEATH_CAUSES_PANE;
    @FXML
    private AnchorPane CONTENT_PANE;
    @FXML
    private AnchorPane AVATAR_PANE;

    @FXML
    private JFXButton RETURN_BUTTON;
    @FXML
    private JFXButton MODIFY_BUTTON;
    @FXML
    private JFXTextField SIM_ID_FIELD;
    @FXML
    private JFXTextField SIM_NAME_FIELD;
    @FXML
    private JFXTextField SIM_SURNAME_FIELD;
    @FXML
    private JFXTextField SIM_BORNNAME_FIELD;
    @FXML
    private ComboBox<Race> SIM_RACE_CB;
    @FXML
    private ComboBox<Age> SIM_AGE_CB;
    @FXML
    private ComboBox<Gender> SIM_SEX_CB;
    @FXML
    private JFXToggleButton TOGGLE_IS_ALIVE;
    @FXML
    private ComboBox<DeathCauses> SIM_DEATH_CAUSE;
    @FXML
    private HeaderPane HEADER_PANE;
    @FXML
    private Circle SIM_PHOTO;

    private String path;

    private List<? extends Control> readOnlyControls;
    private ChangeListener<? super ResourceBundle> languageListener = this::languageChange;
    private ChangeListener<? super Member> memberListener = this::memberChange;
    private ChangeListener<? super Boolean> modifiableListener = this::modifiableChange;

    {
        member = new SimpleObjectProperty<>();
        modifiable = new SimpleBooleanProperty(false);
        path = null;
    }

    @FXML
    private void returnAction() {
        cleanListeners();
        sm.getScreenMainController().removeInfoPanel(paneShowInfoSim);
    }

    @FXML
    private void modifyAction(ActionEvent actionEvent) {
        if (modifiable.get()) {
            getMember().setName(SIM_NAME_FIELD.getText());
            getMember().setSurname(SIM_SURNAME_FIELD.getText());
            getMember().setBornname(SIM_BORNNAME_FIELD.getText());
            getMember().setPhoto(path);
            getMember().setRace(SIM_RACE_CB.getSelectionModel().getSelectedItem());
            getMember().setAlive(TOGGLE_IS_ALIVE.isSelected());
            getMember().setAge(SIM_AGE_CB.getSelectionModel().getSelectedItem());
            getMember().setGender(SIM_SEX_CB.getSelectionModel().getSelectedItem());
            if (!TOGGLE_IS_ALIVE.isSelected())
                getMember().setDeathCause(SIM_DEATH_CAUSE.getSelectionModel().getSelectedItem());
            context.getService().updateMember(getMember());
        }
        modifiable.set(!modifiable.get());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        this.languageBundle.bind(context.getBundle());
        addLanguageListener();

        populateComboBoxes();
        SIM_ID_FIELD.setEditable(false);
        readOnlyControls = Arrays.asList(
                SIM_NAME_FIELD,
                SIM_SURNAME_FIELD,
                SIM_BORNNAME_FIELD,
                SIM_RACE_CB,
                TOGGLE_IS_ALIVE,
                SIM_DEATH_CAUSE,
                SIM_AGE_CB,
                SIM_SEX_CB);
        setControlsModifiable(false);
        initListeners();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }


    private void populateComboBoxes() {
        SIM_RACE_CB.setCellFactory(sm.getRaceListCell());
        SIM_RACE_CB.setButtonCell(sm.getRaceListCell().call(null));
        SIM_RACE_CB.getItems().addAll(Race.values());
        SIM_DEATH_CAUSE.getItems().addAll(DeathCauses.values());
        SIM_DEATH_CAUSE.getSelectionModel().select(DeathCauses.NATURAL);
        SIM_AGE_CB.setCellFactory(sm.getAgeListCell());
        SIM_AGE_CB.setButtonCell(sm.getAgeListCell().call(null));
        SIM_AGE_CB.getItems().addAll(Age.values());
        SIM_SEX_CB.setCellFactory(sm.getGenderListCell());
        SIM_SEX_CB.setButtonCell(sm.getGenderListCell().call(null));
        SIM_SEX_CB.getItems().addAll(Gender.values());
    }

    private void setControlsModifiable(boolean value) {
        readOnlyControls.forEach(control -> {
            if (control instanceof JFXTextField) ((JFXTextField) control).setEditable(value);
            if (control instanceof JFXToggleButton) ((JFXToggleButton) control).setDisable(!value);
            if (control instanceof ComboBox) ((ComboBox) control).setDisable(!value);
        });
    }

    private void populateControls(Member member) {
        SIM_ID_FIELD.setText(member.getId() > 0 ? Long.toString(member.getId()) : "0");
        SIM_NAME_FIELD.setText(member.getName());
        SIM_SURNAME_FIELD.setText(member.getSurname());
        SIM_BORNNAME_FIELD.setText(member.getBornname());
        SIM_RACE_CB.getSelectionModel().select(member.getRace());
        path = member.getPhoto();
        SIM_PHOTO.setFill(new ImagePattern(new Image(path)));
        TOGGLE_IS_ALIVE.setSelected(member.isAlive());
        SIM_AGE_CB.getSelectionModel().select(member.getAge());
        SIM_SEX_CB.getSelectionModel().select(member.getGender());
        if (!member.isAlive()) SIM_DEATH_CAUSE.getSelectionModel().select(member.getDeathCause());
    }


    public void choosePhoto(MouseEvent event) {
        if (event.getClickCount() == 2 && modifiable.get()) {
            path = sm.setImageIntoShape(SIM_PHOTO);
        }
    }

    /*
        Listeners
     */

    private void initListeners() {
        member.addListener(memberListener);
        modifiable.addListener(modifiableListener);
        TOGGLE_IS_ALIVE.textProperty().bind(Bindings.when(TOGGLE_IS_ALIVE.selectedProperty()).then("ALIVE")
                .otherwise("DEAD"));
        DEATH_CAUSES_PANE.visibleProperty().bind(TOGGLE_IS_ALIVE.selectedProperty().not());
    }

    private void cleanListeners() {
        member.removeListener(memberListener);
        modifiable.removeListener(modifiableListener);
        languageBundle.removeListener(languageListener);
        TOGGLE_IS_ALIVE.textProperty().unbind();
        DEATH_CAUSES_PANE.visibleProperty().unbind();
    }

    private void languageChange(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        reloadElements();
    }


    private void memberChange(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
        if (newValue != null) {
            populateControls(newValue);
        }
        if (oldValue != null) {
        }
    }

    private void modifiableChange(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        setControlsModifiable(newValue);
        MODIFY_BUTTON.setText(getValueFromKey(newValue ? Keys.CONFIRM : Keys.MODIFY));
    }



    /*
     *   LISTEN LANGUAGE CHANGES
     */

    private void addLanguageListener() {
        this.languageBundle.addListener(languageListener);
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        SIM_NAME_FIELD.setPromptText(getValueFromKey(Keys.SIM_NAME));
        SIM_SURNAME_FIELD.setPromptText(getValueFromKey(Keys.SIM_SURNAME));
        SIM_BORNNAME_FIELD.setPromptText(getValueFromKey(Keys.SIM_BORN_NAME));
        RETURN_BUTTON.setText(getValueFromKey(Keys.RETURN));
        MODIFY_BUTTON.setText(getValueFromKey(modifiable.get() ? Keys.CONFIRM : Keys.MODIFY));
        HEADER_PANE.setTitle(getValueFromKey(Keys.HEADER_INFO_SIM));
    }


    /*
        GETTER
     */

    public Member getMember() {
        return member.get();
    }

    public void setMember(Member member) {
        this.member.set(member);
    }

    /*
        SETTER
     */

    public ObjectProperty<Member> memberProperty() {
        return member;
    }

}
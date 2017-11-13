package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gentree.client.desktop.configuration.messages.Keys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.domain.Member;
import gentree.client.visualization.controls.HeaderPane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    private AnchorPane CONTENT_PANE;

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
    private HeaderPane HEADER_PANE;

    @FXML
    private ImageView photo;

    private String path;

    private List<? extends Control> readOnlyControls;

    {

        member = new SimpleObjectProperty<>();
        modifiable = new SimpleBooleanProperty(false);
        path = null;

    }

    @FXML
    private void returnAction() {
        sm.getScreenMainController().removeInfoPanel(paneShowInfoSim);
    }

    @FXML
    private void modifyAction(ActionEvent actionEvent) {
        if (modifiable.get()) {
            getMember().setName(SIM_NAME_FIELD.getText());
            getMember().setSurname(SIM_SURNAME_FIELD.getText());
            getMember().setBornname(SIM_BORNNAME_FIELD.getText());
            getMember().setPhoto(path);
            context.getService().updateMember(getMember());
        }
        modifiable.set(!modifiable.get());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        this.languageBundle.bind(context.getBundle());


/*
        HeaderPane hp = new HeaderPane("Ala ma kota");
        hp.setMinWidth(200);

        paneShowInfoSim.getChildren().add(0, hp);
*/


        addLanguageListener();
        SIM_ID_FIELD.setEditable(false);
        readOnlyControls = Arrays.asList(SIM_NAME_FIELD, SIM_SURNAME_FIELD, SIM_BORNNAME_FIELD);
        setControlsModifiable(false);
        initListeners();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);

    }

    private void setControlsModifiable(boolean value) {
        readOnlyControls.forEach(control -> {
            if (control instanceof JFXTextField) {
                ((JFXTextField) control).setEditable(value);
            }
        });
    }

    private void populateControls(Member member) {
        SIM_ID_FIELD.setText(member.getId() > 0 ? Long.toString(member.getId()) : "0");
        SIM_NAME_FIELD.setText(member.getName());
        SIM_SURNAME_FIELD.setText(member.getSurname());
        SIM_BORNNAME_FIELD.setText(member.getBornname());
        this.photo.setImage(new Image(member.getPhoto()));
    }


    public void choosePhoto(MouseEvent event) {
        if (event.getClickCount() == 2 && modifiable.get()) {
            path = sm.chooseSimPhoto(photo);
        }
    }

    /*
        Listeners
     */

    private void initListeners() {
        member.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateControls(newValue);
            }
        });

        modifiable.addListener((observable, oldValue, newValue) -> {
            setControlsModifiable(newValue);
            MODIFY_BUTTON.setText(getValueFromKey(newValue ? Keys.CONFIRM : Keys.MODIFY));
        });
    }



     /*
     *   LISTEN LANGUAGE CHANGES
     */

    private void addLanguageListener() {
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
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
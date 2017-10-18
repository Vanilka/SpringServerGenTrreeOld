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
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
public class PaneShowInfoSim implements Initializable, FXMLController, FXMLAnchorPane {

    private final ObjectProperty<Member> member;
    private final BooleanProperty modifiable;

    @FXML
    private final ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    @Getter
    @Setter
    private AnchorPane paneShowInfoSim;

    @FXML
    private AnchorPane contentPane;
    @FXML
    private JFXButton returnButton;
    @FXML
    private JFXButton modifyButton;
    @FXML
    private JFXTextField simId;
    @FXML
    private JFXTextField simName;
    @FXML
    private JFXTextField simSurname;
    @FXML
    private JFXTextField simBornname;

    @FXML
    private HeaderPane HEADER_PANE;
    @FXML
    private ImageView photo;
    private List<? extends Control> readOnlyControls;

    {

        member = new SimpleObjectProperty<>();
        modifiable = new SimpleBooleanProperty(false);

    }

    @FXML
    private void returnAction() {
        sm.getScreenMainController().removeInfoPanel(paneShowInfoSim);
    }

    @FXML
    private void modifyAction(ActionEvent actionEvent) {
        if (modifiable.get()) {
            getMember().setName(simName.getText());
            getMember().setSurname(simSurname.getText());
            getMember().setBornname(simBornname.getText());

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
        simId.setDisable(false);
        readOnlyControls = Arrays.asList(simName, simSurname, simBornname);
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
        simId.setText(member.getId() > 0 ? Long.toString(member.getId()) : "0");
        simName.setText(member.getName());
        simSurname.setText(member.getSurname());
        simBornname.setText(member.getBornname());
        this.photo.setImage(new Image(member.getPhoto()));
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
            modifyButton.setText(getValueFromKey(newValue ? Keys.CONFIRM : Keys.MODIFY));
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
        simName.setPromptText(getValueFromKey(Keys.SIM_NAME));
        simSurname.setPromptText(getValueFromKey(Keys.SIM_SURNAME));
        simBornname.setPromptText(getValueFromKey(Keys.SIM_BORN_NAME));
        returnButton.setText(getValueFromKey(Keys.RETURN));
        modifyButton.setText(getValueFromKey(modifiable.get() ? Keys.CONFIRM : Keys.MODIFY));
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
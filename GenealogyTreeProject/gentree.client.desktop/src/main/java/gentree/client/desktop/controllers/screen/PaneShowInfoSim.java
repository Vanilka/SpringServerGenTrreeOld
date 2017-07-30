package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.domain.Member;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    @FXML
    @Getter
    @Setter
    private AnchorPane paneShowInfoSim;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private JFXButton returnButton;

    @FXML
    private JFXTextField simId;

    @FXML
    private JFXTextField simName;

    @FXML
    private JFXTextField simSurname;

    @FXML
    private JFXTextField simBornname;

    @FXML
    private ImageView photo;


    private ObjectProperty<Member> member;

    private List<? extends Control> readOnlyControls;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();



    {
        member = new SimpleObjectProperty<>();

    }

    @FXML
    private void returnAction() {
        sm.getScreenMainController().removeInfoSim(paneShowInfoSim);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.languageBundle.setValue(resources);
        readOnlyControls = Arrays.asList(simId, simName, simSurname, simBornname);
        initControlsProperties();
        initListeners();

    }

    private void initControlsProperties() {

        readOnlyControls.forEach(control -> {
            if(control instanceof JFXTextField) {
                ((JFXTextField) control).setEditable(true);
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
    }





    /*
        GETTER
     */

    public Member getMember() {
        return member.get();
    }

    public ObjectProperty<Member> memberProperty() {
        return member;
    }

    /*
        SETTER
     */

    public void setMember(Member member) {
        this.member.set(member);
    }
}
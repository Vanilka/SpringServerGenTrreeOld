package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import gentree.client.desktop.configuration.common.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogController;
import gentree.client.desktop.controllers.FXMLPane;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.enums.Age;
import gentree.client.desktop.domain.enums.DeathCauses;
import gentree.client.desktop.domain.enums.Gender;
import gentree.client.desktop.domain.enums.Race;
import gentree.client.desktop.service.responses.ServiceResponse;
import gentree.client.visualization.elements.configuration.ImageFiles;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 03/07/2017.
 */

@Log4j2
public class DialogAddMemberController implements Initializable, FXMLController, FXMLPane, FXMLDialogController {


    private static final String PREFIX_FILE_LOAD = "file://";

    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private AnchorPane paneDeathCause;

    @FXML
    private JFXTextField simName;

    @FXML
    private JFXTextField simSurname;

    @FXML
    private JFXTextField simBornname;

    @FXML
    private ImageView photo;

    @FXML
    private JFXRadioButton simSexMale;

    @FXML
    private JFXRadioButton simSexFemale;

    @FXML
    private ComboBox<Age> comboBoxAge;

    @FXML
    private ComboBox<Race> comboBoxRace;

    @FXML
    private ComboBox<DeathCauses> comboBoxDeathCause;

    @FXML
    private JFXToggleButton toggleAliveButton;

    @FXML
    private JFXButton buttonCancel;

    @FXML
    private JFXButton buttonConfirm;

    private Stage stage;
    private ToggleGroup toggleGroupSex;
    private String path;

    {
        toggleGroupSex = new ToggleGroup();
        path = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        photo.setImage(new Image(ImageFiles.GENERIC_MALE.toString()));
        initListeners();
        createSexToogleGroupe();
        populateAgeComboBox();
        populateRaceComboBox();
        populateDeathCauseComboBox();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }


    public void cancel() {
        this.stage.close();
    }

    public void confirm() {

        Member member = new Member(this.simName.getText(), this.simSurname.getText(), this.simBornname.getText(), path,
                comboBoxAge.getSelectionModel().getSelectedItem(),
                (Gender) toggleGroupSex.getSelectedToggle().getUserData(),
                comboBoxRace.getSelectionModel().getSelectedItem(),
                toggleAliveButton.isSelected(),
                toggleAliveButton.isSelected() ? null : comboBoxDeathCause.getSelectionModel().getSelectedItem());

        ServiceResponse response = context.getService().addMember(member);

        if (response.getStatus().equals(ServiceResponse.ResponseStatus.OK)) {
            this.stage.close();
        }
    }


    public void choosePhoto(MouseEvent event) {
        if (event.getClickCount() == 2) {
            File file = sm.openImageFileChooser();
            if (file != null) {
                try {
                    path = PREFIX_FILE_LOAD.concat(file.getCanonicalPath());
                    this.photo.setImage(new Image(path));
                } catch (Exception e) {
                    log.error(LogMessages.MSG_ERROR_LOAD_IMAGE);
                    e.printStackTrace();
                }
            }
        }
    }


    private void createSexToogleGroupe() {
        this.simSexMale.setToggleGroup(this.toggleGroupSex);
        this.simSexMale.setUserData(Gender.M);
        this.simSexFemale.setToggleGroup(this.toggleGroupSex);
        this.simSexFemale.setUserData(Gender.F);
        this.toggleGroupSex.selectToggle(simSexMale);
    }

    private void populateAgeComboBox() {
        comboBoxAge.getItems().addAll(Age.values());
        comboBoxAge.getSelectionModel().select(Age.YOUNG_ADULT);
    }

    private void populateRaceComboBox() {
        comboBoxRace.getItems().addAll(Race.values());
        comboBoxRace.getSelectionModel().select(Race.HUMAIN);
    }

    private void populateDeathCauseComboBox() {
        comboBoxDeathCause.getItems().addAll(DeathCauses.values());
        comboBoxDeathCause.getSelectionModel().select(DeathCauses.NATURAL);
    }


    /*
        INIT LISTENERS
     */

    private void initListeners() {
        initLanguageListener();
        initAliveListener();
        initSexListener();
    }

    private void initAliveListener() {
        toggleAliveButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                toggleAliveButton.setText("Alive");
                paneDeathCause.setVisible(false);

            } else {
                toggleAliveButton.setText("Mort");
                paneDeathCause.setVisible(true);
            }
        });

        toggleAliveButton.setSelected(true);
    }

    private void initSexListener() {
        toggleGroupSex.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (path == null) {
                if (newValue.getUserData().equals(Gender.M)) {
                    photo.setImage(new Image(ImageFiles.GENERIC_MALE.toString()));
                } else {
                    photo.setImage(new Image(ImageFiles.GENERIC_FEMALE.toString()));
                }
            }
        });
    }

    /*
    *  LISTEN LANGUAGE CHANGES
    */
    private void initLanguageListener() {
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
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

package genealogytree.client.desktop.controllers.implementation.scene;


import genealogytree.client.desktop.configuration.ContextGT;
import genealogytree.client.desktop.configuration.ScreenManager;
import genealogytree.client.desktop.configuration.enums.ImageFiles;
import genealogytree.client.desktop.configuration.messages.LogMessages;
import genealogytree.client.desktop.controllers.FXMLTab;
import genealogytree.client.desktop.domain.GTX_Member;
import genealogytree.client.desktop.service.responses.ServiceResponse;
import genealogytree.domain.enums.Age;
import genealogytree.domain.enums.Sex;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 26/12/2016.
 */
@Log4j2
public class TabInfoMemberPaneController implements Initializable, FXMLTab {

    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();
    private static final Logger LOG = LogManager.getLogger(TabInfoMemberPaneController.class);
    @FXML
    private AnchorPane infoMemberTab;

    @FXML
    private JFXTextField simSurnameField;

    @FXML
    private JFXTextField simNameField;

    @FXML
    private JFXTextField simBornnameField;

    @FXML
    private JFXTextField simSex;

    @FXML
    private JFXTextField simAge;

    @FXML
    private ComboBox<Age> comboBoxAge;

    @FXML
    private JFXRadioButton simSexMale;

    @FXML
    private JFXRadioButton simSexFemale;

    @FXML
    private JFXTextField simId;

    @FXML
    private ImageView simPhoto;

    @FXML
    private VBox roVbox;

    @FXML
    private VBox rwVbox;

    @FXML
    private JFXButton buttonCancel;

    @FXML
    private JFXButton buttonModify;


    private BooleanProperty editable;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private Tab tab;
    private JFXTabPane tabPane;
    private ToggleGroup toogleGroupeSex;

    private ObjectProperty<GTX_Member> member;

    private ChangeListener<Object> listener = ((obs, oldValue, newValue) -> populateFields());

    {
        member = new SimpleObjectProperty<>();
        toogleGroupeSex = new ToggleGroup();
        editable = new SimpleBooleanProperty(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);

        this.languageBundle.setValue(rb);

        createSexToogleGroup();
        addMemberChangeListener();
        addLanguageListener();
        roVbox.visibleProperty().bind(rwVbox.visibleProperty().not());
        addEditableListener();
        editable.setValue(false);

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    @FXML
    void buttonCancel(ActionEvent event) {
        this.tabPane.getTabs().remove(tab);
    }

    @FXML
    void buttonModify(ActionEvent event) {
        if (editable.get()) {
            updateMember(member.get());
            editable.set(false);
        } else {
            editable.setValue(true);
        }
    }

    public void addMemberChangeListener() {
        this.member.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.getProperties().forEach(p -> p.removeListener(listener));
            }
            if (newValue != null) {
                newValue.getProperties().forEach(p -> p.addListener(listener));
                populateFields();
            }
        });
    }


    private void createSexToogleGroup() {
        this.simSexMale.setToggleGroup(this.toogleGroupeSex);
        this.simSexMale.setUserData(Sex.MALE);
        this.simSexFemale.setToggleGroup(this.toogleGroupeSex);
        this.simSexFemale.setUserData(Sex.FEMALE);

        this.toogleGroupeSex.selectToggle(simSexMale);
    }

    private void populateAgeComboBox() {
        comboBoxAge.getItems().clear();
        comboBoxAge.getItems().addAll(Age.values());
        comboBoxAge.getSelectionModel().select(member.get().getAge());
    }

    private ServiceResponse updateMember(GTX_Member member) {
        GTX_Member updated = new GTX_Member();
        updated.setId(Long.parseLong(simId.getText()));
        updated.setVersion(member.getVersion());
        updated.setName(simNameField.getText());
        updated.setBornname(simBornnameField.getText());
        updated.setSurname(simSurnameField.getText());
        updated.setSex((Sex) toogleGroupeSex.getSelectedToggle().getUserData());
        updated.setAge(comboBoxAge.getSelectionModel().getSelectedItem());
        return context.getService().updateMember(member, updated);
    }

    /*
    *LISTEN LANGUAGE CHANGES
    */
    private void addLanguageListener() {
        this.languageBundle.addListener(new ChangeListener<ResourceBundle>() {
            @Override
            public void changed(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue,
                                ResourceBundle newValue) {
                reloadElements();
            }
        });
    }

    private void addEditableListener() {
        this.editable.addListener(observable -> {
            if (!editable.get()) {
                this.simId.setEditable(false);
                this.simNameField.setEditable(false);
                this.simSurnameField.setEditable(false);
                this.simBornnameField.setEditable(false);
                this.simSex.setEditable(false);
                this.simAge.setEditable(false);
                this.rwVbox.setVisible(false);

            } else {
                this.simNameField.setEditable(true);
                this.simSurnameField.setEditable(true);
                this.simBornnameField.setEditable(true);
                rwVbox.setVisible(true);
            }
        });
    }


    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        // TODO
    }



    private void populateFields() {
        simNameField.setText(member.get().getName());
        simSurnameField.setText(member.get().getSurname());
        if (member.get().getBornname() != null && !member.get().getBornname().equals("") && !member.get().getBornname().equals(member.get().getSurname())) {
            simBornnameField.setText(member.get().getBornname());
        } else {
            simBornnameField.setText(member.get().getSurname());
        }
        simSex.setText(member.getValue().getSex().toString());
        simAge.setText(member.getValue().getAge().toString());
        simId.setText(Long.toString(member.getValue().getId()));
        populateAgeComboBox();
        toogleGroupeSex.selectToggle(member.get().getSex() == Sex.FEMALE ? simSexFemale : simSexMale);
        Image img = null;
        try {
            String path = member.getValue().getPhoto();
            img = new Image(path);
        } catch (Exception e) {
            img = new Image(ImageFiles.GENERIC_MALE.toString());
        } finally {
            simPhoto.setImage(img);
        }
    }

    /*
    * GETTERS AND SETTERS
    */

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public JFXTabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(JFXTabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        this.tab = tab;
    }

    public GTX_Member getMember() {
        return member.get();
    }

    public void setMember(GTX_Member member) {
        this.member.set(member);
    }

    public ObjectProperty<GTX_Member> memberProperty() {
        return member;
    }

}

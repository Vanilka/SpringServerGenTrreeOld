package com.genealogytree.client.desktop.controllers.implementation;


import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.configuration.enums.ImageFiles;
import com.genealogytree.client.desktop.configuration.messages.LogMessages;
import com.genealogytree.client.desktop.controllers.FXMLTab;
import com.genealogytree.client.desktop.domain.GTX_Member;
import com.genealogytree.client.desktop.service.responses.MemberResponse;
import com.genealogytree.client.desktop.service.responses.ServiceResponse;
import com.genealogytree.domain.enums.Age;
import com.genealogytree.domain.enums.Sex;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 22/11/2016.
 */
@Log4j2
public class TabAddNewMemberPaneController implements Initializable, FXMLTab {

    private static final Logger LOG = LogManager.getLogger(TabAddNewMemberPaneController.class);

    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();

    @FXML
    private AnchorPane addNewMember;

    @FXML
    private JFXTextField simNameField;

    @FXML
    private JFXTextField simSurnameField;

    @FXML
    private JFXTextField simBornnameField;

    @FXML
    private JFXRadioButton simSexMale;

    @FXML
    private JFXRadioButton simSexFemale;

    @FXML
    private ComboBox<Age> comboBoxAge;

    @FXML
    private JFXButton addSimConfirmButton;

    @FXML
    private JFXButton addSimCancelButton;

    @FXML
    private ImageView simPhoto;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private ToggleGroup toogleGroupeSex;

    private Tab tab;
    private JFXTabPane tabPane;

    private String path;

    /**
     * Initializes the controller class.
     */

    {
        toogleGroupeSex = new ToggleGroup();
        path = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(rb);
        addSimConfirmDisableBinding();
        addSexListener();
        createSexToogleGroupe();
        populateAgeComboBox();
        addLanguageListener();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    private void populateAgeComboBox() {
        comboBoxAge.getItems().addAll(Age.values());
        comboBoxAge.getSelectionModel().select(Age.YOUNG_ADULT);
    }

    @FXML
    public void addSimConfirm() {
        GTX_Member member = new GTX_Member(this.simNameField.getText(), this.simSurnameField.getText(), this.simBornnameField.getText(), comboBoxAge.getSelectionModel().getSelectedItem(), (Sex) toogleGroupeSex.getSelectedToggle().getUserData(), path);

        ServiceResponse response = context.getService().addMember(member);
        if (response instanceof MemberResponse) {
            this.tabPane.getTabs().remove(tab);
        } else {

        }
    }

    private void addSimConfirmDisableBinding() {
        BooleanBinding disableBinding = Bindings.createBooleanBinding(() ->
                ((simNameField.getText().isEmpty() || simSurnameField.getText().isEmpty())), simNameField.textProperty(), simSurnameField.textProperty());

        this.addSimConfirmButton.disableProperty().bind(disableBinding);
    }


    private void addSexListener() {
        toogleGroupeSex.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (path == null) {
                if (newValue.getUserData().equals(Sex.MALE)) {
                    simPhoto.setImage(new Image(ImageFiles.GENERIC_MALE.toString()));
                } else {
                    simPhoto.setImage(new Image(ImageFiles.GENERIC_FEMALE.toString()));
                }
            }
        });
    }

    @FXML
    void setImage(MouseEvent event) {
        if (event.getClickCount() == 2) {
            File file = sc.openImageFileChooser();
            if (file != null) {
                try {
                    path = file.getAbsolutePath();
                    BufferedImage bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    this.simPhoto.setImage(image);
                } catch (Exception e) {
                    LOG.error("Error image");
                }
            }
        }
    }


    public void setAddSimCancel() {
        this.tabPane.getTabs().remove(tab);
    }


    private void createSexToogleGroupe() {
        this.simSexMale.setToggleGroup(this.toogleGroupeSex);
        this.simSexMale.setUserData(Sex.MALE);
        this.simSexFemale.setToggleGroup(this.toogleGroupeSex);
        this.simSexFemale.setUserData(Sex.FEMALE);

        this.toogleGroupeSex.selectToggle(simSexMale);
    }

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


    /*
     * LISTEN LANGUAGE CHANGES
     */
    private void addLanguageListener() {
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {

        this.simNameField.setPromptText(getValueFromKey("simName"));
        this.simSurnameField.setPromptText(getValueFromKey("simSurname"));

        this.addSimConfirmButton.setText(getValueFromKey("button_confirm"));
        this.addSimCancelButton.setText(getValueFromKey("addSimCancel"));

        this.simSexFemale.setText(getValueFromKey("simSexF"));
        this.simSexMale.setText(getValueFromKey("simSexM"));
    }

    /*
     * GETTERS AND SETTERS
     */

}

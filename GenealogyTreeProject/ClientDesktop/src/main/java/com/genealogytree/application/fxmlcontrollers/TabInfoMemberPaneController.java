package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLTabController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.configuration.ImageFiles;
import com.genealogytree.domain.GTX_Member;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 26/12/2016.
 */
public class TabInfoMemberPaneController implements Initializable, FXMLTabController {

    private static final Logger LOG = LogManager.getLogger(TabInfoMemberPaneController.class);

    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private AnchorPane infoMemberTab;

    @FXML
    private JFXTextField simSurnameField;

    @FXML
    private JFXTextField simNameField;

    @FXML
    private JFXTextField simSex;

    @FXML
    private JFXTextField simAge;

    @FXML
    private ImageView simPhoto;

    @FXML
    private JFXButton buttonCancel;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private Tab tab;
    private JFXTabPane tabPane;

    private ObjectProperty<GTX_Member> member;

    {
        member = new SimpleObjectProperty<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());
        this.languageBundle.setValue(rb);

        this.simNameField.setEditable(false);
        this.simSurnameField.setEditable(false);
        this.simSex.setEditable(false);
        this.simAge.setEditable(false);

    }

    @FXML
    void buttonCancel(ActionEvent event) {
        this.tabPane.getTabs().remove(tab);
    }

    public void addMemberChangeListener() {
        this.member.addListener(new ChangeListener<GTX_Member>() {
            @Override
            public void changed(ObservableValue<? extends GTX_Member> observable, GTX_Member oldValue, GTX_Member newValue) {
                if (newValue != null) {
                    simNameField.setText(member.getValue().getName());
                    simSurnameField.setText(member.getValue().getSurname());
                    simSex.setText(member.getValue().getSex().toString());
                    simAge.setText(member.getValue().getAge().toString());
                    Image img = null;
                    try {
                        String path = member.getValue().getPhoto();
                        if (path != ImageFiles.GENERIC_FEMALE.toString() && path != ImageFiles.GENERIC_MALE.toString()) {
                            img = new Image("file:///" + path);
                        } else {
                            img = new Image(path);
                        }

                    } catch (Exception e) {
                        img = new Image(ImageFiles.GENERIC_MALE.toString());
                    } finally {
                        simPhoto.setImage(img);

                    }

                }
            }
        });
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


    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        // TODO
    }

    /*
    * GETTERS AND SETTERS
    */
    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
        addMemberChangeListener();
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
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

    public GTX_Member getMember() {
        return member.get();
    }

    public ObjectProperty<GTX_Member> memberProperty() {
        return member;
    }

    public void setMember(GTX_Member member) {
        this.member.set(member);
    }
}

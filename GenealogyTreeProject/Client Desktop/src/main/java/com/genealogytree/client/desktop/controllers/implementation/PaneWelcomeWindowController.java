/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.client.desktop.controllers.implementation;

import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.configuration.messages.LogMessages;
import com.genealogytree.client.desktop.controllers.FXMLPane;
import com.genealogytree.client.desktop.domain.GTX_Family;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */

@Log4j2
public class PaneWelcomeWindowController implements Initializable, FXMLPane {

    private static final Logger LOG = LogManager.getLogger(PaneWelcomeWindowController.class);
    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();

    @FXML
    private AnchorPane rootWelcomeWindow;

    @FXML
    private VBox vbox_newProject;
    @FXML
    private TableView<GTX_Family> projectsList;

    @FXML
    private TableColumn<GTX_Family, String> projectsName;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private Button refreshProjectList;

    @FXML
    private Button createNewProject;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(rb);
        setFamilyCellFactory();
        addTopOffsetListener(projectsList);
        addTopOffsetListener(vbox_newProject);
        addLeftOffsetListener(projectsList);
        addLeftOffsetListener();

        projectsList.resize(540, 500);
        vbox_newProject.resize(200, 500);
        // TODO

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }


    private void populateTable() {

    }


    public void chooseProject(MouseEvent event) {

    }

    public void newProject() {


    }


    public void refreshProjectList() {
        populateTable();
    }

    private void setFamilyCellFactory() {
        this.projectsName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }


    /*
     * LISTEN LANGUAGE CHANGES
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
        this.projectsName.setText(getValueFromKey("your_projects"));
    }

    /*
    *  LISTEN POSITION
    */
    private void addTopOffsetListener(TableView tableView) {
        this.rootWelcomeWindow.heightProperty()
                .addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    double y = (newValue.doubleValue() - tableView.getHeight()) / 2;
                    tableView.setLayoutY(y);
                });
    }

    private void addLeftOffsetListener(TableView tableView) {
        this.rootWelcomeWindow.widthProperty()
                .addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    double x = (newValue.doubleValue() - tableView.getWidth()) / 2;
                    tableView.setLayoutX(x);
                });
    }

    private void addTopOffsetListener(VBox vbox) {
        this.rootWelcomeWindow.heightProperty()
                .addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    double y = (newValue.doubleValue() - vbox.getHeight()) / 2;
                    vbox.setLayoutY(y);
                });
    }

    private void addLeftOffsetListener() {
        this.projectsList.layoutXProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            double x = (newValue.doubleValue() - vbox_newProject.getWidth());
            vbox_newProject.setLayoutX(x);
        });
    }

    /*
     * GETTERS AND SETTERS
     */

}

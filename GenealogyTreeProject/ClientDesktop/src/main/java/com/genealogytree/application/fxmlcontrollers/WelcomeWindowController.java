/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.configuration.FXMLFiles;
import com.genealogytree.domain.beans.FamilyBean;
import com.genealogytree.services.responses.ListFamilyResponse;
import com.genealogytree.services.responses.ServerResponse;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
public class WelcomeWindowController implements Initializable, FXMLController {

    private static final Logger LOG = LogManager.getLogger(WelcomeWindowController.class);
    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private AnchorPane rootWelcomeWindow;

    @FXML
    private VBox vbox_newProject;
    @FXML
    private TableView<FamilyBean> projectsList;

    @FXML
    private TableColumn<FamilyBean, String> projectsName;

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
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());
        this.languageBundle.setValue(rb);

        setFamilyCellFactory();
        addTopOffsetListener(projectsList);
        addTopOffsetListener(vbox_newProject);
        addLeftOffsetListener(projectsList);
        addLeftOffsetListener();

        projectsList.resize(540, 500);
        vbox_newProject.resize(200, 500);
        // TODO
    }


    private void populateTable() {

        ServerResponse response = this.context.getFamilyService().getProjects();
        if (response instanceof ListFamilyResponse) {
            ObservableList<FamilyBean> list = FXCollections.observableArrayList();
            list.addAll(((ListFamilyResponse) response).getListFamily());
            projectsList.getItems().clear();
            projectsList.setItems(list);
        }

    }


    public void chooseProject(MouseEvent event) {
        if (event.getClickCount() == 2 && projectsList.getSelectionModel().getSelectedItem() != null) {
            System.out.println("THis is your project : " + projectsList.getSelectionModel().getSelectedItem().getName());
        }
    }

    public void newProject() {
        this.manager.showNewDialog(new NewProjectDialog(),FXMLFiles.NEW_PROJECT_DIALOG.toString());

        System.out.println("udalo sie");

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
        // Nothing to do
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
    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
        populateTable();
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.GenealogyTree;
import com.genealogytree.application.FXMLController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.configuration.traduction.Languages;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
public class MenuBarController implements Initializable, FXMLController {

    private static final Logger LOG = LogManager.getLogger(MenuBarController.class);

    private GenealogyTree application;
    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private MenuBar mainBar;

    @FXML
    private Menu menuFile;

    @FXML
    private Menu menuProject;

    @FXML
    private MenuItem menuItemConnectToServer;

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private MenuItem menuItemNewProject;

    @FXML
    private MenuItem menuItemOpenProject;

    @FXML
    private MenuItem menuItemSaveProjectAs;

    @FXML
    private MenuItem menuItemSaveProject;

    @FXML
    private MenuItem menuItemCloseProject;

    @FXML
    private ComboBox<Languages> languageChooser;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());

        this.languageBundle.setValue(rb);

        this.setCellFactoryToComboBox();

        this.languageChooser.setItems(FXCollections.observableArrayList(Languages.values()));
        this.languageChooser.getSelectionModel()
                .select(Languages.valueOf(languageBundle.getValue().getString("language")));
    }

    @FXML
    public void exitProgram() {
        System.exit(0);
    }

    private void setCellFactoryToComboBox() {
        class LanguageCell extends ListCell<Languages> {
            {
                super.setPrefHeight(40);
                super.setPrefWidth(150);
            }

            @Override
            public void updateItem(Languages item, boolean empty) {
                super.updateItem(item, empty);
                {
                    if (item != null) {
                        ImageView imv = new ImageView(item.getBadge());
                        imv.setFitWidth(30);
                        imv.setFitHeight(30);
                        setGraphic(imv);
                        setText(item.toString().toUpperCase());

                    } else {
                        setText("empty");
                    }
                }
            }
        }

        class LanguageCellFactory implements Callback<ListView<Languages>, ListCell<Languages>> {
            @Override
            public ListCell<Languages> call(ListView<Languages> param) {
                // TODO Auto-generated method stub
                return new LanguageCell();
            }
        }

        this.languageChooser.setCellFactory(new LanguageCellFactory());
        this.languageChooser.setButtonCell(new LanguageCell());
    }

    /*
     * LISTEN LANGUAGE CHANGES
     */
    private void addLanguageListener() {

        this.languageChooser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Languages>() {
            @Override
            public void changed(ObservableValue<? extends Languages> observable, Languages oldValue,
                                Languages newValue) {
                context.setLocale(newValue.toString());
                context.setBundle(context.getLocale());
            }
        });

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
        this.menuFile.setText(getValueFromKey("menu_program"));
        this.menuProject.setText(getValueFromKey("menu_project"));
        this.menuItemClose.setText(getValueFromKey("menu_program_close"));
        this.menuItemCloseProject.setText(getValueFromKey("menu_project_close"));
        // this.menuItemConnectToServer.setText(getValueFromKey("menu_project_close"));
        this.menuItemNewProject.setText(getValueFromKey("menu_project_new"));
        this.menuItemOpenProject.setText(getValueFromKey("menu_project_open"));
        this.menuItemSaveProject.setText(getValueFromKey("menu_project_save"));
        this.menuItemSaveProjectAs.setText(getValueFromKey("menu_project_save_as"));
    }

	/*
     * GETTERS AND SETTERS
	 */

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }

    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
    }

}

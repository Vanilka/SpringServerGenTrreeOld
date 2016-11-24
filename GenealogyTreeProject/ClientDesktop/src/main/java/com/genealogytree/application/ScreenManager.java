/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application;

import com.genealogytree.application.fxmlcontrollers.*;
import com.genealogytree.configuration.BorderPaneReloadHelper;
import com.genealogytree.configuration.FXMLFiles;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author vanilka
 */
public class ScreenManager {

    private static final Logger LOG = LogManager.getLogger(ScreenManager.class);
    private GenealogyTreeContext context;
    private PaneMainWindowController mainWindow;
    private PaneChooseApplicationTypeController chooseApplicationType;
    private PaneFooterController footer;
    private PaneMenuBarController menuBar;
    private BorderPane root;
    private Stage stage;
    private Scene scene;
    private BorderPaneReloadHelper helper;

    {
        helper = new BorderPaneReloadHelper();
    }

    public ScreenManager() {
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public ScreenManager getManager() {
        return this;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void startApplication() {

        setNewMainWindow();
        loadFxml(this.menuBar, this.mainWindow.getRootWindow(), FXMLFiles.MENU_BAR.toString(), Where.TOP);
        loadFxml(this.footer, this.mainWindow.getRootWindow(), FXMLFiles.FOOTER.toString(), Where.BOTTOM);
        loadFxml(this.chooseApplicationType, this.mainWindow.getRootWindow(), FXMLFiles.CHOOSE_APPLICATION_TYPE.toString(), Where.CENTER);

        this.scene = new Scene(root);
        this.stage.setScene(this.scene);
        this.stage.setTitle("Genealogy Tree v2.0");
        this.stage.setHeight(750);
        this.stage.setWidth(1200);
        this.stage.setMinHeight(750);
        this.stage.setMinWidth(1200);
        this.stage.show();
    }

    public PaneMainWindowController getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(PaneMainWindowController mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setNewMainWindow() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FXMLFiles.MAIN_FXML.toString()));
        try {
            this.root = ((BorderPane) loader.load());
            this.mainWindow = loader.getController();
            this.mainWindow.setRootWindow(root);
            this.mainWindow.setManager(this);
            this.mainWindow.setContext(this.context);
        } catch (IOException ex) {
            ex.printStackTrace();
            LOG.error(ex.getClass() + " - " + ex.getMessage());
            LOG.error(ex.getCause());
        }

    }

    public void showNewDialog(FXMLDialogController controller, String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), this.context.getBundle());

        try {
            Stage dialogStage = new Stage();
            AnchorPane dialogwindow = (AnchorPane) loader.load();
            controller = loader.getController();
            controller.setManager(this);
            controller.setContext(this.context);
            controller.setStage(dialogStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.getStage());
            Scene scene = new Scene(dialogwindow);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.showAndWait();


        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error(ex.getClass() + " - " + ex.getMessage());
            LOG.error(ex.getCause());
        }

    }


    public FXMLPaneController loadFxml(FXMLPaneController controller, Pane pane, String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), this.context.getBundle());
        try {
            pane.getChildren().clear();
            pane.getChildren().addAll((Pane) loader.load());
            controller = loader.getController();
            controller.setManager(this);
            controller.setContext(this.context);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error(ex.getClass() + " - " + ex.getMessage());
            LOG.error(ex.getCause());
        }
        return controller;
    }

    public FXMLPaneController loadFxml(FXMLPaneController controller, AnchorPane anchor, String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), this.context.getBundle());
        try {
            anchor.getChildren().clear();
            anchor.getChildren().addAll((AnchorPane) loader.load());
            controller = loader.getController();
            controller.setManager(this);
            controller.setContext(this.context);
        } catch (Exception ex) {
            LOG.error(ex.getClass() + " - " + ex.getMessage());
            LOG.error(ex.getCause());
            ex.printStackTrace();
        }
        return controller;
    }

    public void loadFxml(FXMLPaneController controller, BorderPane border, String fxml, Where where) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), this.context.getBundle());
        try {
            AnchorPane temp = (AnchorPane) loader.load();
            controller = loader.getController();
            switch (where) {
                case TOP:
                    border.setTop(temp);
                    break;
                case CENTER:
                    helper.before(border);
                    border.setCenter(temp);
                    helper.after(border);
                    break;
                case BOTTOM:
                    border.setBottom(temp);
                    break;
            }
            controller.setManager(this);
            controller.setContext(this.context);

        } catch (IOException ex) {
            ex.printStackTrace();
            LOG.error(ex.getClass() + " - " + ex.getMessage());
            LOG.error(ex.getCause());

        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error(ex.getClass() + " - " + ex.getMessage());
            LOG.error(ex.getCause());
        }

    }

    public FXMLTabController loadFxml(FXMLTabController controller, JFXTabPane tabPane, Tab tab, String fxml, String title) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), this.context.getBundle());
        try {
            tab.setContent(loader.load());
            tab.setText(title);
            controller = loader.getController();
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            controller.setManager(this);
            controller.setContext(this.context);
            controller.setTabAndTPane(tabPane, tab);


        } catch (IOException ex) {
            ex.printStackTrace();
            LOG.error(ex.getClass() + " - " + ex.getMessage());
            LOG.error(ex.getCause());

        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error(ex.getClass() + " - " + ex.getMessage());
            LOG.error(ex.getCause());
        }

        return controller;
    }

    public FXMLTabController loadFxml(FXMLTabController controller, JFXTabPane tabPane,Tab tab, String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), this.context.getBundle());
        try {
            tab.setContent(loader.load());
            controller = loader.getController();
            controller.setManager(this);
            controller.setContext(this.context);
            controller.setTabAndTPane(tabPane,tab);

        } catch (IOException ex) {
            ex.printStackTrace();
            LOG.error(ex.getClass() + " - " + ex.getMessage());
            LOG.error(ex.getCause());

        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error(ex.getClass() + " - " + ex.getMessage());
            LOG.error(ex.getCause());
        }

        return controller;
    }


    public void setContext(GenealogyTreeContext context) {
        this.context = context;
    }


    public enum Where {
        TOP,
        CENTER,
        BOTTOM
    }

}

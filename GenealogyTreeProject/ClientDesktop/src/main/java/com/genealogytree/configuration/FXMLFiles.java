/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.configuration;

/**
 * @author vanilka
 */
public enum FXMLFiles {
    MAIN_FXML("MainFXML.fxml"),
    MENU_BAR("MenuBar.fxml"),
    FOOTER("Footer.fxml"),
    CHOOSE_APPLICATION_TYPE("ChooseApplicationType.fxml"),
    LOGON_FORM("LogonForm.fxml"),
    LOCAL_APPLICATION_CHOICE("LocalApplicationChoice.fxml"),
    ONLINE_APPLICATION_CHOICE("OnlineApplicationChoice.fxml"),
    WELCOME_WINDOW("WelcomeWindow.fxml"),
    LOGON_WINDOW("LogonWindow.fxml"),
    REGISTER_FORM("RegisterForm.fxml"),
    REGISTER_CONFIRMATION("RegisterConfirmation.fxml"),
    MAIN_APPLICATION_WINDOW("MainApplicationWindow.fxml"),
    NEW_PROJECT_DIALOG("NewProjectDialog.fxml");

    private final String fxmlPath = "/com/genealogytree/application/fxml/";
    private String file;

    private FXMLFiles(String file) {
        this.file = fxmlPath.concat(file);
    }

    @Override
    public String toString() {
        return file;
    }
}

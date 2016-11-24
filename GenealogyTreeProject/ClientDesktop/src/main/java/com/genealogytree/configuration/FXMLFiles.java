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
    MAIN_FXML("PaneMainFXML.fxml"),
    MENU_BAR("PaneMenuBar.fxml"),
    FOOTER("PaneFooter.fxml"),
    CHOOSE_APPLICATION_TYPE("PaneChooseApplicationType.fxml"),
    LOGON_FORM("PaneLogonForm.fxml"),
    LOCAL_APPLICATION_CHOICE("PaneLocalApplicationChoice.fxml"),
    ONLINE_APPLICATION_CHOICE("PaneOnlineApplicationChoice.fxml"),
    WELCOME_WINDOW("PaneWelcomeWindow.fxml"),
    LOGON_WINDOW("PaneLogonWindow.fxml"),
    REGISTER_FORM("PaneRegisterForm.fxml"),
    REGISTER_CONFIRMATION("PaneRegisterConfirmation.fxml"),
    MAIN_APPLICATION_WINDOW("PaneMainApplicationWindow.fxml"),
    NEW_PROJECT_DIALOG("DialogNewProject.fxml"),
    TAB_ADD_NEW_MEMBER("TabAddNewMember.fxml"),
    TAB_INFO_PROJECT("TabInfoProject.fxml");

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

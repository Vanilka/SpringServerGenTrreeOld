package genealogytree.client.desktop.configuration.enums;

/**
 * Created by Martyna SZYMKOWIAK on 19/03/2017.
 */
public enum FXMLFile {
    MAIN_FXML("main.fxml"),
    FOOTER_FXML("footer.fxml"),
    MENU_FXML("menu.fxml"),
    CHOOSE_MODE_SCREEN_FXML("chose-mode-screen.fxml"),
    LOCAL_APP_MODE("PaneLocalApplicationMode.fxml"),
    ONLINE_APP_MODE("PaneOnlineApplicationMode.fxml"),
    LOGON_WINDOW("PaneLogonWindow.fxml"),
    LOGON_FORM("PaneLogonForm.fxml"),
    WELCOME_WINDOW("PaneWelcomeWindow.fxml"),
    REGISTER_FORM("PaneRegisterForm.fxml"),
    REGISTER_CONFIRMATION("PaneRegisterConfirmation.fxml"),
    MAIN_APPLICATION_WINDOW("PaneMainApplicationWindow.fxml"),
    NEW_PROJECT_DIALOG("DialogNewProject.fxml"),
    GENEALOGY_TREE_DRAW("PaneGenealogyTreeDraw.fxml"),
    TAB_ADD_NEW_MEMBER("TabAddNewMember.fxml"),
    TAB_INFO_PROJECT("TabInfoProject.fxml"),
    TAB_INFO_MEMBER("TabInfoMember.fxml"),
    TAB_INFO_RELATION("TabInfoRelation.fxml"),
    TAB_ADD_NEW_RELATION("TabAddNewRelation.fxml"),
    TAB_RELATION_TABLE("TabShowRelationTable.fxml"),
    TAB_MEMBER_TABLE("TabShowMemberTable.fxml");

    private final String fxmlPath = "/layout/screen/";
    private String file;

    private FXMLFile(String file) {
        this.file = fxmlPath.concat(file);
    }

    @Override
    public String toString() {
        return file;
    }
}

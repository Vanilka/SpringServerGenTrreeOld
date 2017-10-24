package gentree.client.desktop.configuration.enums;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
public enum FilesFXML {

    ROOT_WINDOW_FXML("root.window.fxml"),
    MAIN_MENU_FXML("main.menu.fxml"),
    MAIN_FOOTER_FXML("main.footer.fxml"),
    LOCAL_APP_MODE("button.local.mode.fxml"),
    ONLINE_APP_MODE("button.online.mode.fxml"),
    SCREEN_WELCOME_FXML("screen.welcome.fxml"),
    OPEN_PROJECT_DIALOG("dialog.open.project.fxml"),
    ADD_MEMBER_DIALOG("dialog.add.member.fxml"),
    SCREEN_MAIN_FXML("screen.main.fxml"),
    TAB_OPEN_NEW_PROJECT_FXML("tab.open.new.project.fxml"),
    TAB_OPEN_EXISTING_PROJECT_FXML("tab.open.existing.project.fxml"),
    SCREEN_MAIN_RIGHT_FXML("screen.main.right.fxml"),
    SCREEN_MAIN_LEFT_FXML("screen.main.left.fxml"),
    SCREEN_MAIN_LEFT_FAMILY_INFO_FXML("tab.family.info.fxml"),
    SCREEN_MAIN_LEFT_FAMILY_VIEW_FXML("tab.family.view.fxml"),
    SCREEN_LOGON_REGISTER_FXML("screen.logon.register.fxml"),
    SCREEN_OPEN_ONLINE_PROJECT_FXML("screen.open.online.project.fxml"),
    PANE_SHOW_INFO_MEMBER_FXML("pane.show.info.member.fxml"),
    PANE_SHOW_INFO_RELATION_FXML("pane.show.info.relation.fxml"),
    PANE_LOGON_FXML("pane.logon.fxml"),
    DIALOG_APP_PROPERTIES("dialog.app.properties.fxml"),
    DIALOG_APP_PROPERTIES_TREE("dialog.app.properties.tree.fxml"),
    DIALOG_APP_PROPERTIES_OTHER("dialog.app.properties.other.fxml"),
    DIALOG_APP_PROPERTIES_ONLINE("dialog.app.properties.online.fxml"),
    DIALOG_ADD_PARENTS_TO_MEMBER("dialog.add.parents.to.member.fxml"),
    DIALOG_ADD_SPOUSE_TO_MEMBER("dialog.add.spouse.fxml"),
    DIALOG_CHOOSE_MEMBER("dialog.choose.member.fxml"),
    DIALOG_ADD_CHILDREN("dialog.add.children.fxml"),
    DIALOG_ADD_REALM("dialog.add.realm.fxml");


    private final String fxmlPath = "/layout/screen/";
    private String file;

    private FilesFXML(String file) {
        this.file = fxmlPath.concat(file);
    }

    @Override
    public String toString() {
        return file;
    }

}


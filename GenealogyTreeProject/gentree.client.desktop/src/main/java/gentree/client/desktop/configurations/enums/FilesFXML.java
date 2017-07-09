package gentree.client.desktop.configurations.enums;

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
    SCREEN_MAIN_LEFT_FAMILY_VIEW_FXML("tab.family.view.fxml");

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


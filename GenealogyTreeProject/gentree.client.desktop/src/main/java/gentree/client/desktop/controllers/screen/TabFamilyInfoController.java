package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import gentree.client.desktop.configurations.enums.FilesFXML;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLTab;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */

@Log4j2
public class TabFamilyInfoController implements Initializable, FXMLController, FXMLTab {

    @FXML
    private AnchorPane screenMainLeftFamilyInfoController;

    @FXML
    private JFXTextField familyName;

    @FXML
    private JFXTextField membersCount;

    @FXML
    private JFXButton modifyNameButton;

    @FXML
    private JFXButton addMemberButton;

    @FXML
    private VBox contentVBox;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @Getter
    @Setter
    private ScreenMainLeftController screenMainLeft;

    @Getter
    @Setter
    private Tab tab;

    @Getter
    @Setter
    private JFXTabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.languageBundle.setValue(resources);
        initGraphicalElements();
        loadFamily();
        addListener();
    }

    @FXML
    private void openNewMemberDialog() {
        sm.showNewDialog(new DialogAddMemberController(), FilesFXML.ADD_MEMBER_DIALOG);
    }

    private void loadFamily() {
        familyName.setText(context.getService().getCurrentFamily().getName().get());
    }

    private void initGraphicalElements() {
        familyName.setEditable(false);
        membersCount.setEditable(false);
        contentVBox.setAlignment(Pos.TOP_CENTER);
        membersCount.setText("" + context.getService().getCurrentFamily().getMembers().size());

    }

    private void addListener() {
        context.getService().getCurrentFamily().getMembers().addListener((InvalidationListener) observable -> {
            membersCount.setText("" + context.getService().getCurrentFamily().getMembers().size());
        });
    }


    @Override
    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tab = tab;
        this.tabPane = tabPane;
    }
}
package com.genealogytree.application.fxmlcontrollers;

import com.genealogytree.application.FXMLPaneController;
import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.configuration.FXMLFiles;
import com.genealogytree.domain.GTX_Member;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */

public class PaneMainApplicationWindowController implements Initializable, FXMLPaneController {

    private static final Logger LOG = LogManager.getLogger(PaneMainApplicationWindowController.class);

    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private AnchorPane mainApplicationWindow;

    @FXML
    private JFXTabPane gtMainTabPane;

    @FXML
    private TableView<GTX_Member> gtFamilyMemberTable;

    @FXML
    private TableColumn<GTX_Member, String> simNameColumn;

    @FXML
    private TableColumn<GTX_Member, String> simSurnameColumn;


    private Tab gtMainTabInfo;


    {
        gtMainTabInfo = new Tab();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.info("Initialisation " + this.getClass().getSimpleName() + ":  " + this.toString());

        this.languageBundle.setValue(rb);

    }


    private void setInfoTab() {
        this.manager.loadFxml(new TabInfoProjectPaneController(), gtMainTabPane, gtMainTabInfo, FXMLFiles.TAB_INFO_PROJECT.toString(), getValueFromKey("info"));
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
        // Nothing to do
    }

    @FXML
    public void showInfoMember(MouseEvent event) {
        if (event.getClickCount() == 2 && gtFamilyMemberTable.getSelectionModel().getSelectedItem() != null) {
            GTX_Member member = gtFamilyMemberTable.getSelectionModel().getSelectedItem();
            Tab infoSimTab = new Tab();
            TabInfoMemberPaneController tabInfoMember = (TabInfoMemberPaneController) this.manager.loadFxml(new TabInfoMemberPaneController(), gtMainTabPane, infoSimTab, FXMLFiles.TAB_INFO_MEMBER.toString(), member.getName().concat(" " + member.getSurname()));
            tabInfoMember.setMember(member);

        }
    }

    private void setCellFactory() {
        this.simNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.simSurnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
    }

    private void setMemberList() {
        this.gtFamilyMemberTable.setItems(this.context.getFamilyService().getCurrentFamily().getGtx_membersList());
    }



    /*
     * GETTERS AND SETTERS
     */
    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
        setInfoTab();

    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }
}

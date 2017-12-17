package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.desktop.service.RestConnectionService;
import gentree.client.desktop.service.ScreenManager;
import gentree.client.desktop.service.responses.FamilyListResponse;
import gentree.client.desktop.service.responses.FamilyResponse;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */
@Log4j2
public class ScreenOpenOnlineProjectController implements Initializable, FXMLController, FXMLAnchorPane {

    private final RestConnectionService rcs = RestConnectionService.INSTANCE;

    @FXML
    private AnchorPane screenOpenOnlineProjectPane;

    @FXML
    private VBox ROND_BUTTONS_VBOX;

    @FXML
    private TableView<Family> FAMILY_TABLE;

    @FXML
    private TableColumn<Family, String> FAMILY_NAME;


    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private ChangeListener<? super Number> familyTableLayoutXListener = this::familyTableLayoutXChange;

    @FXML
    private void chooseFamily() {
        if (FAMILY_TABLE.getSelectionModel().getSelectedItem() != null) {
            ServiceResponse response = context.getService().setCurrentFamily(FAMILY_TABLE.getSelectionModel().getSelectedItem());
            if (response instanceof FamilyResponse) {
                sm.loadFxml(new ScreenMainController(), sm.getMainWindowBorderPane(), FilesFXML.SCREEN_MAIN_FXML, ScreenManager.Where.CENTER);
            }
        }
    }

    @FXML
    private void refreshProjectList() {
        populateFamilyList();
    }


    @FXML
    private void newProject() {
        sm.showNewDialog(new DialogOpenProjectController(), FilesFXML.OPEN_PROJECT_DIALOG);
        refreshProjectList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        setFamilyCellFactory();
        populateFamilyList();
        initPanes();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    private void initPanes() {
        addLeftOffsetListener(FAMILY_TABLE);
        addLeftOffsetListener();
        FAMILY_TABLE.resize(540, 500);
        ROND_BUTTONS_VBOX.resize(200, 500);
    }


    private void populateFamilyList() {
        FAMILY_TABLE.getItems().clear();
        FamilyListResponse response = (FamilyListResponse) rcs.retrieveFamilies();

        if (response.getStatus() == ServiceResponse.ResponseStatus.OK) {
            FAMILY_TABLE.getItems().addAll(response.getList());

            System.out.println(FAMILY_TABLE.getItems());
        } else {
            System.out.println("ERROR POPOULATE LIST");
        }
    }

    private void setFamilyCellFactory() {
        this.FAMILY_NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    /*
            Position listener
     */

    /*
     *  LISTEN POSITION
     */

    //TODO REFACTOR

    private void addTopOffsetListener(TableView tableView) {
        this.screenOpenOnlineProjectPane.heightProperty()
                .addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    double y = (newValue.doubleValue() - tableView.getHeight()) / 2;
                    tableView.setLayoutY(y);
                });
    }

    private void addLeftOffsetListener(TableView tableView) {
        this.screenOpenOnlineProjectPane.widthProperty()
                .addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    double x = (newValue.doubleValue() - tableView.getWidth()) / 2;
                    tableView.setLayoutX(x);
                });
    }

    private void addTopOffsetListener(VBox vbox) {

        this.screenOpenOnlineProjectPane.heightProperty()
                .addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    double y = (newValue.doubleValue() - vbox.getHeight()) / 2;
                    vbox.setLayoutY(y);
                });
    }

    private void addLeftOffsetListener() {
        this.FAMILY_TABLE.layoutXProperty().addListener(familyTableLayoutXListener);
    }

    private void familyTableLayoutXChange(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double x = (newValue.doubleValue() - ROND_BUTTONS_VBOX.getWidth());
        ROND_BUTTONS_VBOX.setLayoutX(x);
    }
}

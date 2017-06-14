package genealogytree.application.fxmlcontrollers;

import genealogytree.application.FXMLTabController;
import genealogytree.application.GenealogyTreeContext;
import genealogytree.application.ScreenManager;
import genealogytree.configuration.ImageFiles;
import genealogytree.domain.GTX_Member;
import genealogytree.domain.GTX_Relation;
import genealogytree.domain.enums.RelationType;
import genealogytree.services.responses.RelationResponse;
import genealogytree.services.responses.ServerResponse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 25/11/2016.
 */
public class TabAddNewRelationPaneController implements Initializable, FXMLTabController {

    private static final Logger LOG = LogManager.getLogger(TabAddNewRelationPaneController.class);
    private static final int RELATION_HEIGHT = 50;
    private static final int RELATION_WIDTH = 50;
    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private AnchorPane addNewRelationTab;

    @FXML
    private JFXButton addSimCancelButton;

    @FXML
    private JFXButton addRelationConfirmButton;

    @FXML
    private JFXToggleButton toggleActiveButton;

    @FXML
    private AnchorPane templateAnchorPane;

    @FXML
    private ComboBox<GTX_Member> simLeftChoice;

    @FXML
    private ComboBox<GTX_Member> childChoice;

    @FXML
    private ComboBox<GTX_Member> simRightChoice;

    @FXML
    private ComboBox<RelationType> relationType;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private Tab tab;
    private JFXTabPane tabPane;

    private boolean isActive = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInfoLog("Initialisation : " + this.toString());

        this.languageBundle.setValue(rb);

        relationType.setItems(FXCollections.observableArrayList(
                RelationType.NEUTRAL,
                RelationType.LOVE,
                RelationType.FIANCE,
                RelationType.MARRIED
        ));

        setCellFactoryToCombobox(simLeftChoice);
        setCellFactoryToCombobox(simRightChoice);
        setCellFactoryToCombobox(childChoice);
        setCellFactoryToRelationType(relationType);

        toggleActiveButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true) {
                    toggleActiveButton.setText("Active");
                } else {
                    toggleActiveButton.setText("Inactive");
                }
            }
        });

        toggleActiveButton.setSelected(true);


    }

    @FXML
    public void addRelationConfirm() {
        GTX_Relation relation = new GTX_Relation(simLeftChoice.getValue(), simRightChoice.getValue(), childChoice.getValue(), relationType.getValue(), true);
        ServerResponse response = this.context.getFamilyService().addNewRelation(relation);

        if (response instanceof RelationResponse) {
            this.tabPane.getTabs().remove(tab);
        }
    }

    @FXML
    public void setRelationCancel() {
        this.tabPane.getTabs().remove(tab);
    }

    @FXML
    public void activeToggleButtonEvent(ActionEvent event) {


    }

    private void setCellFactoryToCombobox(ComboBox<GTX_Member> combobox) {

        combobox.setCellFactory(param -> {
            final ListCell<GTX_Member> cell = getCustomListCellMembers();
            return cell;
        });

        combobox.setButtonCell(getCustomListCellMembers());

    }

    private void setCellFactoryToRelationType(ComboBox<RelationType> combobox) {
        combobox.setCellFactory(getCustomRelationListCellMembers());
        combobox.setButtonCell(getCustomRelationListCellMembers().call(null));

    }

    private Callback<ListView<RelationType>, ListCell<RelationType>> getCustomRelationListCellMembers() {
        Callback<ListView<RelationType>, ListCell<RelationType>> callback = new Callback<ListView<RelationType>, ListCell<RelationType>>() {
            @Override
            public ListCell<RelationType> call(ListView<RelationType> param) {
                final ListCell<RelationType> relationCell = new ListCell<RelationType>() {
                    @Override
                    public void updateItem(RelationType item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            switch (item) {
                                case NEUTRAL:
                                    setGraphic(setGraphicToImageView(ImageFiles.RELATION_NEUTRAL.toString(), RELATION_WIDTH, RELATION_HEIGHT));
                                    setText("");
                                    break;
                                case LOVE:
                                    setGraphic(setGraphicToImageView(ImageFiles.RELATION_LOVE.toString(), RELATION_WIDTH, RELATION_HEIGHT));
                                    setText("");
                                    break;
                                case FIANCE:
                                    setGraphic(setGraphicToImageView(ImageFiles.RELATION_FIANCE.toString(), RELATION_WIDTH, RELATION_HEIGHT));
                                    setText("");
                                    break;
                                case MARRIED:
                                    setGraphic(setGraphicToImageView(ImageFiles.RELATION_MARRIED.toString(), RELATION_WIDTH, RELATION_HEIGHT));
                                    setText("");
                                    break;
                                default:
                                    setGraphic(setGraphicToImageView(ImageFiles.RELATION_NEUTRAL.toString(), RELATION_WIDTH, RELATION_HEIGHT));
                                    setText("");
                            }
                        } else {
                            setGraphic(setGraphicToImageView(ImageFiles.RELATION_NEUTRAL.toString(), RELATION_WIDTH, RELATION_HEIGHT));
                            setText("");
                        }
                    }

                };
                return relationCell;
            }
        };
        return callback;
    }

    private ListCell<GTX_Member> getCustomListCellMembers() {
        ListCell<GTX_Member> cell = new ListCell<GTX_Member>() {
            @Override
            public void updateItem(GTX_Member item, boolean empty) {
                super.updateItem(item, empty);
                {
                    if (item != null) {

                        ImageView imv = new ImageView(item.getPhoto());
                        imv.setFitWidth(80);
                        imv.setFitHeight(100);
                        setGraphic(imv);
                        setText(item.getName() + " " + item.getSurname().toUpperCase());
                    } else {
                        setGraphic(null);
                        setText("null");
                    }

                }
            }
        };
        return cell;
    }

    private ImageView setGraphicToImageView(String path, int width, int height) {
        ImageView imv = new ImageView(path);
        imv.setFitWidth(width);
        imv.setFitHeight(height);
        return imv;
    }

    private void populateComboBox(ObservableList<GTX_Member> list, ComboBox<GTX_Member> comboBox) {
        comboBox.setItems(list);
    }


    /*
     * LISTEN LANGUAGE CHANGES
     */
    private void addLanguageListener() {
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        // Nothing to do
    }


    /*
     * GETTERS AND SETTERS
     */

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public JFXTabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(JFXTabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        this.tab = tab;
    }

    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
        populateComboBox(context.getFamilyService().getCurrentFamily().getGtx_membersList(), simLeftChoice);
        populateComboBox(context.getFamilyService().getCurrentFamily().getGtx_membersList(), simRightChoice);
        populateComboBox(context.getFamilyService().getCurrentFamily().getGtx_membersList(), childChoice);

    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }

    private void setInfoLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.info(msg);
        System.out.println("INFO:  " + msg);
    }

    private void setErrorLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.error(msg);
        System.out.println("ERROR:  " + msg);
    }
}

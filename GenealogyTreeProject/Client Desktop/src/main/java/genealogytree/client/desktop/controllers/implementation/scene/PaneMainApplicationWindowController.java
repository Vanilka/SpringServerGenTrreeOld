package genealogytree.client.desktop.controllers.implementation.scene;


import genealogytree.client.desktop.configuration.ContextGT;
import genealogytree.client.desktop.configuration.ScreenManager;
import genealogytree.client.desktop.configuration.enums.FXMLFile;
import genealogytree.client.desktop.configuration.enums.ImageFiles;
import genealogytree.client.desktop.configuration.messages.LogMessages;
import genealogytree.client.desktop.controllers.FXMLPane;
import genealogytree.client.desktop.domain.GTX_Member;
import genealogytree.client.desktop.domain.GTX_Relation;
import genealogytree.domain.enums.RelationType;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */

@Log4j2
public class PaneMainApplicationWindowController implements Initializable, FXMLPane {

    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();
    private static int TABLE_IMAGE_MEMBER_HEIGHT = 80;
    private static int TABLE_IMAGE_MEMBER_WIDTH = 60;
    private final ToggleGroup buttonsTableGroup;
    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();
    @FXML
    private AnchorPane mainApplicationWindow;
    @FXML
    private AnchorPane workAnchorPane;
    @FXML
    private AnchorPane workAnchorPaneContent;
    @FXML
    private JFXTabPane gtMainTabPane;
    @FXML
    private TableView<GTX_Member> gtFamilyMemberTable;
    @FXML
    private TableColumn<GTX_Member, String> simNameColumn;
    @FXML
    private TableColumn<GTX_Member, String> simSurnameColumn;
    @FXML
    private TableView<GTX_Relation> gtFamilyRelationTable;
    @FXML
    private TableColumn<GTX_Relation, GTX_Member> relationSimLeftColumn;
    @FXML
    private TableColumn<GTX_Relation, RelationType> relationTypeColumn;
    @FXML
    private TableColumn<GTX_Relation, GTX_Member> relationSimRightColumn;
    @FXML
    private ToggleButton buttonShowMemberTable;
    @FXML
    private ToggleButton buttonShowRelationTable;
    private Tab gtMainTabInfo;


    {
        gtMainTabInfo = new Tab();
        buttonsTableGroup = new ToggleGroup();
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        gtFamilyMemberTable.setVisible(true);
        gtFamilyRelationTable.setVisible(false);

        this.languageBundle.setValue(rb);

        setButtonToToggleGroup();
        setCellValueFactory();

        this.relationSimLeftColumn.setCellFactory(setMemberCellFactory("LEFT"));
        this.relationSimRightColumn.setCellFactory(setMemberCellFactory("RIGHT"));
        this.relationTypeColumn.setCellFactory(setRelationTypeCellFactory());

        addLanguageListener();
        setInfoTab();
        setMemberList();
        setRelationList();
        setWorkingPane();
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);

    }

    private void setInfoTab() {
        this.sc.loadFxml(new TabInfoProjectPaneController(), gtMainTabPane, gtMainTabInfo, FXMLFile.TAB_INFO_PROJECT, getValueFromKey("info"));
    }


    private void setWorkingPane() {
        PaneGenealogyTreeDrawController ctr = (PaneGenealogyTreeDrawController) sc.loadFxml(new PaneGenealogyTreeDrawController(), workAnchorPaneContent, FXMLFile.GENEALOGY_TREE_DRAW);
    }

    private void setButtonToToggleGroup() {
        buttonShowMemberTable.setToggleGroup(buttonsTableGroup);
        buttonShowRelationTable.setToggleGroup(buttonsTableGroup);

        buttonsTableGroup.selectToggle(buttonShowMemberTable);

        buttonsTableGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (newValue == null) {
                buttonsTableGroup.selectToggle(oldValue);
            }
            if (buttonsTableGroup.getSelectedToggle().equals(buttonShowMemberTable)) {
                gtFamilyMemberTable.setVisible(true);
                gtFamilyRelationTable.setVisible(false);
            } else {
                gtFamilyMemberTable.setVisible(false);
                gtFamilyRelationTable.setVisible(true);
            }
        });
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
            TabInfoMemberPaneController tabInfoMember = (TabInfoMemberPaneController) sc.loadFxml(new TabInfoMemberPaneController(), gtMainTabPane, infoSimTab, FXMLFile.TAB_INFO_MEMBER, member.getName().concat(" " + member.getSurname()));
            tabInfoMember.setMember(member);

        }
    }

    @FXML
    public void showInfoRelation(MouseEvent event) {
        if (event.getClickCount() == 2 && gtFamilyRelationTable.getSelectionModel().getSelectedItem() != null) {
            GTX_Relation relation = gtFamilyRelationTable.getSelectionModel().getSelectedItem();
            Tab infoRelationTab = new Tab();
            TabInfoRelationPaneController tabInfoRelation = (TabInfoRelationPaneController) sc.loadFxml(new TabInfoRelationPaneController(), gtMainTabPane, infoRelationTab, FXMLFile.TAB_INFO_RELATION, "Relation");
            tabInfoRelation.setRelation(relation);
        }
    }

    private void setCellValueFactory() {
        this.simNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.simSurnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());

        this.relationSimLeftColumn.setCellValueFactory(cellData -> cellData.getValue().simLeftProperty());
        this.relationSimRightColumn.setCellValueFactory(cellData -> cellData.getValue().simRightProperty());
        this.relationTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

    }


    private void setMemberList() {
        this.gtFamilyMemberTable.setItems(context.getService().getCurrentFamily().getMembersList());
    }

    private void setRelationList() {
        this.gtFamilyRelationTable.setItems(context.getService().getCurrentFamily().getRelationsList());
    }

    private Callback<TableColumn<GTX_Relation, GTX_Member>,
            TableCell<GTX_Relation, GTX_Member>> setMemberCellFactory(String parameter) {
        Callback<TableColumn<GTX_Relation, GTX_Member>, TableCell<GTX_Relation, GTX_Member>> callback =
                new Callback<TableColumn<GTX_Relation, GTX_Member>, TableCell<GTX_Relation, GTX_Member>>() {
                    @Override
                    public TableCell<GTX_Relation, GTX_Member> call(TableColumn<GTX_Relation, GTX_Member> param) {
                        TableCell<GTX_Relation, GTX_Member> cell = new TableCell<GTX_Relation, GTX_Member>() {

                            @Override
                            protected void updateItem(GTX_Member item, boolean empty) {
                                super.updateItem(item, empty);
                                ImageView imageview = new ImageView();
                                if (item != null) {
                                    imageview.setFitHeight(TABLE_IMAGE_MEMBER_HEIGHT);
                                    imageview.setFitWidth(TABLE_IMAGE_MEMBER_WIDTH);
                                    imageview.setImage(new Image(item.getPhoto()));
                                    setGraphic(imageview);
                                } else {
                                    if (!empty) {
                                        imageview.setFitHeight(TABLE_IMAGE_MEMBER_HEIGHT);
                                        imageview.setFitWidth(TABLE_IMAGE_MEMBER_WIDTH);
                                        String path = parameter.equals("LEFT") == true ?
                                                ImageFiles.NO_NAME_FEMALE.toString() : ImageFiles.NO_NAME_MALE.toString();
                                        imageview.setImage(new Image(path));
                                        setGraphic(imageview);
                                    } else {
                                        setGraphic(null);
                                    }
                                }
                            }

                        };
                        return cell;
                    }
                };

        return callback;
    }

    private Callback<TableColumn<GTX_Relation, RelationType>,
            TableCell<GTX_Relation, RelationType>> setRelationTypeCellFactory() {

        Callback<TableColumn<GTX_Relation, RelationType>,
                TableCell<GTX_Relation, RelationType>> callback = new Callback<TableColumn<GTX_Relation, RelationType>, TableCell<GTX_Relation, RelationType>>() {
            @Override
            public TableCell<GTX_Relation, RelationType> call(TableColumn<GTX_Relation, RelationType> param) {
                TableCell<GTX_Relation, RelationType> cell = new TableCell<GTX_Relation, RelationType>() {
                    @Override
                    protected void updateItem(RelationType item, boolean empty) {
                        super.updateItem(item, empty);
                        ImageView imageview = new ImageView();
                        if (item != null) {
                            String path;
                            switch (item) {
                                case FIANCE:
                                    path = ImageFiles.RELATION_FIANCE.toString();
                                    break;
                                case MARRIED:
                                    path = ImageFiles.RELATION_MARRIED.toString();
                                    break;
                                case LOVE:
                                    path = ImageFiles.RELATION_LOVE.toString();
                                    break;
                                default:
                                    path = ImageFiles.RELATION_NEUTRAL.toString();
                                    break;
                            }
                            imageview.setImage(new Image(path));
                            imageview.setFitHeight(40);
                            imageview.setFitWidth(40);
                            setGraphic(imageview);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        };

        return callback;
    }

    private ImageView setGraphicToImageView(ImageView imv, String path) {
        imv = new ImageView(path);
        return imv;
    }

}

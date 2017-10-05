package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXTabPane;
import gentree.client.desktop.configuration.messages.Keys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLTab;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.visualization.elements.configuration.ImageFiles;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 03/07/2017.
 */
@Getter
@Setter
@Log4j2
public class TabFamilyViewController implements Initializable, FXMLController, FXMLAnchorPane, FXMLTab {

    public static final String TABLE_RELATION_SIM_RIGHT = "RIGHT";
    public static final String TABLE_RELATION_SIM_LEFT = "LEFT";
    private static int TABLE_IMAGE_MEMBER_HEIGHT = 80;
    private static int TABLE_IMAGE_MEMBER_WIDTH = 60;
    private final ToggleGroup buttonsTableGroup;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private JFXTabPane gtMainTabPane;
    @FXML
    private TableView<Member> gtFamilyMemberTable;
    @FXML
    private TableColumn<Member, String> simNameColumn;
    @FXML
    private TableColumn<Member, String> simSurnameColumn;
    @FXML
    private TableColumn<Member, String> simPhotoColumn;
    @FXML
    private TableView<Relation> gtFamilyRelationTable;
    @FXML
    private TableColumn<Relation, Member> relationSimLeftColumn;
    @FXML
    private TableColumn<Relation, RelationType> relationTypeColumn;
    @FXML
    private TableColumn<Relation, Member> relationSimRightColumn;
    @FXML
    private ToggleButton buttonShowMemberTable;
    @FXML
    private ToggleButton buttonShowRelationTable;

    private ScreenMainLeftController screenMainLeft;

    private Tab tab;
    private JFXTabPane tabPane;

    {
        buttonsTableGroup = new ToggleGroup();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        this.languageBundle.bind(context.getBundle());
        addLanguageListener();

        gtFamilyMemberTable.setVisible(true);
        gtFamilyRelationTable.setVisible(false);

        setButtonToToggleGroup();
        setCellValueFactory();

        this.relationSimLeftColumn.setCellFactory(setMemberCellFactory(TABLE_RELATION_SIM_LEFT));
        this.relationSimRightColumn.setCellFactory(setMemberCellFactory(TABLE_RELATION_SIM_RIGHT));
        this.relationTypeColumn.setCellFactory(setRelationTypeCellFactory());
        this.simPhotoColumn.setCellFactory(setPhotoCellFactory());

        setMemberList();
        setRelationList();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);

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
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        // Nothing to do
        buttonShowMemberTable.setText(getValueFromKey(Keys.BUTTON_MEMBERS));
        buttonShowRelationTable.setText(getValueFromKey(Keys.BUTTON_MEMBERS));
    }

    @FXML
    public void showInfoMember(MouseEvent event) {
        Member selected = gtFamilyMemberTable.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && selected != null) {
            sm.getScreenMainController().showInfoSim(selected);

        }
    }

    @FXML
    public void showInfoRelation(MouseEvent event) {
        Relation selected = gtFamilyRelationTable.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && selected != null) {
            sm.getScreenMainController().showInfoRelation(selected);
        }
    }

    private void setCellValueFactory() {
        this.simNameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        this.simSurnameColumn.setCellValueFactory(data -> data.getValue().surnameProperty());
        this.simPhotoColumn.setCellValueFactory(sm.getPhotoValueFactory());

        this.relationSimLeftColumn.setCellValueFactory(data -> data.getValue().leftProperty());
        this.relationSimRightColumn.setCellValueFactory(data -> data.getValue().rightProperty());
        this.relationTypeColumn.setCellValueFactory(data -> data.getValue().typeProperty());

    }


    private void setMemberList() {
        this.gtFamilyMemberTable.setItems(context.getService().getCurrentFamily().getMembers());
    }

    private void setRelationList() {
        this.gtFamilyRelationTable.setItems(context.getService().getCurrentFamily().getRelations());
    }

    private Callback<TableColumn<Relation, Member>,
            TableCell<Relation, Member>> setMemberCellFactory(String parameter) {
        Callback<TableColumn<Relation, Member>, TableCell<Relation, Member>> callback =
                new Callback<TableColumn<Relation, Member>, TableCell<Relation, Member>>() {
                    @Override
                    public TableCell<Relation, Member> call(TableColumn<Relation, Member> param) {
                        TableCell<Relation, Member> cell = new TableCell<Relation, Member>() {

                            @Override
                            protected void updateItem(Member item, boolean empty) {
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
                                        String path = parameter.equals(TABLE_RELATION_SIM_LEFT) ?
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

    private Callback<TableColumn<Relation, RelationType>,
            TableCell<Relation, RelationType>> setRelationTypeCellFactory() {
        Callback<TableColumn<Relation, RelationType>,
                TableCell<Relation, RelationType>> callback = new Callback<TableColumn<Relation, RelationType>, TableCell<Relation, RelationType>>() {
            @Override
            public TableCell<Relation, RelationType> call(TableColumn<Relation, RelationType> param) {
                TableCell<Relation, RelationType> cell = new TableCell<Relation, RelationType>() {
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

    private Callback<TableColumn<Member, String>,
            TableCell<Member, String>> setPhotoCellFactory() {

        Callback<TableColumn<Member, String>, TableCell<Member, String>> callback =
                new Callback<TableColumn<Member, String>, TableCell<Member, String>>() {
                    @Override
                    public TableCell<Member, String> call(TableColumn<Member, String> param) {

                        TableCell<Member, String> cell = new TableCell<Member, String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                ImageView imageview = new ImageView();
                                imageview.setFitHeight(TABLE_IMAGE_MEMBER_HEIGHT);
                                imageview.setFitWidth(TABLE_IMAGE_MEMBER_WIDTH);
                                if (item != null) {
                                    imageview.setImage(new Image(item));
                                    setGraphic(imageview);
                                } else {
                                    if (!empty) {

                                        imageview.setImage(new Image(ImageFiles.GENERIC_MALE.toString()));
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

    private ImageView setGraphicToImageView(ImageView imv, String path) {
        imv = new ImageView(path);
        return imv;
    }


    @Override
    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tab = tab;
        this.tabPane = tabPane;
    }
}
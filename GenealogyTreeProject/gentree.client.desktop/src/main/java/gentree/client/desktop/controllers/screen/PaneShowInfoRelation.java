package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gentree.client.desktop.configuration.messages.Keys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.visualization.controls.HeaderPane;
import gentree.client.visualization.elements.FamilyMemberCard;
import gentree.client.visualization.elements.RelationTypeCard;
import gentree.client.visualization.elements.configuration.ImageFiles;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 10/07/2017.
 */

@Log4j2
public class PaneShowInfoRelation implements Initializable, FXMLController, FXMLAnchorPane {

    private static int TABLE_IMAGE_MEMBER_HEIGHT = 80;
    private static int TABLE_IMAGE_MEMBER_WIDTH = 60;

    @FXML
    @Getter
    @Setter
    private AnchorPane paneShowInfoRelation;

    @FXML
    private AnchorPane CONTENT_PANE;

    @FXML
    private HeaderPane HEADER_PANE;

    @FXML
    private JFXButton RETURN_BUTTON;

    @FXML
    private JFXTextField RELATION_ID_FIELD;


    @FXML
    private StackPane RELATION_TYPE_PANE;

    @FXML
    private TableView<Member> FAMILY_MEMBER_TABLE;

    @FXML
    private TableColumn<Member, String> SIM_NAME_COLUMN;
    @FXML
    private TableColumn<Member, String> SIM_SURNAME_COLUMN;
    @FXML
    private TableColumn<Member, String> SIM_PHOTO_COLUMN;


    @FXML
    private FamilyMemberCard motherCard;

    @FXML
    private FamilyMemberCard fatherCard;


    private RelationTypeCard relationTypeElement;

    private ObjectProperty<Relation> relation;

    private List<? extends Control> readOnlyControls;

    // TODO  Refactor Labels

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private ChangeListener<? super ResourceBundle> languageListener = this::languageChange;
    private ChangeListener<? super Relation> relationListener = this::relationChanged;


    {
        motherCard = new FamilyMemberCard();
        fatherCard = new FamilyMemberCard();
        relationTypeElement = new RelationTypeCard();
        relation = new SimpleObjectProperty<>();

    }

    @FXML
    private void returnAction() {
        cleanListeners();
        sm.getScreenMainController().removeInfoPanel(paneShowInfoRelation);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        this.languageBundle.bind(context.getBundle());
        addLanguageListener();

        readOnlyControls = Arrays.asList(RELATION_ID_FIELD);
        initPanes();
        initControlsProperties();
        initListeners();

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);

    }

    private void initPanes() {
        RELATION_TYPE_PANE.getChildren().add(relationTypeElement);
        setCellValueFactory();
    }

    private void initControlsProperties() {

        readOnlyControls.forEach(control -> {
            if (control instanceof JFXTextField) {
                ((JFXTextField) control).setEditable(true);
            }
        });
    }

    private void populateControls(Relation r) {
        //TODO populate controls
        RELATION_ID_FIELD.setText(Long.toString(r.getId()));
        motherCard.setMember(r.getLeft());
        fatherCard.setMember(r.getRight());
        relationTypeElement.setRelation(r);
        FAMILY_MEMBER_TABLE.setItems(r.getChildren());
    }

    private void setCellValueFactory() {
        this.SIM_NAME_COLUMN.setCellValueFactory(data -> data.getValue().nameProperty());
        this.SIM_SURNAME_COLUMN.setCellValueFactory(data -> data.getValue().surnameProperty());
        this.SIM_PHOTO_COLUMN.setCellValueFactory(sm.getPhotoValueFactory());
        this.SIM_PHOTO_COLUMN.setCellFactory(setPhotoCellFactory());
    }

    /*
        Listeners
     */

    private void initListeners() {
        relation.addListener(relationListener);
    }

    private void cleanListeners() {
        relation.removeListener(relationListener);
        this.languageBundle.unbind();
        languageBundle.removeListener(languageListener);
    }

    private void languageChange(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        reloadElements();
    }

    private void relationChanged(ObservableValue<? extends Relation> observable, Relation oldValue, Relation newValue) {
        if (newValue != null) {
            populateControls(newValue);
        }
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


    /*
     *   LISTEN LANGUAGE CHANGES
     */

    private void addLanguageListener() {
        this.languageBundle.addListener(languageListener);
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        //Todo reload gentree.client.visualization.elements after refactor labels
        SIM_PHOTO_COLUMN.setText(getValueFromKey(Keys.AVATAR));
        SIM_NAME_COLUMN.setText(getValueFromKey(Keys.SIM_NAME));
        SIM_SURNAME_COLUMN.setText(getValueFromKey(Keys.SIM_SURNAME));
        RETURN_BUTTON.setText(getValueFromKey(Keys.RETURN));
        HEADER_PANE.setTitle(getValueFromKey(Keys.HEADER_INF_RELATION));
    }



    /*
        GETTER AND SETTERS
     */

    public Relation getRelation() {
        return relation.get();
    }

    public void setRelation(Relation relation) {
        this.relation.set(relation);
    }

    public ObjectProperty<Relation> relationProperty() {
        return relation;
    }

}
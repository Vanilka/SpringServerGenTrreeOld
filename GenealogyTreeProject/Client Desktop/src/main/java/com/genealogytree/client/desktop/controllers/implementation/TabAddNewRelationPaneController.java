package com.genealogytree.client.desktop.controllers.implementation;

import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.configuration.enums.ImageFiles;
import com.genealogytree.client.desktop.controllers.FXMLTab;
import com.genealogytree.client.desktop.domain.GTX_Member;
import com.genealogytree.client.desktop.domain.GTX_Relation;
import com.genealogytree.client.desktop.service.responses.RelationResponse;
import com.genealogytree.client.desktop.service.responses.ServiceResponse;
import com.genealogytree.domain.enums.RelationType;
import com.genealogytree.domain.enums.Sex;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Created by vanilka on 25/11/2016.
 */
public class TabAddNewRelationPaneController implements Initializable, FXMLTab {

    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();
    private static final Logger LOG = LogManager.getLogger(TabAddNewRelationPaneController.class);
    private static final int RELATION_HEIGHT = 50;
    private static final int RELATION_WIDTH = 50;
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

    private GTX_Member selectedLeft;
    private GTX_Member selectedRight;
    private GTX_Member selectedChild;

    private GTX_Member simNull = new GTX_Member("Noname", "Noname", null, null, null);

    private boolean isActive = false;

    private static Predicate<GTX_Member> sexFilter(Sex sex) {
        return element -> element.getSex().equals(sex);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.languageBundle.setValue(rb);
        relationType.setItems(FXCollections.observableArrayList(
                RelationType.NEUTRAL,
                RelationType.LOVE,
                RelationType.FIANCE,
                RelationType.MARRIED
        ));



        /*
            COMBOBOX CELL FACTORY
         */

        setCellFactoryToCombobox(simLeftChoice);
        setCellFactoryToCombobox(simRightChoice);
        setCellFactoryToCombobox(childChoice);
        setCellFactoryToRelationType(relationType);
        toggleActiveButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == true) {
                toggleActiveButton.setText("Active");
            } else {
                toggleActiveButton.setText("Inactive");
            }
        });

        toggleActiveButton.setSelected(true);

        addLanguageListener();
        addRegisterButtonDisableCondition();

        /*
            POPULATE COMBOBOX
         */
        addSelectedMemberListener();
        populateComboBox(context.getService().getCurrentFamily().getGtx_membersList().filtered(sexFilter(Sex.FEMALE)), simLeftChoice);
        populateComboBox(context.getService().getCurrentFamily().getGtx_membersList().filtered(sexFilter(Sex.MALE)), simRightChoice);
        populateComboBox(context.getService().getCurrentFamily().getGtx_membersList()
                .filtered(p ->context.getService().getCurrentFamily().getBornRelation(p).getSimLeft() == null )
                .filtered(p -> context.getService().getCurrentFamily().getBornRelation(p).getSimRight() == null), childChoice);


        simLeftChoice.getSelectionModel().selectFirst();
        simRightChoice.getSelectionModel().selectFirst();
        childChoice.getSelectionModel().selectFirst();
    }

    @FXML
    public void addRelationConfirm() {

        GTX_Relation relation = new GTX_Relation(
                selectedLeft == null || selectedLeft.equals(simNull) ? null : selectedLeft,
                selectedRight == null || selectedRight.equals(simNull) ? null : selectedRight,
                selectedChild == null || selectedChild.equals(simNull) ? null : selectedChild,
                relationType.getValue(),
                true);
        ServiceResponse response = context.getService().addRelation(relation);

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

    @FXML
    public void choseFather(ActionEvent event) {
        refreshChoseLeft();
         refreshChild();

    }

    @FXML
    public void choseMother(ActionEvent event) {
        refreshChoseRight();
        refreshChild();
    }

    @FXML
    public void choseChild(ActionEvent event) {
        refreshChoseLeft();
        refreshChoseRight();
    }

    private void addSelectedMemberListener() {
        simLeftChoice.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedLeft = newValue;
        });

        simRightChoice.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedRight = newValue;
        });

        childChoice.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedChild = newValue;
        });
    }

    private void setCellFactoryToCombobox(ComboBox<GTX_Member> combobox) {

        combobox.setCellFactory(param -> {
            final ListCell<GTX_Member> cell = getCustomListCellMembers(combobox);
            return cell;
        });

        combobox.setButtonCell(getCustomListCellMembers(combobox));

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

    private ListCell<GTX_Member> getCustomListCellMembers(ComboBox<GTX_Member> comboBox) {
        ListCell<GTX_Member> cell = new ListCell<GTX_Member>() {
            @Override
            public void updateItem(GTX_Member item, boolean empty) {
                super.updateItem(item, empty);
                {
                    if (item != null) {
                        if (item != simNull) {
                            ImageView imv = new ImageView(item.getPhoto());
                            imv.setFitWidth(80);
                            imv.setFitHeight(100);
                            setGraphic(imv);
                            setText(item.getName() + " " + item.getSurname().toUpperCase());
                        } else if (comboBox.equals(simLeftChoice)) {

                            ImageView imv = new ImageView(ImageFiles.NO_NAME_FEMALE.toString());
                            imv.setFitWidth(80);
                            imv.setFitHeight(100);
                            setGraphic(imv);
                            setText("NO NAME");
                        } else if (comboBox.equals(simRightChoice) || comboBox.equals(childChoice)) {
                            ImageView imv = new ImageView(ImageFiles.NO_NAME_MALE.toString());
                            imv.setFitWidth(80);
                            imv.setFitHeight(100);
                            setGraphic(imv);
                            setText("NO NAME");
                        }
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

    /*
        LISTEN CHANGE CHOOSE SIM
     */

    private void populateComboBox(ObservableList<GTX_Member> list, ComboBox<GTX_Member> comboBox) {

        comboBox.getItems().addAll(simNull);
        comboBox.getItems().addAll(list);

    }

    private void refreshChoseRight() {
        ObservableList<GTX_Member> list = context.getService().getCurrentFamily().getGtx_membersList().filtered(sexFilter(Sex.MALE));

        // Remove sisters from List
        if (selectedLeft != null && !selectedLeft.equals(simNull)) {
            for (GTX_Member member : context.getService().getCurrentFamily().getBornRelation(selectedLeft).getChildren()) {
                if (member.getSex().equals(Sex.MALE)) {
                    list = list.filtered(p -> (!p.equals(member)));
                }
            }
            list = removeDescends(selectedLeft, list);
            list = removeAscendsLeft(selectedLeft, list);
        } else {
            // DO NOTHING
        }

        // Reinitialisation ComboBox
        reinitializationComboBox(simRightChoice, list, selectedRight);

    }

    private void refreshChoseLeft() {
        ObservableList<GTX_Member> list = context.getService().getCurrentFamily().getGtx_membersList().filtered(sexFilter(Sex.FEMALE));

        // Remove sisters from List
        if (selectedRight != null && !selectedRight.equals(simNull)) {
            for (GTX_Member member : context.getService().getCurrentFamily().getBornRelation(selectedRight).getChildren()) {
                if (member.getSex().equals(Sex.FEMALE)) {
                    list = list.filtered(p -> (!p.equals(member)));
                }
            }
            list = removeDescends(selectedRight, list);
            list = removeAscendsRight(selectedRight, list);
        } else {
            //DO NOTHING
        }

        // Reinitialisation ComboBox
        reinitializationComboBox(simLeftChoice, list, selectedLeft);
    }

    private void refreshChild() {

        System.out.println("test1");

        ObservableList<GTX_Member> tempListChildren = context.getService().getCurrentFamily().getGtx_membersList()
                .filtered(p -> context.getService().getCurrentFamily().getBornRelation(p).getSimLeft() == null
                        && context.getService().getCurrentFamily().getBornRelation(p).getSimRight() == null);

        System.out.println("test2");
        if (selectedLeft != null && !selectedLeft.equals(simNull)) {
            tempListChildren = tempListChildren.filtered(p -> !p.equals(selectedLeft));
            tempListChildren = removeAllAscends(selectedLeft, tempListChildren);

        }

        System.out.println("test3");
        if (selectedRight != null && !selectedRight.equals(simNull)) {
            tempListChildren = tempListChildren.filtered(p -> !p.equals(selectedRight));
            tempListChildren = removeAllAscends(selectedRight, tempListChildren);
        }

        System.out.println("test4");

        if (selectedChild == null || selectedChild.equals(simNull)) {
            System.out.println("test5");
            childChoice.getItems().removeIf(p -> p != simNull);
            System.out.println("test5aa");
            childChoice.getItems().addAll(tempListChildren);
            System.out.println("test5a");

        } else if (tempListChildren.contains(selectedChild)) {
            System.out.println("test6");
            tempListChildren = tempListChildren.filtered(p -> !p.equals(selectedChild));
            childChoice.getItems().removeIf(p -> p != selectedChild);
            childChoice.getItems().addAll(tempListChildren);
            System.out.println("test6a");
        } else {
            System.out.println("test7");
            childChoice.getItems().clear();
            childChoice.getItems().addAll(tempListChildren);
            System.out.println("test7a");

        }
    }

    private void reinitializationComboBox(ComboBox<GTX_Member> comboBox, ObservableList<GTX_Member> list, GTX_Member selectedBean) {
        // Reinitialisation ComboBox
        if (selectedBean == null || selectedBean.equals(simNull)) {
            comboBox.getItems().removeIf(p -> ((p != null) && (!p.equals(simNull))));
            comboBox.getItems().addAll(list);
        } else if (!list.contains(selectedBean)) {
            comboBox.getItems().clear();
            comboBox.getItems().add(simNull);
            comboBox.getItems().addAll(list);
            comboBox.getSelectionModel().selectFirst();

            //do nothing
        } else {
            list = list.filtered(p -> !p.equals(selectedBean));
            comboBox.getItems().removeIf(p -> ((p != null && !p.equals(simNull)) && !p.equals(selectedBean)));
            comboBox.getItems().addAll(list);
        }
    }

    private ObservableList<GTX_Member> removeDescends(GTX_Member member, ObservableList<GTX_Member> list) {
        ObservableList<GTX_Relation> relations = context.getService().getCurrentFamily().getGtx_relations()
                .filtered(p -> ((p.getSimRight() != null && p.getSimRight().equals(member))
                        | (p.getSimLeft() != null && p.getSimLeft().equals(member))));

        if (relations != null && relations.size() > 0) {
            for (GTX_Relation r : relations) {
                if (r.getChildren() != null && r.getChildren().size() > 0) {
                    for (GTX_Member m : r.getChildren()) {
                        list = list.filtered(q -> (!q.equals(m)));
                        removeDescends(m, list);
                    }
                }
            }
        }
        return list;
    }

    private ObservableList<GTX_Member> removeAscendsRight(GTX_Member member, ObservableList<GTX_Member> list) {
        list = list.filtered(p -> (!p.equals(context.getService().getCurrentFamily().getBornRelation(member).getSimRight())));
        return list;
    }

    private ObservableList<GTX_Member> removeAscendsLeft(GTX_Member member, ObservableList<GTX_Member> list) {
        list = list.filtered(p -> (!p.equals(context.getService().getCurrentFamily().getBornRelation(member).getSimLeft())));
        return list;
    }

    private ObservableList<GTX_Member> removeAllAscends(GTX_Member grain, ObservableList<GTX_Member> listToFiltr) {
        for (GTX_Member m : context.getService().getCurrentFamily().getBornRelation(grain).getChildren()) {
            listToFiltr = listToFiltr.filtered(p -> (!p.equals(m)));
        }
        //For Fathers
        if (context.getService().getCurrentFamily().getBornRelation(grain).getSimLeft() != null) {
            listToFiltr = listToFiltr.filtered(p -> (!p.equals(context.getService().getCurrentFamily().getBornRelation(grain).getSimLeft())));
            listToFiltr = removeAllAscends(context.getService().getCurrentFamily().getBornRelation(grain).getSimLeft(), listToFiltr);

        }

        if (context.getService().getCurrentFamily().getBornRelation(grain).getSimRight() != null) {
            listToFiltr = listToFiltr.filtered(p -> (!p.equals(context.getService().getCurrentFamily().getBornRelation(grain).getSimRight())));
            listToFiltr = removeAllAscends(context.getService().getCurrentFamily().getBornRelation(grain).getSimRight(), listToFiltr);

        }

        return listToFiltr;
    }

    /*
     * LISTENNERS
     */

    private void addLanguageListener() {
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void addRegisterButtonDisableCondition() {
        BooleanBinding disableBinding = Bindings.createBooleanBinding(() ->
                (((simLeftChoice.valueProperty().get()== null || simLeftChoice.valueProperty().get().equals(simNull))
                        || (simRightChoice.valueProperty().get() == null || simRightChoice.valueProperty().get().equals(simNull)))
                        && (childChoice.valueProperty().get() == null || childChoice.valueProperty().get().equals(simNull))
                ), simLeftChoice.valueProperty(), simRightChoice.valueProperty(), childChoice.valueProperty());

        this.addRelationConfirmButton.disableProperty().bind(disableBinding);
    }

    /*
     * GETTERS AND SETTERS
     */
    private void reloadElements() {
        // Nothing to do
    }

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

package genealogytree.client.desktop.controllers.implementation.scene;

import genealogytree.client.desktop.configuration.ContextGT;
import genealogytree.client.desktop.configuration.ScreenManager;
import genealogytree.client.desktop.configuration.enums.ImageFiles;
import genealogytree.client.desktop.configuration.messages.LogMessages;
import genealogytree.client.desktop.controllers.FXMLTab;
import genealogytree.client.desktop.controllers.implementation.custom.tree_elements.GTLeaf;
import genealogytree.client.desktop.domain.GTX_Member;
import genealogytree.client.desktop.domain.GTX_Relation;
import genealogytree.domain.enums.RelationType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 04/01/2017.
 */
@Log4j2
public class TabInfoRelationPaneController implements Initializable, FXMLTab {

    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();

    @FXML
    private AnchorPane infoRelationTab;

    @FXML
    private HBox contentHBox;

    @FXML
    private HBox childrenHBox;

    @FXML
    private AnchorPane simLeftView;

    @FXML
    private AnchorPane simRightView;

    @FXML
    private ImageView equalRelation;

    @FXML
    private ImageView typeRelationImgView;

    @FXML
    private JFXButton cancelRelationView;

    @FXML
    private JFXButton modifyRelation;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private Tab tab;
    private JFXTabPane tabPane;

    private ObjectProperty<GTX_Relation> relation;

    {

        relation = new SimpleObjectProperty<>();

    }


    /*
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(rb);

        simLeftView.getChildren().add(new GTLeaf());
        simRightView.getChildren().add(new GTLeaf());
        typeRelationImgView.setImage(new Image(ImageFiles.RELATION_NEUTRAL.toString()));
        equalRelation.setImage(new Image(ImageFiles.EQUAL_TRIANGLE.toString()));

        relation.addListener(getRelationListener());
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    @FXML
    public void closeTab() {
        this.tabPane.getTabs().remove(tab);
    }

    private ChangeListener<GTX_Relation> getRelationListener() {

        ChangeListener<GTX_Relation> listener = (observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillComponents(newValue);
                System.out.println("RELATION LOADED "+newValue.toString());
            }
        };
        return listener;
    }

    private void fillComponents(GTX_Relation relation) {
        simLeftView.getChildren().clear();

        GTLeaf leafSimLeft = new GTLeaf(relation.getSimLeft());
        if (relation.getSimLeft() == null) {
            leafSimLeft.setImage(ImageFiles.NO_NAME_FEMALE.toString());
        }
        simLeftView.getChildren().add(leafSimLeft);

        simRightView.getChildren().clear();
        GTLeaf leafSimRight = new GTLeaf(relation.getSimRight());
        if (relation.getSimRight() == null) {
            leafSimRight.setImage(ImageFiles.NO_NAME_MALE.toString());
        }
        simRightView.getChildren().add(leafSimRight);

        typeRelationImgView.setImage(new Image(getTypeImagePath(relation.getType())));

        for (GTX_Member m : relation.getChildren()) {
            childrenHBox.getChildren().add(new GTLeaf(m));
        }
    }

    private String getTypeImagePath(RelationType type) {
        String path = ImageFiles.RELATION_NEUTRAL.toString();
        switch (type) {
            case FIANCE:
                ImageFiles.RELATION_FIANCE.toString();
                break;
            case LOVE:
                ImageFiles.RELATION_LOVE.toString();
                break;
            case MARRIED:
                ImageFiles.RELATION_MARRIED.toString();
                break;
            default:
                ImageFiles.RELATION_NEUTRAL.toString();
        }
        return path;
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
        // TODO
    }

    /*
     * GETTERS
     */

    public Tab getTab() {
        return tab;
    }

    /*
    *   SETTERS
    */
    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public JFXTabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(JFXTabPane tabPane) {
        this.tabPane = tabPane;
    }

    public GTX_Relation getRelation() {
        return relation.get();
    }

    public void setRelation(GTX_Relation relation) {
        this.relation.set(relation);
    }

    public ObjectProperty<GTX_Relation> relationProperty() {
        return relation;
    }

    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        this.tab = tab;
    }

}

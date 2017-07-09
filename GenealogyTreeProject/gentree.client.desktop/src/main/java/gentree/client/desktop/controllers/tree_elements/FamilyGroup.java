package gentree.client.desktop.controllers.tree_elements;

import gentree.client.desktop.GenTreeRun;
import gentree.client.desktop.domain.Relation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * Created by Martyna SZYMKOWIAK on 05/07/2017.
 */
@Getter
@Setter
public class FamilyGroup extends AnchorPane {

    private static double OFFSET_FOND_DARK = 10.0;
    private static double OFFSET_FOND = 30.0;
    private static int MIN_OFFSET_HORIZONTAL = 300;
    private static int MIN_OFFSET_VERTICAL = 200;

    @FXML
    private Rectangle bodyNode;

    @FXML
    private Rectangle bodyNodeDark;

    @FXML
    private Rectangle labelFondNode;

    @FXML
    private Label nameNode;

    @FXML
    private StackPane content;

    @FXML
    private HBox contentHbox;

    private ObjectProperty<Relation> rootRelation;

    private int idNode;

    {
        rootRelation = new SimpleObjectProperty<>();
    }

    public FamilyGroup(Relation bean, int id) {
        this(bean);
        this.idNode = id;

    }

    public FamilyGroup(Relation bean) {
        super();
        fxmlLoading();
        propertyBinding();
        initAutoResizing();

        this.rootRelation.set(bean);
    }

    /**
     * Function for Loading FXML view
     */
    private void fxmlLoading() {
        FXMLLoader fxmlLoader = new FXMLLoader(GenTreeRun.class.getResource("/layout/tree_elements/family.group.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    /**
     * Function for Listen RootRelation changes.
     * <p>
     * This function has for role update NODE NAME  when  the name of racine child changes
     */
    private void propertyBinding() {

        rootRelation.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                nameNode.textProperty().unbind();
            }

            nameNode.textProperty().bind(rootRelation.getValue().getChildren().get(0).surnameProperty());
        });


    }

    /**
     * TODO
     */
    private void initListener() {

    }


    /**
     * Function guard for resizing elements
     */
    private void initAutoResizing() {
        bodyNodeDark.widthProperty().bind(this.widthProperty().subtract(OFFSET_FOND_DARK));
        bodyNodeDark.heightProperty().bind(this.heightProperty().subtract(OFFSET_FOND_DARK));
        bodyNode.widthProperty().bind(this.widthProperty().subtract(OFFSET_FOND));
        bodyNode.heightProperty().bind(this.heightProperty().subtract(OFFSET_FOND));
        content.prefWidthProperty().bind(contentHbox.widthProperty());

        contentHbox.setBorder(new Border(new BorderStroke(Color.GREEN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        content.setBorder(new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    /**
     * Getter for rootRelation property
     *
     * @return ObjectProperty<Relation>
     */
    public ObjectProperty<Relation> rootRelationProperty() {
        return rootRelation;
    }


    /**
     * Getter for rootRelation
     *
     * @return Relation
     */
    public Relation getRootRelation() {
        return rootRelation.get();
    }

    public void setRootRelation(Relation rootRelation) {
        this.rootRelation.set(rootRelation);
    }
}


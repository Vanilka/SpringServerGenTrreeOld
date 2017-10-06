package gentree.client.visualization.elements;

import gentree.client.desktop.domain.Relation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
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
    private AnchorPane PANEL_HEADER;

    @FXML
    private Label nameNode;

    @FXML
    private AnchorPane content;

    @FXML
    private HBox contentHbox;

    private ObjectProperty<Relation> rootRelation;

    private final int idNode;

    {
        rootRelation = new SimpleObjectProperty<>();
    }

    public FamilyGroup(Relation bean, int id) {
        super();
        this.idNode = id;
        fxmlLoading();
        propertyBinding();
        initAutoResizing();
        this.rootRelation.set(bean);

    }

    /**
     * Function for Loading FXML view
     */
    private void fxmlLoading() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/layout/elements/family.group.fxml"));
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

            nameNode.textProperty().bind(Bindings.concat(" (ID: "+idNode,")  ",rootRelation.getValue().getChildren().get(0).surnameProperty()));
        });


    }

    /**
     * TODO
     */
    private void initListener() {

    }



    /**
     * Function guard for resizing gentree.client.visualization.elements
     */
    private void initAutoResizing() {

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


    protected void initBorder(Color color, Pane node) {
        node.setBorder(new Border
                (new BorderStroke(color,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(6))));
    }



}


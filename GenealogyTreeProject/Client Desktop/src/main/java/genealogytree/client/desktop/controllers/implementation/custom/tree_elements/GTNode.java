/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import genealogytree.client.desktop.GenealogyTree;
import genealogytree.client.desktop.domain.GTX_Relation;
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
 *
 * Class that reprezent NODE in GenealogyTree
 * NODE is an container for relations childs, for specific root element
 *
 * @author Martyna SZYMKOWIAK
 *
 */
@Getter
@Setter
public class GTNode extends AnchorPane {

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

    private ObjectProperty<GTX_Relation> rootRelation;

    private int idNode;

    {
        rootRelation = new SimpleObjectProperty<>();
    }
    public GTNode(GTX_Relation bean, int id) {
        this(bean);
        this.idNode = id;

    }
    public GTNode(GTX_Relation bean) {
        super();
        fxmlLoading();
        propertyBinding();
        initAutoResizing();

        this.rootRelation.set(bean);
    }

    /**
     *  Function for Loading FXML view
     */
    private void fxmlLoading() {
        FXMLLoader fxmlLoader = new FXMLLoader(GenealogyTree.class.getResource("/layout/screen/custom-controls/GTNode.fxml"));
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
     *
     *  This function has for role update NODE NAME  when  the name of racine child changes
     */
    private void propertyBinding() {

        rootRelation.addListener((observable, oldValue, newValue) -> {
            if(oldValue != null) {
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
     *
     *  Getter for rootRelation property
     *
     * @return ObjectProperty<GTX_Relation>
     */
    public ObjectProperty<GTX_Relation> rootRelationProperty() {
        return rootRelation;
    }


    /**
     *
     * Getter for rootRelation
     *
     * @return GTX_Relation
     */
    public GTX_Relation getRootRelation() {
        return rootRelation.get();
    }

    public void setRootRelation(GTX_Relation rootRelation) {
        this.rootRelation.set(rootRelation);
    }
}


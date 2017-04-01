/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.client.desktop.controllers.implementation.custom;

import com.genealogytree.client.desktop.GenealogyTree;
import com.genealogytree.client.desktop.domain.GTX_Relation;
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
 * @author vanilka
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

    private GTX_Relation racine;

    public GTNode(GTX_Relation bean) {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(GenealogyTree.class.getResource("/layout/screen/custom-controls/GTNode.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }

        initAutoResizing();

        this.racine = bean;
    }


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
}


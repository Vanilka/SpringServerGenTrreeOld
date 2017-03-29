/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.client.desktop.controllers.implementation.custom;

import com.genealogytree.client.desktop.GenealogyTree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * @author vanilka
 */
public class GTNode extends AnchorPane {

    @FXML
    private AnchorPane imgNode;

    @FXML
    private Rectangle bodyNode;

    @FXML
    private Rectangle labelFondNode;

    @FXML
    private Label nameNode;

    @FXML
    private AnchorPane AnchorB;

    public GTNode() {
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

        AnchorB.getChildren().add(new GTLeaf());
    }
}

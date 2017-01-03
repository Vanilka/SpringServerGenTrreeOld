package com.genealogytree.application.custom_controls.controllers;

import com.genealogytree.GenealogyTree;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

/**
 * Created by vanilka on 03/01/2017.
 */
public class GTLeaf extends AnchorPane {

   public GTLeaf (){
       super();
        FXMLLoader fxmlLoader = new FXMLLoader(GenealogyTree.class.getResource("application/custom_controls/fxml/GTLeaf.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }

    }
}

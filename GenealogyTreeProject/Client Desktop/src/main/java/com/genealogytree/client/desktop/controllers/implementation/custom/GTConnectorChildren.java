package com.genealogytree.client.desktop.controllers.implementation.custom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Comparator;

/**
 * Created by Martyna SZYMKOWIAK on 30/03/2017.
 */
public class GTConnectorChildren extends Region {

    private static double LINE_VERTICAL_SIZE = 50.0;
    private static double LINE_WIDTH = 15;

    ObservableList<GTLeaf> members = FXCollections.observableArrayList();

    public GTConnectorChildren(GTLeaf... children) {

        for (GTLeaf child : children) {
            getChildren().add(initLineV(child));
        }

        getChildren().add(initLineH(getChildren()));

    }


    /*
        Draw vertical Line from Child to Connectior
     */
    public Line initLineV(GTLeaf leaf) {
        Line line = new Line();

        line.startXProperty().bind(leaf.layoutXProperty().add(leaf.getWidth() / 2));
        line.startYProperty().bind(leaf.layoutYProperty());

        line.endXProperty().bind(line.startXProperty());
        line.endYProperty().bind(line.startYProperty().subtract(LINE_VERTICAL_SIZE));

        line.setStroke(Color.BLACK);
        line.setStrokeWidth(LINE_WIDTH);


        return line;
    }

    /*
       Draw Horizontal line to connect all Children
     */
    public Line initLineH(ObservableList<Node> nodes) {
        ObservableList<Line> lines = FXCollections.observableArrayList();

        for (Node n : nodes) {
            if (n instanceof Line) {
                lines.add((Line) n);
            }
        }

        Line line = new Line();

        Line max = lines.stream().max(Comparator.comparingDouble(Line::getStartX)).get();
        Line min = lines.stream().min(Comparator.comparingDouble(Line::getStartX)).get();

        line.startXProperty().bind(min.startXProperty());
        line.startYProperty().bind(min.endYProperty());

        line.endXProperty().bind(max.startXProperty());
        line.endYProperty().bind(max.endYProperty());

        line.setStroke(Color.BLACK);
        line.setStrokeWidth(LINE_WIDTH);

        return line;
    }

}

package com.genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/**
 * Created by Martyna SZYMKOWIAK on 2017-05-19.
 */
public class GTPanelCommon extends BorderPane {

    protected Bounds getRelativeBounds(Node node) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return this.sceneToLocal(nodeBoundsInScene);
    }

    protected Point2D getTopPoint(Bounds b) {
        if (b != null) {
        }

        return b == null ? null : new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY());
    }

    protected Point2D getBottomPoint(Bounds b) {

        if (b != null) {
            System.out.println(b);
        }
        return b == null ? null : new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY() + b.getHeight());
    }

    protected Line drawConnector(Point2D p1, Point2D p2) {
        Line connector = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        connector.setStroke(Color.BLACK);
        connector.setStrokeWidth(10);
        connector.setStrokeLineJoin(StrokeLineJoin.ROUND);
        connector.setStrokeLineCap(StrokeLineCap.ROUND);
        return connector;
    }

}


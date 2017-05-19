package com.genealogytree.client.desktop.controllers.implementation.custom.connectors;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 31/03/2017.
 */
@Getter
@Setter
public class LeafConnectorChild extends LeafConnector {

    public LeafConnectorChild(Node parent) {
        super(parent);
    }



    /*
    Computing connector point :  top-center
    */
    protected Point2D getConnectPointChild(Bounds b) {
        return new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY());
    }

}

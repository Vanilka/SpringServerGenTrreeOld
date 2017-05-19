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
public class LeafConnector {

    protected Node parent;


    public LeafConnector(Node parent) {
        this.parent = parent;
    }



    /*
        Return object representing Node in Parent coordonates
     */
    protected Bounds boundTo(Node child, Node parent) {
        Bounds nodeBoundsInScene = child.localToScene(child.getBoundsInLocal());
        return parent.sceneToLocal(nodeBoundsInScene);
    }
}

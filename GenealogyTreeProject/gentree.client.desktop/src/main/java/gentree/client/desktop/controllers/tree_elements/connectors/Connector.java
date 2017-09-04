package gentree.client.desktop.controllers.tree_elements.connectors;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

/**
 * Created by Martyna SZYMKOWIAK on 29/08/2017.
 */
public abstract class Connector {

    protected static Point2D getBottomPoint(Bounds b) {
        return b == null ? null : new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY() + b.getHeight());
    }

    protected static Point2D getTopPoint(Bounds b) {
        return b == null ? null : new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY());
    }

    protected static Point2D getLeftPoint(Bounds b) {
        return b == null ? null : new Point2D(b.getMinX(), b.getMinY() + b.getHeight()/2);
    }

    protected static Point2D getRightPoint(Bounds b) {
        return b == null ? null : new Point2D(b.getMaxX(), b.getMinY() + b.getHeight()/2);
    }
}
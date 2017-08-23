package gentree.client.desktop.controllers.tree_elements.connectors;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import lombok.Getter;

/**
 * Created by Martyna SZYMKOWIAK on 23/08/2017.
 */
public class LineConnector {


    protected ObjectProperty<Line> line;

    public LineConnector() {
        line = new SimpleObjectProperty<>(new Line());
        initLineProperties(line.get());
    }

    protected Point2D getBottomPoint(Bounds b) {

        if (b != null) {
            System.out.println(b);
        }
        return b == null ? null : new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY() + b.getHeight());
    }


    protected Point2D getTopPoint(Bounds b) {
        if (b != null) {
        }

        return b == null ? null : new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY());
    }

    protected void initLineProperties(Line line) {
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(10);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
    }

    public Line getLine() {
        return line.get();
    }

    public ReadOnlyProperty<Line> lineProperty() {
        return line;
    }

}

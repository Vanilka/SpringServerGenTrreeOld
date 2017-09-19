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
 *
 * Class to manage Line Connectors.
 *
 */
public class LineConnector extends Connector {


    protected ObjectProperty<Line> line;

    public LineConnector() {
        line = new SimpleObjectProperty<>(new Line());
        initLineProperties(line.get());
    }


    protected void initLineProperties(Line line) {
        initLineProperties(line, Color.BLACK, 10.0);
    }

    protected void initLineProperties(Line line, Color color, Double width) {
        line.setStroke(color);
        line.setStrokeWidth(width);
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

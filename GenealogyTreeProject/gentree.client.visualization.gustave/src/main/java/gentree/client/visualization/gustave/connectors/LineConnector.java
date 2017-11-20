package gentree.client.visualization.gustave.connectors;

import gentree.client.visualization.elements.configuration.AutoCleanable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/**
 * Created by Martyna SZYMKOWIAK on 23/08/2017.
 * <p>
 * Class to manage Line Connectors.
 */
public class LineConnector extends Connector implements AutoCleanable {


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

    @Override
    public void clean() {
        line.setValue(null);
    }
}

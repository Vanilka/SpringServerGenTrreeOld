package genealogytree.client.desktop.controllers.implementation.custom.connectors;

import genealogytree.client.desktop.controllers.implementation.custom.tree_elements.GTLeaf;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by Martyna SZYMKOWIAK on 30/03/2017.
 */
public class GTConnectorSpouse extends Region {

    private GTLeaf left;
    private GTLeaf right;
    private Line line;

    public GTConnectorSpouse(GTLeaf left, GTLeaf right) {
        super();
        this.left = left.getLayoutX() < right.getLayoutX() ? left : right;
        this.right = left.getLayoutX() < right.getLayoutX() ? right : left;
        line = new Line();
        getChildren().addAll(line);
        autoresize();
    }

    public void autoresize() {
        line.startXProperty().bind(left.layoutXProperty().add(left.getWidth()));
        line.startYProperty().bind(left.layoutYProperty().add(left.getHeight()/2));

        line.endXProperty().bind(right.layoutXProperty());
        line.endYProperty().bind(right.layoutYProperty().add(right.getHeight()/2));

        line.setStroke(Color.BLACK);
        line.setStrokeWidth(6);

    }

}

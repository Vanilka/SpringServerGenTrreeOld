package gentree.client.desktop.controllers.tree_elements.panels;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
@Getter
@Setter
public abstract class SubBorderPane extends BorderPane {

    private SubBorderPane parentPane;

    protected void initBorder(Color color, Pane node) {
        node.setBorder(new Border
                (new BorderStroke(color,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        BorderWidths.DEFAULT)));
    }

}

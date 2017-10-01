package gentree.client.desktop.controllers.tree_elements.panels;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;

/**
 * Created by Martyna SZYMKOWIAK on 04/09/2017.
 */
public abstract class SubRelationPane extends SubBorderPane {

    @Getter
    protected final HBox childrenBox;

    SubRelationPane() {
        childrenBox = new HBox();

        setOnMouseClicked(event -> {
            System.out.println("clicket on me :" + this.toString());
        });
    }


}

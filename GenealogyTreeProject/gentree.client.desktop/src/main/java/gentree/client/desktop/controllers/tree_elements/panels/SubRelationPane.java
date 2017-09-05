package gentree.client.desktop.controllers.tree_elements.panels;

import javafx.scene.layout.HBox;
import lombok.Getter;

/**
 * Created by Martyna SZYMKOWIAK on 04/09/2017.
 */
public abstract class SubRelationPane extends SubBorderPane {

    @Getter
    protected final HBox childrenBox;

    SubRelationPane() {
        childrenBox = new HBox();
    }
}

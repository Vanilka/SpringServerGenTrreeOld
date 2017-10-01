package gentree.client.desktop.controllers.tree_elements;

import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.desktop.service.ScreenManager;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 30/07/2017.
 */
@Getter
@Setter
public class RelationTypeElement extends RelationTypeCard {


    ScreenManager sm = ScreenManager.INSTANCE;

    {
        init();
    }

    public RelationTypeElement() {
        super();
    }

    public RelationTypeElement(Relation relation) {
        super(relation);
    }

    private void init() {
        dropShadow = new DropShadow();
        initShadow();

        this.setOnMouseEntered(t -> circle.setEffect(dropShadow));

        this.setOnMouseExited(t -> circle.setEffect(null));

        this.setOnContextMenuRequested(event -> sm.showRelationContextMenu(returnThis(), event));

    }

    private void initShadow() {
        dropShadow.setRadius(10.0f);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        dropShadow.setColor(Color.color(0.7, 0.7, 0.2));
    }

    private RelationTypeElement returnThis() {
        return this;
    }

}

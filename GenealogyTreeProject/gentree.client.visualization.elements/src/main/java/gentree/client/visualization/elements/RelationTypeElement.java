package gentree.client.visualization.elements;

import gentree.client.desktop.domain.Relation;
import gentree.client.visualization.elements.configuration.ContextProvider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    private static final ObjectProperty<ContextProvider> CONTEXT_PROVIDER__PROPERTY = new SimpleObjectProperty<>();

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


        if(CONTEXT_PROVIDER__PROPERTY.get() != null) {
            this.setOnContextMenuRequested(event -> CONTEXT_PROVIDER__PROPERTY.get().showRelationContextMenu(returnThis(), event));
        }

        CONTEXT_PROVIDER__PROPERTY.addListener((observable, oldValue, newValue) -> {
            if(newValue == null) {
                this.setOnContextMenuRequested(null);
            } else {
                this.setOnContextMenuRequested(event -> newValue.showRelationContextMenu(returnThis(), event));
            }
        });
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


    public static void setContextProviderProperty(ContextProvider contextProviderProperty) {
        CONTEXT_PROVIDER__PROPERTY.set(contextProviderProperty);
    }
}

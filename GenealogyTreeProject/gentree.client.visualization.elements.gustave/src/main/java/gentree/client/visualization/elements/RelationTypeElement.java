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

    private static final ObjectProperty<ContextProvider> CONTEXT_PROVIDER_PROPERTY = new SimpleObjectProperty<>();

    {
        init();
    }

    public RelationTypeElement() {
        super();
    }

    public RelationTypeElement(Relation relation) {
        super(relation);
    }

    public static void setContextProviderProperty(ContextProvider contextProviderProperty) {
        CONTEXT_PROVIDER_PROPERTY.set(contextProviderProperty);
    }

    private void init() {
        dropShadow = new DropShadow();
        initShadow();
        this.setOnMouseEntered(t -> circle.setEffect(dropShadow));
        this.setOnMouseExited(t -> circle.setEffect(null));

        this.setOnMouseClicked(event -> {
            System.out.println("Relation " + this.getRelation().get() + " -> Has reference   " + this.getRelation().get().getReferenceNumber());
        });


        if (CONTEXT_PROVIDER_PROPERTY.get() != null) {
            this.setOnContextMenuRequested(event -> CONTEXT_PROVIDER_PROPERTY.get().showRelationContextMenu(returnThis(), event));
            this.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && relation.get() != null) {
                    CONTEXT_PROVIDER_PROPERTY.get().showInfoRelation(relation.get());
                }
            });

        }


        CONTEXT_PROVIDER_PROPERTY.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                this.setOnContextMenuRequested(null);
                this.setOnMouseClicked(null);
            } else {
                this.setOnContextMenuRequested(event -> newValue.showRelationContextMenu(returnThis(), event));
                this.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && relation.get() != null) {
                        newValue.showInfoRelation(relation.get());
                    }
                });
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


}

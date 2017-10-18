package gentree.client.visualization.elements;

import gentree.client.desktop.domain.Member;
import gentree.client.visualization.elements.configuration.ContextProvider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class FamilyMember extends FamilyMemberCard {

    private static final ObjectProperty<ContextProvider> CONTEXT_PROVIDER__PROPERTY = new SimpleObjectProperty<>();

    public FamilyMember(Member member) {
        super(member);
        initialize();
    }

    public FamilyMember() {
        this(null);
    }

    public static void setContextProviderProperty(ContextProvider contextProviderProperty) {
        CONTEXT_PROVIDER__PROPERTY.set(contextProviderProperty);
    }

    private void initialize() {
        this.setOnMouseEntered(t -> {
            rectangleFond.setStroke(Color.valueOf("#64bf37"));
        });

        this.setOnMouseExited(t -> {

            rectangleFond.setStroke(Color.TRANSPARENT);
        });

        if (CONTEXT_PROVIDER__PROPERTY.get() != null) {
            this.setOnContextMenuRequested(event -> CONTEXT_PROVIDER__PROPERTY.get().showSimContextMenu(returnThis(), event));
        }


        CONTEXT_PROVIDER__PROPERTY.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                this.setOnContextMenuRequested(null);
            } else {
                this.setOnContextMenuRequested(event -> newValue.showSimContextMenu(returnThis(), event));
            }
        });
    }

    private FamilyMember returnThis() {
        return this;
    }
}

package gentree.client.visualization.elements;

import gentree.client.desktop.domain.Member;
import gentree.client.visualization.elements.configuration.AutoCleanable;
import gentree.client.visualization.elements.configuration.ContextProvider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class FamilyMember extends FamilyMemberCard implements AutoCleanable {

    private final static String pathfxml = "/layout/elements/family.member.fxml";
    private static final ObjectProperty<ContextProvider> CONTEXT_PROVIDER_PROPERTY = new SimpleObjectProperty<>();
    private ChangeListener<? super ContextProvider> contextListener = this::contextChanged;

    public FamilyMember(Member member) {
        super(member, pathfxml);
        initialize();
    }

    public FamilyMember() {
        this(null);
    }

    public static void setContextProviderProperty(ContextProvider contextProviderProperty) {
        CONTEXT_PROVIDER_PROPERTY.set(contextProviderProperty);
    }

    private void initialize() {
        this.setOnMouseEntered(t -> {
            rectangleFond.setStroke(Color.valueOf("#64bf37"));
        });

        this.setOnMouseExited(t -> {

            rectangleFond.setStroke(Color.TRANSPARENT);
        });

        if (CONTEXT_PROVIDER_PROPERTY.get() != null) {
            this.setOnContextMenuRequested(event -> CONTEXT_PROVIDER_PROPERTY.get().showSimContextMenu(returnThis(), event));
            this.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && getMember() != null) {
                    CONTEXT_PROVIDER_PROPERTY.get().showInfoSim(getMember());
                }
            });
        }

        CONTEXT_PROVIDER_PROPERTY.addListener(contextListener);

    }

    @Override
    public void clean() {
        System.out.println("CLEAN Family Member" +getMember());
        super.clean();
        CONTEXT_PROVIDER_PROPERTY.removeListener(contextListener);
        this.setOnContextMenuRequested(null);
        this.setOnMouseClicked(null);
        setMember(null);
        contextListener = null;

    }


    private FamilyMember returnThis() {
        return this;
    }

    private void contextChanged(ObservableValue<? extends ContextProvider> observable, ContextProvider oldValue, ContextProvider newValue) {
        if (newValue == null) {
            this.setOnContextMenuRequested(null);
            this.setOnMouseClicked(null);
        } else {
            this.setOnContextMenuRequested(event -> newValue.showSimContextMenu(returnThis(), event));
            this.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && getMember() != null) {
                    newValue.showInfoSim(getMember());
                }
            });
        }
    }
}

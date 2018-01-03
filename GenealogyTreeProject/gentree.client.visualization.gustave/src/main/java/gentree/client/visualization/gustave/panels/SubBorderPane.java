package gentree.client.visualization.gustave.panels;

import gentree.client.visualization.elements.FamilyGroup;
import gentree.client.visualization.elements.configuration.AutoCleanable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
@Getter
@Setter
public abstract class SubBorderPane extends BorderPane implements AutoCleanable {
    /**
     * Relation Height =
     */
    protected final static double RELATION_HEIGHT = 280;

    private final ObjectProperty<SubBorderPane> parentPane;
    private final ObjectProperty<FamilyGroup> familyGroup;
    private ChangeListener<? super SubBorderPane> parentPaneListener = this::parentPaneChanged;


    {
        parentPane = new SimpleObjectProperty<>();
        familyGroup = new SimpleObjectProperty<>();
    }

    public SubBorderPane() {
        initParentListeners();
    }

    protected void initBorder(Color color, Pane node) {
        node.setBorder(new Border
                (new BorderStroke(color,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        BorderWidths.DEFAULT)));
    }


    protected Bounds getRelativeBounds(Node node, Node relativeTo) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return relativeTo.sceneToLocal(nodeBoundsInScene);
    }

    private void initParentListeners() {
        parentPane.addListener(parentPaneListener);
    }

    @Override
    public void clean() {
        parentPane.removeListener(parentPaneListener);
        familyGroup.unbind();
        parentPane.setValue(null);
        familyGroup.setValue(null);
        parentPaneListener = null;
    }


    /*
     * GETTERS AND SETTERS
     */

    public SubBorderPane getParentPane() {
        return parentPane.get();
    }

    public void setParentPane(SubBorderPane parentPane) {
        this.parentPane.set(parentPane);
    }

    public ObjectProperty<SubBorderPane> parentPaneProperty() {
        return parentPane;
    }

    public FamilyGroup getFamilyGroup() {
        return familyGroup.get();
    }

    public void setFamilyGroup(FamilyGroup familyGroup) {
        this.familyGroup.set(familyGroup);
    }

    public ObjectProperty<FamilyGroup> familyGroupProperty() {
        return familyGroup;
    }

    private void parentPaneChanged(ObservableValue<? extends SubBorderPane> observable, SubBorderPane oldValue, SubBorderPane newValue) {
        if (oldValue != null) {
            familyGroup.unbind();
            familyGroup.setValue(null);
        }

        if (newValue == null) {
            familyGroup.unbind();
            familyGroup.setValue(null);
        } else {
            familyGroup.bind(newValue.familyGroupProperty());
        }
    }
}

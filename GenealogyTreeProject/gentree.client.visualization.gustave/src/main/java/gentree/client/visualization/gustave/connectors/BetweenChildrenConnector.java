package gentree.client.visualization.gustave.connectors;

import gentree.client.visualization.gustave.panels.PanelChild;
import gentree.client.visualization.gustave.panels.SubRelationPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.shape.Line;

import java.util.Comparator;


/**
 * Created by Martyna SZYMKOWIAK on 04/10/2017.
 */
public class BetweenChildrenConnector extends LineConnector {

    private final ReadOnlyBooleanWrapper isEmpty = new ReadOnlyBooleanWrapper();
    /*
     *  Parent pane for this Connector
     */
    private SubRelationPane subBorderPane;
    private ObservableList<ChildConnector> list = FXCollections.observableArrayList();
    private ChangeListener<? super Bounds> boundsListener = this::boundsChanged;
    private ListChangeListener<? super ChildConnector> changeListListener = this::childListChange;
    // private ObjectProperty<ChildConnector> start = new SimpleObjectProperty<>();
    //  private ObjectProperty<ChildConnector> end = new SimpleObjectProperty<>();

    /**
     * SubRelationPane element is the parent for this Connector
     *
     * @param subBorderPane
     */
    public BetweenChildrenConnector(SubRelationPane subBorderPane) {
        super();
        isEmpty.bind(Bindings.isEmpty(list));
        this.subBorderPane = subBorderPane;
        this.subBorderPane.getChildren().add(1, getLine());
        initLineProperties(getLine());
        initListeners();
    }

    private void initListeners() {

        // initPointListener();

        list.addListener(changeListListener);
        getLine().visibleProperty().bind(Bindings.createBooleanBinding(() -> list.size() > 0, list));


    }

    private void cleanListeners() {
        list.removeListener(changeListListener);
        isEmpty.unbind();
        getLine().visibleProperty().unbind();
        list.forEach(ChildConnector::clean);
        list.clear();

    }

    @Override
    public void clean() {
        cleanListeners();
        super.clean();

        subBorderPane = null;

        changeListListener = null;
        boundsListener = null;


    }

    private void drawLine() {
        if (list.size() > 0) {
            Line startLine = list.stream().min(Comparator.comparingDouble(value -> value.getLine().getStartX())).get().getLine();
            Line endLine = list.stream().max(Comparator.comparingDouble(value -> value.getLine().getStartX())).get().getLine();
            setLineCoordinates(getLine(), startLine.getEndX(), startLine.getEndY(), endLine.getEndX(), endLine.getEndY());
        }
    }

    private void initPointListener() {

        //  start.bind(Bindings.createObjectBinding(() -> list.stream().min(Comparator.comparingDouble(value -> value.getLine().getStartX())).orElse(null), list));
        //  end.bind(Bindings.createObjectBinding(() -> list.stream().max(Comparator.comparingDouble(value -> value.getLine().getStartX())).orElse(null), list));
    }

    public void addPanelChild(PanelChild child) {
        list.add(new ChildConnector(child, subBorderPane));
    }

    public void removePanelChild(PanelChild child) {
        list.removeIf(p -> p.getPanelChild().equals(child));
    }

    public ReadOnlyBooleanProperty isListEmpty() {
        return isEmpty.getReadOnlyProperty();
    }


    private void childListChange(ListChangeListener.Change<? extends ChildConnector> c) {
        while (c.next()) {
            if (c.wasAdded()) {
                for (ChildConnector childConnector : c.getAddedSubList()) {
                    childConnector.getLine().boundsInParentProperty().addListener(boundsListener);
                    childConnector.getLine().boundsInLocalProperty().addListener(boundsListener);
                }
            }

            if (c.wasRemoved()) {
                c.getRemoved().forEach(element -> {
                    element.removeLine();
                    element.getLine().boundsInParentProperty().removeListener(boundsListener);
                    element.getLine().boundsInLocalProperty().removeListener(boundsListener);
                    // element.clean();
                });
            }
        }
    }

    private void boundsChanged(ObservableValue<? extends Bounds> obs, Bounds oldValue, Bounds newValue) {
        drawLine();
    }

}

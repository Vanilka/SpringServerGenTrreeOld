package gentree.client.visualization.gustave.connectors;

import gentree.client.visualization.gustave.panels.PanelChild;
import gentree.client.visualization.gustave.panels.SubRelationPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
    private ObjectProperty<ChildConnector> start = new SimpleObjectProperty<>();
    private ObjectProperty<ChildConnector> end = new SimpleObjectProperty<>();

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

        initPointListener();

        list.addListener((ListChangeListener<ChildConnector>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (ChildConnector childConnector : c.getAddedSubList()) {

                        childConnector.getLine().boundsInParentProperty().addListener((obs, oldValue, newValue) -> {
                            drawLine();
                        });

                        childConnector.getLine().boundsInLocalProperty().addListener((obs, oldValue, newValue) -> {
                            drawLine();
                        });
                    }
                }


                if (c.wasRemoved()) {
                    c.getRemoved().forEach(ChildConnector::removeLine);
                }
            }
        });

        getLine().visibleProperty().bind(Bindings.createBooleanBinding(() -> list.size() > 0, list));


    }

    private void drawLine() {
        if (list.size() > 0) {
            if (start.get() != null && start.get() != null) {

                Line startLine = start.get().getLine();
                Line endLine = end.get().getLine();

                setLineCoordinates(getLine(), startLine.getEndX(), startLine.getEndY(), endLine.getEndX(), endLine.getEndY());
            }
        }
    }

    private void initPointListener() {

        start.bind(Bindings.createObjectBinding(() -> list.stream().min(Comparator.comparingDouble(value -> value.getLine().getStartX())).orElse(null), list));

        end.bind(Bindings.createObjectBinding(() -> list.stream().max(Comparator.comparingDouble(value -> value.getLine().getStartX())).orElse(null), list));


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


}

package gentree.client.visualization.gustave.configuration;

import gentree.client.visualization.gustave.connectors.ChildConnector;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

public class ChildrenConnectorsGuard implements Observer {

    private final ObservableList<ChildConnector> observableList;

    @Getter
    private final ObjectProperty<ChildConnector> firstChild = new SimpleObjectProperty<>();

    @Getter
    private final ObjectProperty<ChildConnector> lastChild = new SimpleObjectProperty<>();

    public ChildrenConnectorsGuard(ObservableList<ChildConnector> list) {
        this.observableList = list;

        list.addListener((ListChangeListener<? super ChildConnector>)  c -> {
            while (c.next()) {
                populateFirstAndLastChild();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("UPDATE DETECTED");
        if(o instanceof ChildConnector) {
            System.out.println("list");
            System.out.println("LIST" +observableList);
            populateFirstAndLastChild();
        }
    }
    private void populateFirstAndLastChild() {
        if (!observableList.isEmpty()) {
            firstChild.setValue(observableList.stream().min(Comparator.comparingDouble(value -> value.getLine().getStartX())).get());
            lastChild.setValue(observableList.stream().max(Comparator.comparingDouble(value -> value.getLine().getStartX())).get());
        } else {
            firstChild.setValue(null);
            lastChild.setValue(null);
        }
    }

    public void addObserverTo(Observable observable) {
        observable.addObserver(this);
    }

}

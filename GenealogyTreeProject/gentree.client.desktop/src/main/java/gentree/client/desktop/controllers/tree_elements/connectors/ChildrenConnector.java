package gentree.client.desktop.controllers.tree_elements.connectors;

import gentree.client.desktop.controllers.tree_elements.panels.PanelChild;
import gentree.client.desktop.controllers.tree_elements.panels.SubBorderPane;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Created by Martyna SZYMKOWIAK on 20/08/2017.
 */
public class ChildrenConnector {

    private SubBorderPane subBorderPane;

    private ObservableList<ChildConnector> list = FXCollections.observableArrayList();


    public ChildrenConnector(SubBorderPane subBorderPane) {
        this.subBorderPane = subBorderPane;
        initListeners();
    }

    private void initListeners() {
        list.addListener((ListChangeListener<ChildConnector>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {


                } else if (c.wasRemoved()) {


                }
            }
        });

    }

    public void addPanelChild(PanelChild child) {
        list.add(new ChildConnector(child, subBorderPane));
    }

    public void removePanelChild(PanelChild child) {
        list.removeIf(p -> p.getPanelChild().equals(child));
    }


}

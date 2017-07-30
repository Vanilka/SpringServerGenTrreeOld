package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.controllers.tree_elements.RelationTypeElement;
import gentree.client.desktop.domain.Member;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class PanelRelationCurrent extends SubBorderPane {

    private final AnchorPane relation;
    private final HBox childrenBox;


    private final ObjectProperty<RelationTypeElement> relationTypeElement;
    private final ObjectProperty<FamilyMember> spouse;
    private final ObservableList<FamilyMember> children;

    {
        relation = new AnchorPane();
        childrenBox = new HBox();

        relationTypeElement = new SimpleObjectProperty<>();
        spouse = new SimpleObjectProperty<>();
        children = FXCollections.observableArrayList();
    }

    PanelRelationCurrent() {
        autoresize();
        initPanes();
        initListeners();
    }


    private void initPanes() {
        this.setCenter(childrenBox);
        this.setTop(relation);

    }

    private void autoresize() {
        childrenBox.maxHeight(Double.MAX_VALUE);
        childrenBox.maxWidth(Double.MAX_VALUE);
    }

    /*
        Listeners
     */

    private void initListeners() {
        initSpouseListener();
        initChildrenListener();
    }


    private void initSpouseListener() {
        spouse.addListener((observable, oldValue, newValue) -> {
            relation.getChildren().removeAll();
            if(newValue != null) {
              relation.getChildren().addAll(relationTypeElement.get(), spouse.get());
            }
        });
    }

    private void initChildrenListener() {
        children.addListener((ListChangeListener<FamilyMember>) c ->  {
            while (c.next()) {
                if(c.wasAdded()) {
                    childrenBox.getChildren().addAll(c.getAddedSubList());
                } else if (c.wasRemoved()) {
                    childrenBox.getChildren().removeAll(c.getRemoved());
                }
            }

        });
    }


}

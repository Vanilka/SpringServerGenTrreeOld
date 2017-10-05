package gentree.client.visualization.vanilla.element;

import gentree.client.desktop.domain.Relation;
import gentree.client.visualization.elements.RelationTypeElement;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class VRelationType extends RelationTypeElement {

    private final ObjectProperty<VMember> spouse = new SimpleObjectProperty<>();
    private final ObjectProperty<VMember> mainSim = new SimpleObjectProperty<>();
    private final ObservableList<VMember> relationChildren = FXCollections.observableArrayList();

    DoubleProperty maxRight, maxLeft;

    public VRelationType() {
        super();
    }

    public VRelationType(Relation relation) {
        super(relation);
    }

    public VMember getSpouse() {
        return spouse.get();
    }

    public void setSpouse(VMember spouse) {
        checkParent(spouse);
        this.spouse.set(spouse);
    }

    public ObjectProperty<VMember> spouseProperty() {
        return spouse;
    }

    public VMember getMainSim() {
        return mainSim.get();
    }

    public void setMainSim(VMember mainSim) {
        checkParent(mainSim);
        this.mainSim.set(mainSim);
    }

    public ObjectProperty<VMember> mainSimProperty() {
        return mainSim;
    }

    public ObservableList<VMember> getRelationChildren() {
        return relationChildren;
    }

    public void addChild(VMember child) {
        relationChildren.add(child);
    }

    public void checkParent(Node node) {
        if (!node.getParent().equals(this.getParent())) {
            System.out.println("Added element should have same parent !!!");
        }
    }

}

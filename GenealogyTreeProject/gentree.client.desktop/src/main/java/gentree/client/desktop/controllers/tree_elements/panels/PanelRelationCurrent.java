package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.controllers.tree_elements.RelationTypeElement;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.enums.RelationType;
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
public class PanelRelationCurrent extends SubBorderPane implements RelationPane{

    private final AnchorPane relation;
    private final HBox childrenBox;

    private final FamilyMember spouseCard;
    private final RelationTypeElement relationTypeElement;


    private final ObjectProperty<RelationType> relationType;
    private final ObjectProperty<Member> spouse;
    private final ObservableList<PanelChild> children;

    {
        relation = new AnchorPane();
        childrenBox = new HBox();
        spouseCard = new FamilyMember();
        relationTypeElement = new RelationTypeElement();
        relationType = new SimpleObjectProperty<>();
        spouse = new SimpleObjectProperty<>();
        children = FXCollections.observableArrayList();
    }

    public PanelRelationCurrent() {
        this(null, RelationType.NEUTRAL, null);
    }

    public PanelRelationCurrent(Member spouse, RelationType type) {
        this(spouse, type, null);
    }

    public PanelRelationCurrent(Member spouse, RelationType type, SubBorderPane parent) {
        initPanes();
        initListeners();
        this.spouse.setValue(spouse);
        this.relationType.setValue(type);
    }


    private void initPanes() {
        this.setCenter(childrenBox);
        this.setTop(relation);

        spouseCard.setLayoutX(200);

    }

    /*
        Listeners
     */

    private void initListeners() {
        initSpouseListener();
        initChildrenListener();
        initRelationTypeListener();
    }


    private void initSpouseListener() {
        spouse.addListener((observable, oldValue, newValue) -> {
            relation.getChildren().removeAll();
            if (newValue != null) {
                spouseCard.setMember(newValue);
                relation.getChildren().addAll(relationTypeElement, spouseCard);
            }
        });
    }

    private void initRelationTypeListener() {
        relationType.addListener((observable, oldValue, newValue) -> {
            relationTypeElement.setType(newValue == null ? RelationType.NEUTRAL : newValue);
        });
    }

    private void initChildrenListener() {
        children.addListener((ListChangeListener<PanelChild>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    childrenBox.getChildren().addAll(c.getAddedSubList());
                } else if (c.wasRemoved()) {
                    childrenBox.getChildren().removeAll(c.getRemoved());
                }
            }

        });
    }

    @Override
    public void addChild(PanelChild child) {
        children.add(child);
        child.setParentPane(this);
    }
}

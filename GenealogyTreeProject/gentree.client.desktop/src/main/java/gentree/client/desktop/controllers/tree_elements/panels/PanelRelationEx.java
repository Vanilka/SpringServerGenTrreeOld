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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class PanelRelationEx extends SubBorderPane implements RelationPane {


    private final HBox relation;
    private final HBox childrenBox;

    private final FamilyMember spouseCard;
    private final RelationTypeElement relationTypeElement;

    private final ObjectProperty<RelationType> relationType;
    private final ObjectProperty<Member> spouse;
    private final ObservableList<PanelChild> children;

    {
        relation = new HBox();
        childrenBox = new HBox();
        spouseCard = new FamilyMember();
        relationTypeElement = new RelationTypeElement();
        relationType = new SimpleObjectProperty<>();
        spouse = new SimpleObjectProperty<>();
        children = FXCollections.observableArrayList();
    }

    public PanelRelationEx() {
        this(null, RelationType.NEUTRAL, null);
    }

    public PanelRelationEx(Member spouse, RelationType type) {
        this(spouse, type, null);
    }

    public PanelRelationEx(Member spouse, RelationType type, SubBorderPane parent) {
        initPanes();
        initListeners();
        this.spouse.setValue(spouse);
        this.relationType.setValue(type);
        this.initBorder(Color.BROWN, this);
        this.setPadding(new Insets(10, 10, 10, 10));

    }


    private void initPanes() {
        initHbox();
        this.setCenter(childrenBox);
        this.setTop(relation);
        relationTypeElement.setLayoutX(300);
        this.setPadding(new Insets(10, 10, 10, 10));
        setMargin(relation, new Insets(0, 0, 50, 0));

    }

    private void initHbox() {
        childrenBox.setSpacing(10);
        relation.setSpacing(40);
        relation.setAlignment(Pos.CENTER);
    }


    @Override
    public void addChild(PanelChild child) {

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
                relation.getChildren().addAll(spouseCard, relationTypeElement);
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

                    c.getAddedSubList().forEach(child -> {

                    });

                } else if (c.wasRemoved()) {
                    childrenBox.getChildren().removeAll(c.getRemoved());
                }
            }

        });
    }
}

package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.domain.Member;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 * <p>
 * Panel representing Sim itself and his own children
 * Top: Leaf
 * Center: List of children
 */
@Getter
@Setter
public class PanelSingle extends SubBorderPane implements RelationPane{

    private final FamilyMember member;

    private final HBox hboxChildren;

    private final ObservableList<PanelChild> panelChildrenList;

    /*
        Initialization
     */

    {
        member = new FamilyMember();
        hboxChildren = new HBox();
        panelChildrenList = FXCollections.observableArrayList();

    }

    public PanelSingle() {
        this(null, null);
    }

    public PanelSingle(Member m) {
        this(m, null);
    }

    public PanelSingle(Member m, SubBorderPane parent) {
        init();
        member.setMember(m);
        setParentPane(parent);

        this.initBorder(Color.DARKBLUE, this);
        this.setPadding(new Insets(10, 10, 10, 10));
    }


    /*
        Init Panes
     */

    private void init() {
        initListeners();
        initHbox();
        setTop(member);
        setCenter(hboxChildren);
    }

    private void  initHbox() {
        hboxChildren.setSpacing(10);
    }

    private void initListeners() {
        panelChildrenList.addListener((ListChangeListener<PanelChild>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    hboxChildren.getChildren().addAll(c.getAddedSubList());
                }

                if (c.wasRemoved()) {
                    hboxChildren.getChildren().removeAll(c.getRemoved());
                }
            }
        });
    }

    public void addMember(Member m) {
        if (m != null) {
            member.setMember(m);
        }
    }


    @Override
    public void addChild(PanelChild child) {
        panelChildrenList.add(child);
        child.setParentPane(this);
    }
}

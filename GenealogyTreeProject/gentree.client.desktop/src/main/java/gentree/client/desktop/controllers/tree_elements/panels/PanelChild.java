package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.domain.Member;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 * <p>
 * Panel Child extends BorderPane
 * Left: Hbox of PanelsEx
 * Center: Panel Single
 * Right: Panel Current
 */
@Getter
@Setter
public class PanelChild extends SubBorderPane {


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final AnchorPane panelSinglePane;


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final AnchorPane panelRelationCurrentPane;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final HBox panelRelationExPane;


    private final ObjectProperty<Member> member;
    private final ObjectProperty<PanelSingle> panelSingle;
    private final ObjectProperty<PanelRelationCurrent> panelRelationCurrent;
    private final ObservableList<PanelRelationEx> panelRelationEx;

    {
        panelSinglePane = new AnchorPane();
        panelRelationCurrentPane = new AnchorPane();
        panelRelationExPane = new HBox();


        member = new SimpleObjectProperty<>();
        panelSingle = new SimpleObjectProperty<>();
        panelRelationCurrent = new SimpleObjectProperty<>();
        panelRelationEx = FXCollections.observableArrayList();

        initListeners();
        initPanes();
    }

    public PanelChild(Member m) {
        this(m, null);
    }

    public PanelChild(Member m, SubBorderPane parent) {
        member.setValue(m);
        setParentPane(parent);
        setCenter(panelSinglePane);
        setLeft(panelRelationExPane);
        setRight(panelRelationCurrentPane);

        this.initBorder(Color.ORANGE, this);

        if(parent != null) {
            this.setPadding(new Insets(60, 10, 10, 10));
        }
    }


    /*
        Init Listeners
     */

    private void initListeners() {
        initPanelSingleListener();
        initRelationCurrentListener();
        initRelationExListener();
    }

    private void initPanelSingleListener() {
        panelSingle.addListener((observable, oldValue, newValue) -> {
            panelSinglePane.getChildren().clear();
            if(newValue != null) {
                panelSinglePane.getChildren().add(newValue);
            }
        });

    }

    private void initRelationCurrentListener() {
        panelRelationCurrent.addListener((observable, oldValue, newValue) -> {
            panelRelationCurrentPane.getChildren().clear();
            if(newValue != null) {
                panelRelationCurrentPane.getChildren().add(newValue);
            }
        });
    }

    private void initRelationExListener() {
        panelRelationEx.addListener((ListChangeListener<? super PanelRelationEx>)  c -> {
            while (c.next()) {
                if(c.wasAdded()) {
                    panelRelationExPane.getChildren().addAll(c.getAddedSubList());
                } else if(c.wasRemoved()) {
                    panelRelationExPane.getChildren().removeAll(c.getRemoved());
                }
            }
        });

    }

    public void addRelationsEx(PanelRelationEx... relations) {
        panelRelationEx.addAll(relations);
    }

    private void initPanes() {
    }

    private void setAnchors(Node n) {
        AnchorPane.setBottomAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setTopAnchor(n, 0.0);
    }


    /*
        Getters
     */

    public ObjectProperty<Member> memberProperty() {
        return member;
    }

    public Member getMember() {
        return member.get();
    }

    public ReadOnlyObjectProperty<PanelSingle> panelSingleProperty() {
        return panelSingle;
    }

    public ReadOnlyObjectProperty<PanelRelationCurrent> panelRelationCurrentProperty() {
        return panelRelationCurrent;
    }

    /*
        Setters
     */

    public void setMember(Member member) {
        this.member.set(member);
    }

    public void setPanelSingle(PanelSingle panelSingle) {
        this.panelSingle.set(panelSingle);
        panelSingle.setParentPane(this);
    }

    public void setPanelRelationCurrent(PanelRelationCurrent panelRelationCurrent) {
        this.panelRelationCurrent.set(panelRelationCurrent);
        panelRelationCurrent.setParentPane(this);
    }

}

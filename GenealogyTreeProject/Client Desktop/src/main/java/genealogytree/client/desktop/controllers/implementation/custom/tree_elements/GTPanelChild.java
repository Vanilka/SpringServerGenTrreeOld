package genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import genealogytree.client.desktop.controllers.implementation.custom.GTPanel;
import genealogytree.client.desktop.controllers.implementation.custom.GTPanelSim;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 30/03/2017.
 * An graphical object representing the child relation id GenealogyTree.
 * For Each Child in relation the GTPanelChild is generated
 *
 * @author Martyna SZYMKOWIAK
 *         <p>
 *         Top
 *         Center HboxPane
 *         Right panelSingle
 *         Left pane EX
 */
@Getter
@Setter
public class GTPanelChild extends GTPanelCommon implements GTPanel {


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private HBox pane;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private StackPane paneSingle;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private StackPane paneCouple;

    private ObservableList<GTPanelEx> panelsExList;
    private ObjectProperty<GTPanelSignle> panelSingle;
    private ObjectProperty<GTPanelCouple> panelCouple;

    private GTPanel parentPanel;
    private ObjectProperty<GTLeaf> leaf;


    {
        init();
        setPadding(new Insets(100, 30, 0, 30));
    }

    public GTPanelChild(GTLeaf leaf) {
        this(leaf, null);

    }

    public GTPanelChild(GTLeaf leaf, GTPanelSim parent) {
        viewInit(pane);
        viewInit(paneSingle);
        this.leaf.setValue(leaf);

        setLeft(pane);
        setCenter(paneSingle);
        setRight(paneCouple);

    }


    private void init() {
        pane = new HBox();
        paneSingle = new StackPane();
        paneCouple = new StackPane();
        leaf = new SimpleObjectProperty<>();
        panelSingle = new SimpleObjectProperty<>();
        panelCouple = new SimpleObjectProperty<>();
        panelsExList = FXCollections.observableArrayList();
        pane.getChildren().addAll(panelsExList);
        initListeners();
    }

    private void initListeners() {

        //
        panelsExList.addListener((ListChangeListener<GTPanelEx>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    pane.getChildren().addAll(c.getAddedSubList());
                } else if (c.wasRemoved()) {
                    pane.getChildren().removeAll(c.getRemoved());
                }
            }
        });

        panelSingle.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                paneSingle.getChildren().remove(oldValue);
            }
            paneSingle.getChildren().add(newValue == null ? new GTPanelSignle(leaf.get(), this) : newValue);
        });

        panelCouple.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                paneCouple.getChildren().remove(oldValue);
            }

            if (newValue != null)
                paneCouple.getChildren().add(newValue);
        });
    }


    private void viewInit(HBox pane) {
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setSpacing(50);
        pane.setBorder(new Border(new BorderStroke(Color.BLUE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Rectangle rectangle = new Rectangle(pane.getLayoutX(), pane.getLayoutY(), pane.getWidth(), pane.getHeight());
        rectangle.setFill(Color.ANTIQUEWHITE);
        rectangle.setVisible(false);
        rectangle.widthProperty().bind(pane.widthProperty().add(20));
        rectangle.setHeight(100);
        rectangle.layoutXProperty().bind(pane.layoutXProperty().subtract(20));
        rectangle.layoutYProperty().bind(pane.layoutYProperty().add(20));
        getChildren().add(rectangle);

        panelsExList.addListener((ListChangeListener<? super GTPanelEx>) c -> {
            if(c.getList().size() > 0) {
                rectangle.setVisible(true);
            } else {
                rectangle.setVisible(false);
            }
        });
    }

    private void viewInit(BorderPane pane) {
        pane.setBorder(new Border(new BorderStroke(Color.DARKCYAN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    private void viewInit(StackPane pane) {
        pane.setBorder(new Border(new BorderStroke(Color.DARKCYAN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public ReadOnlyObjectProperty<GTLeaf> returnLeaf() {
        return panelSingle.get().getLeaf();
    }
        /*
            GETTERS
        */

    public ObjectProperty<GTPanelSignle> panelSingleProperty() {
        return panelSingle;
    }

    public ObjectProperty<GTPanelCouple> panelCoupleProperty() {
        return panelCouple;
    }

    public ObjectProperty<GTLeaf> leafProperty() {
        return leaf;
    }

    public GTLeaf getLeaf() {
        return leaf.get();
    }


    /*
        SETTERS
     */

    public void setPanelSingle(GTPanelSignle panelSingle) {
        this.panelSingle.set(panelSingle);
    }

    public void setPanelCouple(GTPanelCouple panelCouple) {
        this.panelCouple.set(panelCouple);
    }

    public void setLeaf(GTLeaf leaf) {
        this.leaf.set(leaf);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GTPanelChild{");
        sb.append("leaf=").append(leaf);
        sb.append(", panelsExList=").append(panelsExList);
        sb.append(", leafProperty=").append(leafProperty());
        sb.append('}');
        return sb.toString();
    }
}



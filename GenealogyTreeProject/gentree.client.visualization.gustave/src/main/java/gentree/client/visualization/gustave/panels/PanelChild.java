package gentree.client.visualization.gustave.panels;

import gentree.client.desktop.domain.Member;
import gentree.client.visualization.gustave.connectors.SpouseConnector;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

    private final static double MARGIN_TOP = 0.0;
    private final static double MARGIN_LEFT = 0.0;
    private final static double MARGIN_RIGHT = 0.0;
    private final static double MARGIN_BOTTOM = 20.0;

    private final static double PADDING_TOP = 60.0;
    private final static double PADDING_LEFT = 10.0;
    private final static double PADDING_RIGHT = 10.0;
    private final static double PADDING_BOTTOM = 0.0;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final AnchorPane panelSinglePane;


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final AnchorPane panelRelationCurrentPane;

    @Getter
    private final HBox panelRelationExPane;


    private final ObjectProperty<PanelSingle> panelSingle;
    private final ObjectProperty<PanelRelationCurrent> panelRelationCurrent;
    private final ObservableList<PanelRelationEx> panelRelationEx;
    private  SpouseConnector spouseConnector;

    private final ObjectProperty<Member> member;
    private ChangeListener<? super PanelSingle> panelSingleListener = this::panelSingleChanged;
    private ChangeListener<? super PanelRelationCurrent> panelRelationCurrentListener = this::panelRelationCurrentChanged;
    private ListChangeListener<? super PanelRelationEx> panelRelationExListener = this::panelRelationExChanged;

    {
        panelSinglePane = new AnchorPane();
        panelRelationCurrentPane = new AnchorPane();
        panelRelationExPane = new HBox();

        member = new SimpleObjectProperty<>();
        panelSingle = new SimpleObjectProperty<>();
        panelRelationCurrent = new SimpleObjectProperty<>();
        panelRelationEx = FXCollections.observableArrayList();
        spouseConnector = new SpouseConnector(this);
        initListeners();
        initPanes();

    }

    public PanelChild(Member m) {
        this(m, null);
    }

    public PanelChild(Member m, SubBorderPane parent) {
        super();
        member.setValue(m);
        setParentPane(parent);
        setCenter(panelSinglePane);
        setLeft(panelRelationExPane);
        setRight(panelRelationCurrentPane);

        minWidth(USE_PREF_SIZE);
        maxWidth(USE_PREF_SIZE);

        prefWidthProperty().bind(panelRelationExPane.prefWidthProperty()
                .add(panelSinglePane.prefWidthProperty())
                .add(panelRelationCurrentPane.prefWidthProperty()));

        if (parent != null) {
            this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        }
    }

    /*
        Init Listeners
     */

    private void initListeners() {
        panelSingle.addListener(panelSingleListener);
        panelRelationCurrent.addListener(panelRelationCurrentListener);
        panelRelationEx.addListener(panelRelationExListener);
    }

    private void cleanListeners() {
        prefWidthProperty().unbind();
        panelSingle.removeListener(panelSingleListener);
        panelRelationCurrent.removeListener(panelRelationCurrentListener);
        panelRelationEx.removeListener(panelRelationExListener);

    }

    private void panelRelationCurrentChanged(ObservableValue<? extends PanelRelationCurrent> observable, PanelRelationCurrent oldValue, PanelRelationCurrent newValue) {
       if(oldValue != null) {
           oldValue.clean();
       }

        panelRelationCurrentPane.getChildren().clear();
        if (newValue != null) {
            newValue.setParentPane(this);
            panelRelationCurrentPane.getChildren().add(newValue);
        }
    }

    private void panelSingleChanged(ObservableValue<? extends PanelSingle> observable, PanelSingle oldValue, PanelSingle newValue) {
        if(oldValue != null) {
            oldValue.clean();
        }

        panelSinglePane.getChildren().clear();
        if (newValue != null) {
            newValue.setParentPane(this);
            panelSinglePane.getChildren().add(newValue);
        }
    }

    private void panelRelationExChanged(ListChangeListener.Change<? extends PanelRelationEx> c) {
        while (c.next()) {
            if (c.wasAdded()) {
                c.getAddedSubList().forEach(sb -> sb.setParentPane(this));
                panelRelationExPane.getChildren().addAll(c.getAddedSubList());
            } else if (c.wasRemoved()) {
                c.getRemoved().forEach(PanelRelationEx::clean);
                panelRelationExPane.getChildren().removeAll(c.getRemoved());
            }
        }
    }

    public void addRelationsEx(PanelRelationEx... relations) {
        panelRelationEx.addAll(relations);
    }

    private void initPanes() {
        panelRelationExPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public void setElementsNull() {
        member.setValue(null);
        panelSingle.setValue(null);
        panelRelationCurrent.setValue(null);
        panelRelationEx.clear();
        spouseConnector = null;
    }

    public void clean() {
        super.clean();
        cleanListeners();

        if(panelRelationCurrent.get() != null) {
            panelRelationCurrent.get().clean();
        }

        panelSingle.get().clean();
        panelRelationEx.forEach(PanelRelationEx::clean);
        spouseConnector.clean();

        setElementsNull();
        panelSingleListener = null;
        panelRelationCurrentListener = null;
        panelRelationExListener = null;

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

    public void setMember(Member member) {
        this.member.set(member);
    }

    public ReadOnlyObjectProperty<PanelSingle> panelSingleProperty() {
        return panelSingle;
    }

    /*
        Setters
     */

    public ReadOnlyObjectProperty<PanelRelationCurrent> panelRelationCurrentProperty() {
        return panelRelationCurrent;
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

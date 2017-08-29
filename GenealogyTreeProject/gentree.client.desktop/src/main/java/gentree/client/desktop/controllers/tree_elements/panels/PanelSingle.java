package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.controllers.tree_elements.connectors.RelationConnector;
import gentree.client.desktop.domain.Member;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 * <p>
 * Panel representing Sim itself and his own childrenPanels
 * Top: Leaf
 * Center: List of childrenPanels
 */
@Getter
@Setter
public class PanelSingle extends SubBorderPane implements RelationPane{


    private final static double MARGIN_TOP = 0.0;
    private final static double MARGIN_LEFT = 0.0;
    private final static double MARGIN_RIGHT = 0.0;
    private final static double MARGIN_BOTTOM = 50.0;

    private final static double PADDING_TOP = 10.0;
    private final static double PADDING_LEFT = 10.0;
    private final static double PADDING_RIGHT = 10.0;
    private final static double PADDING_BOTTOM = 0.0;

    private final AnchorPane pane;
    private final FamilyMember member;

    private final HBox childrenBox;
    private final ObservableList<PanelChild> childrenPanels;
  //  private final ObservableList<PanelChild> panelChildrenList;
    private final RelationConnector childrenConnector;

    /*
        Initialization
     */

    {
        pane = new AnchorPane();
        member = new FamilyMember();
     //   panelChildrenList = FXCollections.observableArrayList();
        childrenBox = new HBox();
        childrenPanels = FXCollections.observableArrayList();
        childrenConnector = new RelationConnector(this);

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
    }


    /*
        Init Panes
     */

    private void init() {
        initPane();
        initListeners();
        initHbox();
        setTop(pane);
        setCenter(childrenBox);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        setMargin(pane, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));
    }

    private void initPane() {
        pane.getChildren().add(member);
    }

    private void  initHbox() {
        childrenBox.setSpacing(10);
    }

    private void initListeners() {
        initChildrenListener();
        initRelationElementsPositionListener();
    }

    private void initChildrenListener() {
        childrenPanels.addListener((ListChangeListener<PanelChild>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    childrenBox.getChildren().addAll(c.getAddedSubList());
                    c.getAddedSubList().forEach(childrenConnector::addPanelChild);
                }
                if (c.wasRemoved()) {
                    childrenBox.getChildren().removeAll(c.getRemoved());
                    c.getAddedSubList().forEach(childrenConnector::removePanelChild);
                }
            }
        });
    }




    private void initRelationElementsPositionListener() {

        childrenBox.prefWidthProperty().bindBidirectional(pane.prefWidthProperty());

        pane.widthProperty().addListener(observable -> {
            calculateRelationElementsPosition();
        });

        childrenConnector.getLine().startXProperty().addListener(c -> {
            calculateRelationElementsPosition();
        });

        childrenConnector.getLine().endXProperty().addListener((observable, oldValue, newValue) -> {
            calculateRelationElementsPosition();
        });

        childrenConnector.getLine().startYProperty().addListener(c -> {
            calculateRelationElementsPosition();
        });

        childrenConnector.getLine().endYProperty().addListener((observable, oldValue, newValue) -> {
            calculateRelationElementsPosition();
        });
    }

    private void calculateRelationElementsPosition() {
        if (childrenPanels.size() > 0) {
            Line line = childrenConnector.getLine();
            Double offset = (line.getStartX() + line.getEndX() - member.getWidth())/2 - PADDING_LEFT;
            member.setLayoutX(offset);
            childrenConnector.connectRelationToChildren(member);
        }
    }

    public void addMember(Member m) {
        if (m != null) {
            member.setMember(m);
        }
    }


    @Override
    public void addChild(PanelChild child) {
        childrenPanels.add(child);
        child.setParentPane(this);
    }
}

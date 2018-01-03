package gentree.client.visualization.gustave.connectors;

import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationTypeElement;
import gentree.client.visualization.gustave.panels.PanelChild;
import gentree.client.visualization.gustave.panels.PanelRelationCurrent;
import gentree.client.visualization.gustave.panels.PanelSingle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by Martyna SZYMKOWIAK on 29/08/2017.
 */
public class SpouseConnector extends LineConnector {

    private static final Color COLOR_EX = Color.web("#85a2a3");
    private static final Color COLOR_CURRENT = Color.web("#5F9EA0");
    private final Line lineRelationToSim;
    private final Line lineSimConnectAllEx;
    private final Line lineSimConnectSpouse;
    private PanelChild panelChild;
    private ChangeListener<? super Bounds> boundChangeCauseDrawingListener = this::boundChangeCauseDrawing;
    private ChangeListener<? super Bounds> boundsExListener = this::boundsExChange;
    private ChangeListener<? super PanelRelationCurrent> panelRelationCurrentListener = this::panelRelationCurrentChange;
    private ChangeListener<? super PanelSingle> panelSingleListener = this::panelSingleChanged;

    public SpouseConnector(PanelChild panelChild) {
        super();
        this.lineRelationToSim = new Line();
        this.lineSimConnectAllEx = new Line();
        this.lineSimConnectSpouse = new Line();
        this.panelChild = panelChild;
        initLineProperties(lineRelationToSim);
        initLineProperties(lineSimConnectAllEx, COLOR_EX, 100.0);
        initLineProperties(lineSimConnectSpouse, COLOR_CURRENT, 100.0);
        initLines();
        initListener();
    }

    private void initLines() {
        BooleanBinding exEmpty = Bindings.createBooleanBinding(() ->
                panelChild.getPanelRelationExList().isEmpty(), panelChild.getPanelRelationExList());

        BooleanBinding currentEmpty = Bindings.createBooleanBinding(() ->
                panelChild.getPanelRelationCurrent().get() == null, panelChild.getPanelRelationCurrent());

        panelChild.getChildren().add(0, lineSimConnectAllEx);

        panelChild.getChildren().add(0, lineSimConnectSpouse);
        panelChild.getChildren().add(1, lineRelationToSim);
        panelChild.getChildren().add(2, getLine());

        initLineProperties(lineRelationToSim);
        initLineProperties(lineSimConnectAllEx, COLOR_EX, 100.0);
        initLineProperties(lineSimConnectSpouse, COLOR_CURRENT, 100.0);

        lineSimConnectAllEx.visibleProperty().bind(exEmpty.not());
        lineSimConnectSpouse.visibleProperty().bind(currentEmpty.not());
        lineRelationToSim.visibleProperty().bind(currentEmpty.not());
        getLine().visibleProperty().bind(currentEmpty.not());

    }

    private void initListener() {
        panelChild.panelSingleProperty().addListener(panelSingleListener);
        panelChild.panelRelationCurrentProperty().addListener(panelRelationCurrentListener);
        panelChild.getPanelRelationExPane().boundsInParentProperty().addListener(boundsExListener);
        panelChild.getPanelRelationExPane().boundsInLocalProperty().addListener(boundsExListener);
    }

    private void boundChangeCauseDrawing(ObservableValue<? extends Bounds> obs, Bounds oldBoundValue, Bounds newBoundValue) {
        drawLine();
    }

    private void boundsExChange(ObservableValue<? extends Bounds> obs, Bounds oldBoundValue, Bounds newBoundValue) {
        drawSpouseExLine();
    }

    private void panelRelationCurrentChange(ObservableValue<? extends PanelRelationCurrent> observable, PanelRelationCurrent oldValue, PanelRelationCurrent newValue) {
        if (oldValue != null) {
            removeListenersInsidePanelRelationCurrent(oldValue);
        }

        if (newValue != null) {
            newValue.getRelation().boundsInParentProperty().addListener(boundChangeCauseDrawingListener);
            newValue.getRelationTypeElement().boundsInParentProperty().addListener(boundChangeCauseDrawingListener);
            newValue.getSpouseCard().boundsInParentProperty().addListener(boundChangeCauseDrawingListener);
        }
    }

    private void removeListenersInsidePanelRelationCurrent(PanelRelationCurrent current) {
        current.getRelation().boundsInParentProperty().removeListener(boundChangeCauseDrawingListener);
        current.getRelationTypeElement().boundsInParentProperty().removeListener(boundChangeCauseDrawingListener);
        current.getSpouseCard().boundsInParentProperty().removeListener(boundChangeCauseDrawingListener);
    }

    private void panelSingleChanged(ObservableValue<? extends PanelSingle> observable, PanelSingle oldValue, PanelSingle newValue) {
        if (oldValue != null) {
            oldValue.boundsInParentProperty().removeListener(boundChangeCauseDrawingListener);
            oldValue.getMember().boundsInParentProperty().removeListener(boundChangeCauseDrawingListener);
        }

        if (newValue != null) {
            newValue.boundsInParentProperty().addListener(boundChangeCauseDrawingListener);
            newValue.getMember().boundsInParentProperty().addListener(boundChangeCauseDrawingListener);
        }
    }


    @Override
    public void clean() {
        panelChild.panelSingleProperty().removeListener(panelSingleListener);
        if (panelChild.getPanelSingle().get() != null) {
            panelChild.getPanelSingle().get().boundsInParentProperty().removeListener(boundChangeCauseDrawingListener);
            panelChild.getPanelSingle().get().getMember().boundsInParentProperty().removeListener(boundChangeCauseDrawingListener);
        }

        panelChild.getPanelRelationExPane().boundsInParentProperty().removeListener(boundsExListener);
        panelChild.getPanelRelationExPane().boundsInLocalProperty().removeListener(boundsExListener);


        panelChild.panelRelationCurrentProperty().removeListener(panelRelationCurrentListener);
        if (panelChild.getPanelRelationCurrent().get() != null) {
            removeListenersInsidePanelRelationCurrent(panelChild.getPanelRelationCurrent().get());
            panelChild.getPanelRelationCurrent().get().getRelation().boundsInParentProperty().removeListener(boundChangeCauseDrawingListener);
            panelChild.getPanelRelationCurrent().get().getRelationTypeElement().boundsInParentProperty().removeListener(boundChangeCauseDrawingListener);
            panelChild.getPanelRelationCurrent().get().getSpouseCard().boundsInParentProperty().removeListener(boundChangeCauseDrawingListener);
        }

        lineSimConnectAllEx.visibleProperty().unbind();
        lineSimConnectSpouse.visibleProperty().unbind();
        lineRelationToSim.visibleProperty().unbind();
        getLine().visibleProperty().unbind();

        super.clean();

        panelChild = null;

        boundChangeCauseDrawingListener = null;
        boundsExListener = null;
        panelRelationCurrentListener = null;
        panelSingleListener = null;

    }

    private void drawLine() {
        try {
            FamilyMember sim = panelChild.getPanelSingle().get().getMember();
            RelationTypeElement relationType = panelChild.getPanelRelationCurrent().get().getRelationTypeElement();
            FamilyMember sppuse = panelChild.getPanelRelationCurrent().get().getSpouseCard();
            if (sim != null && relationType != null && sppuse != null) {
                drawLine(sim, relationType, sppuse);
            }
        } catch (Exception e) {
        }
    }

    private void drawLine(FamilyMember sim, RelationTypeElement relationType, FamilyMember spouse) {
        Bounds simBounds = getRelativeBounds(sim);
        Point2D simPoint = getRightPoint(simBounds);

        Bounds relationTypeBounds = getRelativeBounds(relationType);
        Point2D relationTypePointLeft = getLeftPoint(relationTypeBounds);
        Point2D relationTypePointRight = getRightPoint(relationTypeBounds);

        Bounds spouseBounds = getRelativeBounds(spouse);
        Point2D spousePoint = getLeftPoint(spouseBounds);
        Point2D spouseRightPoint = getRightPoint(spouseBounds);

        setLineCoordinates(lineRelationToSim, simPoint.getX(), simPoint.getY(), relationTypePointLeft.getX(), relationTypePointLeft.getY());
        setLineCoordinates(getLine(), relationTypePointRight.getX(), relationTypePointRight.getY(), spousePoint.getX(), spousePoint.getY());
        setLineCoordinates(lineSimConnectSpouse, simPoint.getX(), simPoint.getY(), spouseRightPoint.getX() + 10, spouseRightPoint.getY());

    }

    private void drawSpouseExLine() {
        if (panelChild.getPanelRelationExPane().getChildren().size() > 0) {
            Bounds b1 = getRelativeBounds(panelChild.getPanelRelationExPane());
            Point2D p1 = getLeftPoint(b1);

            Bounds b2 = getRelativeBounds(panelChild.getPanelSingle().get().getMember());
            Point2D p2 = getLeftPoint(b2);

            lineSimConnectAllEx.setStartX(p2.getX());
            lineSimConnectAllEx.setStartY(p2.getY());
            lineSimConnectAllEx.setEndX(p1.getX());
            lineSimConnectAllEx.setEndY(p2.getY());

        }
    }


    private void drawSpouseFond() {

    }

    protected Bounds getRelativeBounds(Node node) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return panelChild.sceneToLocal(nodeBoundsInScene);
    }

    protected Bounds getRelativeBounds(Node node, Node relativeTo) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return relativeTo.sceneToLocal(nodeBoundsInScene);
    }
}

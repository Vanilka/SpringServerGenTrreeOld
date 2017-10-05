package gentree.client.visualization.gustave.connectors;

import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationTypeElement;
import gentree.client.visualization.gustave.panels.PanelChild;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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

    private final PanelChild panelChild;

    private final Line lineRelationToSim;
    private final Line lineSimConnectAllEx;
    private final Line lineSimConnectSpouse;

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
                panelChild.getPanelRelationEx().isEmpty(), panelChild.getPanelRelationEx());

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
        initPanelSingleListener();
        initPanelCurrentListener();
    }

    private void initPanelSingleListener() {
        panelChild.panelSingleProperty().addListener((observable, oldValue, newValue) -> {

            newValue.boundsInParentProperty().addListener((obs, oldBoundValue, newBoundValue) -> {
                drawLine();
            });

            newValue.getMember().boundsInParentProperty().addListener((obs, oldBoundValue, newBoundValue) -> {
                drawLine();
            });

        });

    }

    private void initPanelCurrentListener() {
        panelChild.panelRelationCurrentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.getRelation().boundsInParentProperty().addListener((obs, oldBoundValue, newBoundValue) -> {
                    drawLine();
                });

                newValue.getSpouseCard().boundsInParentProperty().addListener((obs, oldBoundValue, newBoundValue) -> {
                    drawLine();
                });
            }
        });

        panelChild.getPanelRelationExPane().boundsInParentProperty().addListener((obs, oldBoundValue, newBoundValue) -> {
            drawSpouseExLine();
        });
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
        setLineCoordinates(lineSimConnectSpouse, simPoint.getX(), simPoint.getY(), spouseRightPoint.getX() + 20, spouseRightPoint.getY());

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

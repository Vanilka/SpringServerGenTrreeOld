package gentree.client.visualization.gustave.connectors;

import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationTypeElement;
import gentree.client.visualization.gustave.panels.PanelChild;
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
        initLineProperties(lineSimConnectSpouse, COLOR_CURRENT,100.0);
        initListener();
    }

    private void initListener() {
        initPanelSingleListener();
        initPanelCurrentListener();

    }

    private void initPanelSingleListener() {
        panelChild.panelSingleProperty().addListener((observable, oldValue, newValue) -> {

            newValue.boundsInLocalProperty().addListener(ob -> {
                drawLine();
            });

            newValue.boundsInParentProperty().addListener(ob -> {
                drawLine();
            });

            newValue.getMember().boundsInLocalProperty().addListener(ob -> {
                drawLine();
            });

            newValue.getMember().boundsInParentProperty().addListener(ob -> {
                drawLine();
            });

            newValue.getMember().layoutXProperty().addListener(ob -> {
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

                newValue.getRelation().boundsInParentProperty().addListener((obs, oldBoundValue, newBoundValue) -> {
                    drawLine();
                });

                newValue.getSpouseCard().boundsInLocalProperty().addListener((obs, oldBoundValue, newBoundValue) -> {
                    drawLine();
                });

                newValue.getSpouseCard().boundsInParentProperty().addListener((obs, oldBoundValue, newBoundValue) -> {
                    drawLine();
                });
            }
        });

        panelChild.getPanelRelationExPane().boundsInLocalProperty().addListener((obs, oldBoundValue, newBoundValue) -> {
            drawSpouseExLine();
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
            if(sim != null && relationType != null && sppuse != null) {
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

        panelChild.getChildren().remove(lineRelationToSim);
        panelChild.getChildren().remove(getLine());
        panelChild.getChildren().remove(lineSimConnectSpouse);

        setLineCoordinates(lineRelationToSim, simPoint.getX(), simPoint.getY(), relationTypePointLeft.getX(), relationTypePointLeft.getY());
        setLineCoordinates(getLine(), relationTypePointRight.getX(), relationTypePointRight.getY(), spousePoint.getX(), spousePoint.getY());
        setLineCoordinates(lineSimConnectSpouse, simPoint.getX(), simPoint.getY(), spouseRightPoint.getX()+20,  spouseRightPoint.getY());

        panelChild.getChildren().add(0, lineSimConnectSpouse);
        panelChild.getChildren().add(1, lineRelationToSim);
        panelChild.getChildren().add(2, getLine());

    }

    private void drawSpouseExLine() {
        if(panelChild.getPanelRelationExPane().getChildren().size() > 0) {
            Bounds b1 = getRelativeBounds(panelChild.getPanelRelationExPane());
            Point2D p1 = getLeftPoint(b1);

            Bounds b2 = getRelativeBounds(panelChild.getPanelSingle().get().getMember());
            Point2D p2 = getLeftPoint(b2);

            panelChild.getChildren().remove(lineSimConnectAllEx);

            lineSimConnectAllEx.setStartX(p2.getX());
            lineSimConnectAllEx.setStartY(p2.getY());
            lineSimConnectAllEx.setEndX(p1.getX());
            lineSimConnectAllEx.setEndY(p2.getY());

            panelChild.getChildren().add(0, lineSimConnectAllEx);
        }
    }

    private void setLineCoordinates(Line line, Double startX, Double startY, Double endX, Double endY) {
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
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

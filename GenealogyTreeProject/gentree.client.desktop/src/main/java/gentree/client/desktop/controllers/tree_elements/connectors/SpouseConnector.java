package gentree.client.desktop.controllers.tree_elements.connectors;

import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.controllers.tree_elements.RelationTypeElement;
import gentree.client.desktop.controllers.tree_elements.panels.PanelChild;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by Martyna SZYMKOWIAK on 29/08/2017.
 */
public class SpouseConnector extends LineConnector {

    private final PanelChild panelChild;

    private final Line lineRelationToSim;
    private final Line lineSimConnectAllEx;

    public SpouseConnector(PanelChild panelChild) {
        super();
        this.lineRelationToSim = new Line();
        this.lineSimConnectAllEx = new Line();
        this.panelChild = panelChild;
        initLineProperties(lineRelationToSim);
        initLineProperties(lineSimConnectAllEx, Color.CADETBLUE, 100.0);
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
                newValue.getRelation().boundsInLocalProperty().addListener(ob -> {
                    drawLine();
                });

                newValue.getRelation().boundsInParentProperty().addListener(ob -> {
                    drawLine();
                });

                newValue.getRelation().needsLayoutProperty().addListener(ob -> {
                    drawLine();
                });

                newValue.getRelation().heightProperty().addListener(ob -> {
                    drawLine();
                });

                newValue.getSpouseCard().boundsInLocalProperty().addListener(ob -> {
                    drawLine();
                });

                newValue.getSpouseCard().boundsInParentProperty().addListener(ob -> {
                    drawLine();
                });

                newValue.getSpouseCard().needsLayoutProperty().addListener(ob -> {
                    drawLine();
                });

                newValue.getSpouseCard().heightProperty().addListener(ob -> {
                    drawLine();
                });
            }
        });

        panelChild.getPanelRelationExPane().boundsInLocalProperty().addListener(observable -> {
            drawSpouseExLine();
        });

        panelChild.getPanelRelationExPane().boundsInParentProperty().addListener(observable -> {
            drawSpouseExLine();
        });

        panelChild.getPanelRelationExPane().needsLayoutProperty().addListener(observable -> {
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

        panelChild.getChildren().remove(lineRelationToSim);
        panelChild.getChildren().remove(getLine());

        lineRelationToSim.setStartX(simPoint.getX());
        lineRelationToSim.setStartY(simPoint.getY());
        lineRelationToSim.setEndX(relationTypePointLeft.getX());
        lineRelationToSim.setEndY(relationTypePointLeft.getY());

        getLine().setStartX(relationTypePointRight.getX());
        getLine().setStartY(relationTypePointRight.getY());
        getLine().setEndX(spousePoint.getX());
        getLine().setEndY(spousePoint.getY());

        panelChild.getChildren().add(0, lineRelationToSim);
        panelChild.getChildren().add(1, getLine());

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

    protected Bounds getRelativeBounds(Node node) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return panelChild.sceneToLocal(nodeBoundsInScene);
    }

    protected Bounds getRelativeBounds(Node node, Node relativeTo) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return relativeTo.sceneToLocal(nodeBoundsInScene);
    }

}

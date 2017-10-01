package gentree.client.visualization.gustave.connectors;

import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationTypeElement;
import gentree.client.visualization.gustave.panels.PanelRelationEx;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/**
 * Created by Martyna SZYMKOWIAK on 29/08/2017.
 */
public class SpouseExConnector extends LineConnector {

    private PanelRelationEx panelRelationEx;

    public SpouseExConnector(PanelRelationEx panel) {
        super();
        this.panelRelationEx = panel;
        initLineProperties(getLine());
        initListeners();
    }

    @Override
    protected void initLineProperties(Line line) {
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(10);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.getStrokeDashArray().addAll(2d, 21d);
    }

    private void initListeners() {
        panelRelationEx.getRelationTypeElement().needsLayoutProperty().addListener(ob -> {
            drawLine();
        });

        panelRelationEx.getSpouseCard().widthProperty().addListener(ob -> {
            drawLine();
        });

        panelRelationEx.getSpouseCard().layoutXProperty().addListener(ob -> {
            drawLine();
        });

        panelRelationEx.getRelationTypeElement().widthProperty().addListener(ob -> {
            drawLine();
        });

        panelRelationEx.getRelationTypeElement().layoutXProperty().addListener(ob -> {
            drawLine();
        });
    }

    private void drawLine() {
        try {
            FamilyMember spouse = panelRelationEx.getSpouseCard();
            RelationTypeElement relationType = panelRelationEx.getRelationTypeElement();
            if(spouse != null && relationType != null) {
                drawLine(spouse, relationType);
            }
        } catch (Exception e) {
        }

    }

    private void drawLine(FamilyMember spouse, RelationTypeElement relationType) {
        Bounds spouseBounds = getRelativeBounds(spouse);
        Point2D spousePoint = getRightPoint(spouseBounds);

        Bounds relationTypeBounds = getRelativeBounds(relationType);
        Point2D relationTypePoint = getLeftPoint(relationTypeBounds);

        panelRelationEx.getChildren().remove(getLine());

        getLine().setStartX(spousePoint.getX());
        getLine().setStartY(spousePoint.getY());
        getLine().setEndX(relationTypePoint.getX());
        getLine().setEndY(relationTypePoint.getY());

        panelRelationEx.getChildren().add(0, getLine());
    }

    protected Bounds getRelativeBounds(Node node) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return panelRelationEx.sceneToLocal(nodeBoundsInScene);
    }

    protected Bounds getRelativeBounds(Node node, Node relativeTo) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return relativeTo.sceneToLocal(nodeBoundsInScene);
    }

}

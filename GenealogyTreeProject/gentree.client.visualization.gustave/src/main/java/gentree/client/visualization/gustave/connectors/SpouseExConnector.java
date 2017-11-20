package gentree.client.visualization.gustave.connectors;

import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationTypeElement;
import gentree.client.visualization.gustave.panels.PanelRelationEx;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private ChangeListener<? super Bounds> boundsListener = this::boundsChanged;

    public SpouseExConnector(PanelRelationEx panel) {
        super();
        this.panelRelationEx = panel;
        initLineProperties(getLine());
        panelRelationEx.getChildren().add(0, getLine());
        initListeners();
    }

    @Override
    protected void initLineProperties(Line line) {

        initLineProperties(line, Color.BLACK, 10.0);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.getStrokeDashArray().addAll(2d, 21d);
    }

    private void initListeners() {

        panelRelationEx.getSpouseCard().boundsInParentProperty().addListener(boundsListener);
        panelRelationEx.getRelationTypeElement().boundsInParentProperty().addListener(boundsListener);
    }

    private void cleanListeners() {
        panelRelationEx.getSpouseCard().boundsInParentProperty().removeListener(boundsListener);
        panelRelationEx.getRelationTypeElement().boundsInParentProperty().removeListener(boundsListener);
    }

    @Override
    public void clean() {

        cleanListeners();

        super.clean();

        panelRelationEx = null;
        boundsListener = null;
    }

    private void drawLine() {
        try {
            FamilyMember spouse = panelRelationEx.getSpouseCard();
            RelationTypeElement relationType = panelRelationEx.getRelationTypeElement();
            if (spouse != null && relationType != null) {
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

        getLine().setStartX(spousePoint.getX());
        getLine().setStartY(spousePoint.getY());
        getLine().setEndX(relationTypePoint.getX());
        getLine().setEndY(relationTypePoint.getY());

    }

    protected Bounds getRelativeBounds(Node node) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return panelRelationEx.sceneToLocal(nodeBoundsInScene);
    }

    protected Bounds getRelativeBounds(Node node, Node relativeTo) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return relativeTo.sceneToLocal(nodeBoundsInScene);
    }

    private void boundsChanged(ObservableValue<? extends Bounds> obs, Bounds oldBoundValue, Bounds newBoundValue) {
        drawLine();
    }
}

package gentree.client.visualization.elements;

import gentree.client.desktop.domain.Relation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 * Created by Martyna SZYMKOWIAK on 31/08/2017.
 */
public class RelationReference extends StackPane {

    private static final Color color1 = Color.web("#ED0C44");
    private static final Color border1 = Color.web("#aa0b33");
    private static final Color border2 = Color.web("#ff144f");
    private final Polygon etiquete = new Polygon();
    private final ObjectProperty<RelationReferenceType> relationReferenceType = new SimpleObjectProperty<>();
    private final ObjectProperty<Relation> relation = new SimpleObjectProperty<>();
    private Text text = new Text();

    public RelationReference() {
        this(null);
    }

    public RelationReference(RelationReferenceType type) {
        init();
        this.relationReferenceType.setValue(type);
    }

    private void init() {
        initColors();
        initListeners();
        getChildren().addAll(etiquete, text);
        this.setVisible(false);
    }

    private void initListeners() {
        initReferenceTypeListener();
        initRelationListener();
    }

    private void initRelationListener() {
        relation.addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                text.textProperty().bind(newValue.referenceNumberProperty().asString());
                this.visibleProperty().bind(newValue.referenceNumberProperty().greaterThan(0));
            } else {
                this.visibleProperty().unbind();
                this.setVisible(false);
                text.textProperty().unbind();
                text.setText("");
            }
        });
    }

    private void initReferenceTypeListener() {
        relationReferenceType.addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case ASC:
                    drawAsc(etiquete);
                    break;
                case DSC:
                    drawDesc(etiquete);
                    break;
                default:
                    etiquete.getPoints().clear();
            }
        });
    }

    private void drawDesc(Polygon polygon) {
        polygon.getPoints().clear();
        polygon.getPoints().addAll(
                0.0, 0.0,
                40.0, 0.0,
                40.0, 40.0,
                20.0, 60.0,
                0.0, 40.0
        );
    }

    private void drawAsc(Polygon polygon) {
        polygon.getPoints().clear();
        polygon.getPoints().addAll(
                20.0, 0.0,
                40.0, 20.0,
                40.0, 60.0,
                0.0, 60.0,
                0.0, 20.0
        );
    }

    private void initColors() {
        etiquete.setFill(color1);
        etiquete.setStrokeWidth(5);
        etiquete.setStroke(border2);
    }


    /*
        Getters && Setters
     */

    public RelationReferenceType getRelationReferenceType() {
        return relationReferenceType.get();
    }

    public void setRelationReferenceType(RelationReferenceType relationReferenceType) {
        this.relationReferenceType.set(relationReferenceType);
    }

    public Relation getRelation() {
        return relation.get();
    }

    public ObjectProperty<Relation> relationProperty() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation.set(relation);
    }

    public enum RelationReferenceType {
        ASC, DSC
    }
}

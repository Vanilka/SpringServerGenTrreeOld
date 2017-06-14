package genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import genealogytree.client.desktop.domain.GTX_Relation;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 31/03/2017.
 */
@Getter
@Setter
public class GTRelationReference extends StackPane {

    private static final Color color1 = Color.web("#ED0C44");
    private static final Color border1 = Color.web("#aa0b33");
    private static final Color border2 = Color.web("#ff144f");


    private Polygon etiquete;
    private Text text;
    private ObjectProperty<GTX_Relation> reference;
    private BooleanProperty desc;

    {
        init();
    }

    public GTRelationReference() {
        this(null, true);

    }

    public GTRelationReference(GTX_Relation ref, boolean desc) {
        this.reference.setValue(ref);
        setDesc(desc);
    }


    private void init() {
        this.reference = new SimpleObjectProperty<>();
        this.desc = new SimpleBooleanProperty(true);
        this.text = new Text();
        initEtiquete();
        initListener();
        getChildren().addAll(etiquete, text);
    }

    private void initEtiquete() {
        etiquete = new Polygon();
        etiquete.setFill(color1);
        etiquete.setStrokeWidth(5);
        etiquete.setStroke(border2);

        etiquete.setOnMouseEntered(t -> {
            etiquete.setStroke(border1);
        });

        etiquete.setOnMouseExited(t -> {
            etiquete.setStroke(border2);
        });
    }

    private void initListener() {

        reference.addListener((observable, oldValue, newValue) -> {
            text.setText(newValue == null ? "" : newValue.getId().toString());
        });


        desc.addListener((observable, oldValue, newValue) -> {
            System.out.println("desc change");
            if (newValue) {
                drawDesc(etiquete);
            } else {
                drawAsc(etiquete);
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


    public void setDesc(boolean desc) {
        this.desc.set(desc);
    }



}

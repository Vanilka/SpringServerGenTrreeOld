package gentree.client.visualization.elements;

import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.visualization.elements.configuration.ImageFiles;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 30/07/2017.
 */
@Getter
@Setter
public class RelationTypeCard extends StackPane {

    public static final double WIDTH = 60;
    public static final double HEIGHT = 60;

    protected Circle circle;


    protected Line line1;
    protected Line line2;

    protected Group group;

    protected ImageView typeImg;
    protected StackPane imageContainer;
    protected DropShadow dropShadow;
    protected ObjectProperty<Relation> relation;
    protected ObjectProperty<RelationType> relationType;

    {
        init();
    }

    public RelationTypeCard() {
        this(null);
    }

    public RelationTypeCard(Relation relation) {
        this.relation.setValue(relation);
        setMinSize(WIDTH, HEIGHT);
        setPrefSize(WIDTH, HEIGHT);
        setMaxSize(WIDTH, HEIGHT);
    }

    private void init() {
        relation = new SimpleObjectProperty<>();
        relationType = new SimpleObjectProperty<>();
        typeImg = new ImageView();
        imageContainer = new StackPane();
        initCircle();
        initCroix();

        group = new Group(line1, line2);
        imageContainer.getChildren().addAll(circle, typeImg, group);
        getChildren().add(imageContainer);
        initListeners();
        setAlignment(Pos.CENTER);
    }

    private void initCircle() {

        circle = new Circle(10, 10, 30, Color.grayRgb(23, 0.5));
    }

    private void initCroix() {
        line1 = new Line(10, 10, 50, 50);
        line2 = new Line(10, 50, 50, 10);
        initLineProperties(line1, Color.RED, 4.0);
        initLineProperties(line2, Color.RED, 4.0);
    }


    protected void initLineProperties(Line line, Color color, Double width) {
        line.setStroke(color);
        line.setStrokeWidth(width);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
    }

    private void initListeners() {

        relation.addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                relationType.bind(Bindings
                        .when(relation.isNull())
                        .then(RelationType.NEUTRAL)
                        .otherwise(newValue.typeProperty()));

                group.visibleProperty().bind(Bindings
                        .when(relationType.isEqualTo(RelationType.NEUTRAL))
                        .then(false)
                        .otherwise(newValue.activeProperty().not()));

            } else {
                group.visibleProperty().unbind();
                relationType.unbind();
                relationType.setValue(RelationType.NEUTRAL);
                group.setVisible(false);
            }
        });


        relationType.addListener((observable, oldValue, newValue) -> {
            String path;
            switch (newValue) {
                case NEUTRAL:
                    path = ImageFiles.RELATION_NEUTRAL.toString();
                    break;
                case FIANCE:
                    path = ImageFiles.RELATION_FIANCE.toString();
                    break;
                case LOVE:
                    path = ImageFiles.RELATION_LOVE.toString();
                    break;
                case MARRIED:
                    path = ImageFiles.RELATION_MARRIED.toString();
                    break;
                default:
                    path = ImageFiles.RELATION_NEUTRAL.toString();
            }
            typeImg.setImage(new Image(path));
        });
    }


    /*
        GETTERS
     */


    /*
      SETTERS
    */
    public void setRelation(Relation relation) {
        this.relation.set(relation);
    }
}

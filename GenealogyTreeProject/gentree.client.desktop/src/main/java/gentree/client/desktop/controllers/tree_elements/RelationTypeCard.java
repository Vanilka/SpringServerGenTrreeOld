package gentree.client.desktop.controllers.tree_elements;

import gentree.client.desktop.configurations.enums.ImageFiles;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

    Circle circle;
    ImageView typeImg;
    StackPane imageContainer;
    DropShadow dropShadow;
    ObjectProperty<Relation> relation;
    ObjectProperty<RelationType> relationType;

    {
        init();
    }

    public RelationTypeCard() {
        this(null);
    }

    public RelationTypeCard(Relation relation) {
        this.relation.setValue(relation);
        setMinSize(WIDTH, HEIGHT);
        setMaxSize(WIDTH, HEIGHT);
    }

    private void init() {
        relation = new SimpleObjectProperty<>();
        relationType = new SimpleObjectProperty<>();
        typeImg = new ImageView();
        imageContainer = new StackPane();
        initCircle();

        imageContainer.getChildren().addAll(circle, typeImg);
        getChildren().add(imageContainer);
        initListeners();
        setAlignment(Pos.CENTER);
    }
    private void initCircle() {

        circle = new Circle(10, 10, 30, Color.grayRgb(23, 0.5));
    }


    private void initListeners() {

        relation.addListener((observable, oldValue, newValue) -> {

            if(newValue != null) {
                relationType.bind(Bindings
                        .when(relation.isNull())
                        .then(RelationType.NEUTRAL)
                        .otherwise(newValue.typeProperty()));
            } else {
                relationType.unbind();
                relationType.setValue(RelationType.NEUTRAL);
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

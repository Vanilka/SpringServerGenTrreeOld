package gentree.client.desktop.controllers.tree_elements;

import com.sun.scenario.effect.Color4f;
import gentree.client.desktop.configurations.enums.ImageFiles;
import gentree.client.desktop.domain.enums.RelationType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
public class RelationTypeElement extends StackPane {

    public static final double WIDTH = 60;
    public static final double HEIGHT = 60;

    Circle circle;
    ImageView typeImg;
    StackPane imageContainer;
    DropShadow dropShadow;
    private ObjectProperty<RelationType> type;
    private BooleanProperty isActive;

    {
        init();
    }

    public RelationTypeElement() {
        this(RelationType.NEUTRAL, true);
    }

    public RelationTypeElement(RelationType type, boolean isActive) {
        this.type.setValue(type);
        this.isActive.setValue(isActive);
        setMinSize(WIDTH, HEIGHT);
        setMaxSize(WIDTH, HEIGHT);
    }

    private void init() {
        type = new SimpleObjectProperty<>();
        isActive = new SimpleBooleanProperty();
        typeImg = new ImageView();
        imageContainer = new StackPane();
        dropShadow = new DropShadow();
        initCircle();
        initShadow();

        imageContainer.getChildren().addAll(circle, typeImg);
        getChildren().add(imageContainer);
        initListeners();
        setAlignment(Pos.CENTER);


        this.setOnMouseEntered(t -> {
            circle.setEffect(dropShadow);
        });

        this.setOnMouseExited(t -> {

         circle.setEffect(null);
        });

    }

    private void initShadow() {
        dropShadow.setRadius(10.0f);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        dropShadow.setColor(Color.color(0.7, 0.7, 0.2));
    }


    private void initCircle() {

        circle = new Circle(10, 10, 30, Color.grayRgb(23, 0.5));
    }


    private void initListeners() {
        type.addListener((observable, oldValue, newValue) -> {
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

    public ObjectProperty<RelationType> typeProperty() {
        return type;
    }

    public boolean isIsActive() {
        return isActive.get();
    }

    public void setIsActive(boolean isActive) {
        this.isActive.set(isActive);
    }

    public BooleanProperty isActiveProperty() {
        return isActive;
    }

    /*
            SETTERS
         */
    public void setType(RelationType type) {
        this.type.set(type);
    }


}

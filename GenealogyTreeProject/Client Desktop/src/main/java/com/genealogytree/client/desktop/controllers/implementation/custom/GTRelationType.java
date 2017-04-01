package com.genealogytree.client.desktop.controllers.implementation.custom;

import com.genealogytree.client.desktop.configuration.enums.ImageFiles;
import com.genealogytree.domain.enums.RelationType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 31/03/2017.
 */
@Getter
@Setter
public class GTRelationType extends AnchorPane {

    Circle circle;
    ImageView typeImg;
    StackPane imageContainer;
    private ObjectProperty<RelationType> type;
    private BooleanProperty isActive;


    {
        init();
    }


    public GTRelationType() {
        this(RelationType.NEUTRAL, true);
    }

    public GTRelationType(RelationType type, boolean isActive) {
    this.type.setValue(type);
    this.isActive.setValue(isActive);

    }


    private void init() {
        type = new SimpleObjectProperty<>();
        isActive = new SimpleBooleanProperty();
        typeImg = new ImageView();
        imageContainer = new StackPane();
        initCircle();

        imageContainer.getChildren().addAll(circle, typeImg);
        getChildren().add(imageContainer);
        initListeners();
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

}

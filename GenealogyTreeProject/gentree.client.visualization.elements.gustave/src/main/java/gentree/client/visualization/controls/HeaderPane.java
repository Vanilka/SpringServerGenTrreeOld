package gentree.client.visualization.controls;

import gentree.client.visualization.controls.skin.HeaderPaneSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.WritableValue;
import javafx.css.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vanilka on 07/10/2017.
 */
public class HeaderPane extends Control {

    private static final String DEFAULT_CLASS_NAME = "header-pane";

    private final SimpleStringProperty title = new SimpleStringProperty("Header Title");


    private final ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
        @Override
        public Object getBean() {
            return HeaderPane.this;
        }

        @Override
        public String getName() {
            return "onAction";
        }

        @Override
        protected void invalidated() {
            setEventHandler(ActionEvent.ACTION, get());
        }
    };

    public HeaderPane() {
        initialize();
        createDefaultSkin();
    }

    public HeaderPane(String title) {
        initialize();
        this.title.set(title);
        createDefaultSkin();

    }

    private void initialize() {
        getStyleClass().add(DEFAULT_CLASS_NAME);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }



    /*
     *   GETTERS SETTERS
     */
    public EventHandler<ActionEvent> getOnAction() {
        return onActionProperty().get();
    }

    public void setOnAction(EventHandler<ActionEvent> value) {
        onAction.set(value);
    }

    public ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {
        return onAction;
    }


    /**
     *  Inner static class Styleable properties
     */
    private static class StyleableProperties {

        static {
            List<CssMetaData<? extends Styleable, ?>> temp
                    = new ArrayList<>(Control.getClassCssMetaData());
            Collections.addAll(temp);
            cssMetaDataList = Collections.unmodifiableList(temp);
        }

        private static final List<CssMetaData<? extends Styleable, ?>> cssMetaDataList;
    }

    @Override
    protected List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.cssMetaDataList;
    }

    /**
     * Create DefaultSkin override
     * @return
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new HeaderPaneSkin(this);
    }


    /**
     * Return default Css
     * @return
     */
    @Override
    public String getUserAgentStylesheet() {
        return HeaderPane.class.getResource("/layout/style/header-pane.css").toExternalForm();
    }
}

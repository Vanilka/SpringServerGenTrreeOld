package gentree.client.visualization.controls;


import gentree.client.visualization.controls.skin.AgePaneSkin;
import gentree.common.configuration.enums.Age;
import javafx.beans.NamedArg;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.util.List;

/**
 * Created by vanilka on 14/10/2017.
 */
public class AgePane extends Control {

    private static final String DEFAULT_CLASS_NAME = "age-pane";

    private final ObjectProperty<Age> simAge = new SimpleObjectProperty<>(Age.YOUNG_ADULT);
    private final BooleanProperty isModifiable = new SimpleBooleanProperty(true);

    public AgePane() {
        this(null);
    }

    public AgePane(Age age) {
        this(age, true);
    }

    public AgePane(Age age, @NamedArg("modifiable") boolean isModifiable) {
        this.simAge.set(age);
        this.isModifiable.setValue(isModifiable);
        createDefaultSkin();
    }


    @Override
    protected List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

    /**
     * Create DefaultSkin override
     *
     * @return
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new AgePaneSkin(this);
    }


    /**
     * Return default Css
     *
     * @return
     */
    @Override
    public String getUserAgentStylesheet() {
        return HeaderPane.class.getResource("/layout/style/age-pane.css").toExternalForm();
    }


    /*
     *  GETTERS / SETTERS
     */

    public Age getSimAge() {
        return simAge.get();
    }

    public void setSimAge(Age simAge) {
        this.simAge.set(simAge);
    }

    public ObjectProperty<Age> simAgeProperty() {
        return simAge;
    }

    public boolean isIsModifiable() {
        return isModifiable.get();
    }

    public void setIsModifiable(boolean isModifiable) {
        this.isModifiable.set(isModifiable);
    }

    public BooleanProperty isModifiableProperty() {
        return isModifiable;
    }
}


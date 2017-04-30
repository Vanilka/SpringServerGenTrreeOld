package com.genealogytree.client.desktop.domain;

import com.genealogytree.client.desktop.configuration.helper.BooleanPropertyMarshaller;
import com.genealogytree.client.desktop.configuration.helper.StringPropertyMarshaller;
import com.genealogytree.domain.enums.RelationType;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * Created by vanilka on 31/12/2016.
 */

@XmlType(name = "relation")
public class GTX_Relation implements  Serializable {
    private static final long serialVersionUID = 2837614786245672177L;

    private Long id;

    private Long version;

    private ObjectProperty<RelationType> type;

    private ObjectProperty<GTX_Member> simLeft;

    private ObjectProperty<GTX_Member> simRight;

    private ObservableList<GTX_Member> children;

    private BooleanProperty active;

    private BooleanProperty current;

    {
        type = new SimpleObjectProperty<>();
        simLeft = new SimpleObjectProperty<>();
        simRight = new SimpleObjectProperty<>();
        children = FXCollections.observableArrayList();
        active = new SimpleBooleanProperty();
    }

    public GTX_Relation() {
        this(null, null, null, RelationType.NEUTRAL, true);
    }

    public GTX_Relation(GTX_Member simLeft, GTX_Member simRight, GTX_Member child) {
        this(simLeft, simRight, child, RelationType.NEUTRAL, true);
    }

    public GTX_Relation(GTX_Member simLeft, GTX_Member simRight, GTX_Member child, RelationType type, boolean active) {
        setSimLeft(simLeft);
        setSimRight(simRight);
        if(child != null) {
            getChildren().add(child);
        }
        setType(type);
        setActive(active);
    }


    public boolean compareLeftRight(GTX_Relation other) {

        if (other == null) return false;

        if (getSimLeft() != null ? !getSimLeft().equals(other.getSimLeft()) : other.getSimLeft() != null)
            return false;
        if (getSimRight() != null ? !getSimRight().equals(other.getSimRight()) : other.getSimRight() != null)
            return false;

        return true;

    }

    public boolean isRacine() {
        return getSimLeft() == null && getSimRight() == null;
    }


    /*
        GETTERS
    */

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public RelationType getType() {
        return type.get();
    }

    public ObjectProperty<RelationType> typeProperty() {
        return type;
    }

    public GTX_Member getSimLeft() {
        return simLeft.get();
    }

    public ObjectProperty<GTX_Member> simLeftProperty() {
        return simLeft;
    }

    public GTX_Member getSimRight() {
        return simRight.get();
    }

    public ObjectProperty<GTX_Member> simRightProperty() {
        return simRight;
    }

    public ObservableList<GTX_Member> getChildren() {
        return children;
    }



    public Boolean isActive() {
        return active.get();
    }


    public BooleanProperty activeProperty() {
        return active;
    }


    public Boolean isCurrent() {
        return current.get();
    }

    public BooleanProperty currentProperty() {
        return current;
    }

    /*
    *  SETTERS
     */

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setType(RelationType type) {
        this.type.set(type);
    }

    public void setSimLeft(GTX_Member simLeft) {
        this.simLeft.set(simLeft);
    }

    public void setSimRight(GTX_Member simRight) {
        this.simRight.set(simRight);
    }

    public void setChildren(ObservableList<GTX_Member> children) {
        this.children = children;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public void setCurrent(boolean current) {
        this.current.set(current);
    }

    @Override
    public String toString() {
        return "GTX_Relation{" +
                "id=" + id +
                ", version=" + version +
                ", type=" + type +
                ", simLeft=" + simLeft +
                ", simRight=" + simRight +
                ", children=" + children +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GTX_Relation)) return false;

        GTX_Relation that = (GTX_Relation) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (getSimLeft() != null ? !getSimLeft().equals(that.getSimLeft()) : that.getSimLeft() != null) return false;
        if (getSimRight() != null ? !getSimRight().equals(that.getSimRight()) : that.getSimRight() != null)
            return false;
        if (children != null ? !children.equals(that.children) : that.children != null) return false;
        return active != null ? active.equals(that.active) : that.active == null;

    }


    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (simLeft != null ? simLeft.hashCode() : 0);
        result = 31 * result + (simRight != null ? simRight.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }
}

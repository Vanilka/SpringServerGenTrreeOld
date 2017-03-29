package com.genealogytree.client.desktop.domain;

import com.genealogytree.domain.enums.RelationType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vanilka on 31/12/2016.
 */
@Getter
@Setter
public class GTX_Relation implements Serializable {
    private static final long serialVersionUID = 2837614786245672177L;

    private Long id;
    private Long version;
    private ObjectProperty<RelationType> type;
    private ObjectProperty<GTX_Member> simLeft;
    private ObjectProperty<GTX_Member> simRight;
    private ObservableList<GTX_Member> children;
    private BooleanProperty isActive;

    {
        type = new SimpleObjectProperty<>();
        simLeft = new SimpleObjectProperty<>();
        simRight = new SimpleObjectProperty<>();
        children = FXCollections.observableArrayList();
        isActive = new SimpleBooleanProperty();
    }

    public GTX_Relation() {
        this(null, null, null, RelationType.NEUTRAL, true);
    }

    public GTX_Relation(GTX_Member simLeft, GTX_Member simRight, GTX_Member child) {
        this(simLeft, simRight, child, RelationType.NEUTRAL, true);
    }

    public GTX_Relation(GTX_Member simLeft, GTX_Member simRight, GTX_Member child, RelationType type, boolean isActive) {
        setSimLeft(simLeft);
        setSimRight(simRight);
        getChildren().add(child);
        setType(type);
        setIsActive(isActive);
    }


    public boolean compareLeftRight(GTX_Relation other) {

        if (other == null) return false;

        if (getSimLeft() != null ? !getSimLeft().equals(other.getSimLeft()) : other.getSimLeft() != null)
            return false;
        if (getSimRight() != null ? !getSimRight().equals(other.getSimRight()) : other.getSimRight() != null)
            return false;

        return true;

    }

    /*
        GETTERS
    */

    public ObjectProperty<GTX_Member> simLeftProperty() {
        return simLeft;
    }

    public ObjectProperty<GTX_Member> simRightProperty() {
        return simRight;
    }

    public GTX_Member getSimLeft() {
        return simLeft.getValue();
    }

    public void setSimLeft(GTX_Member simLeft) {
        this.simLeft.set(simLeft);
    }

    public GTX_Member getSimRight() {
        return simRight.getValue();
    }

    /*
    *  SETTERS
     */

    public void setSimRight(GTX_Member simRight) {
        this.simRight.set(simRight);
    }

    public BooleanProperty isActiveProperty() {
        return isActive;
    }

    public void setChildrenList(List<GTX_Member> children) {
        this.children.clear();
        this.children.addAll(children);
    }

    public void setType(RelationType type) {
        this.type.set(type != null ? type : RelationType.NEUTRAL);
    }

    public void setIsActive(boolean isActive) {
        this.isActive.set(isActive);
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
                ", isActive=" + isActive +
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
        return isActive != null ? isActive.equals(that.isActive) : that.isActive == null;

    }


    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (simLeft != null ? simLeft.hashCode() : 0);
        result = 31 * result + (simRight != null ? simRight.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        return result;
    }
}

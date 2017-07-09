package gentree.client.desktop.domain;

import gentree.client.desktop.domain.enums.RelationType;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */
public class Relation extends Observable implements Serializable {

    private static final long serialVersionUID = 7679518429380405561L;

    private LongProperty id;

    private ObjectProperty<Member> left;
    private ObjectProperty<Member> right;

    private ObservableList<Member> children;

    private ObjectProperty<RelationType> type;
    private BooleanProperty isActive;

    {
        this.id = new SimpleLongProperty();
        this.left = new SimpleObjectProperty<>();
        this.right = new SimpleObjectProperty<>();
        this.children = FXCollections.observableArrayList();
        this.type = new SimpleObjectProperty<>();
        this.isActive = new SimpleBooleanProperty();
    }

    /*
        GETTERS
     */

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public Member getLeft() {
        return left.get();
    }

    public ObjectProperty<Member> leftProperty() {
        return left;
    }

    public Member getRight() {
        return right.get();
    }

    public ObjectProperty<Member> rightProperty() {
        return right;
    }

    public ObservableList<Member> getChildren() {
        return children;
    }

    public RelationType getType() {
        return type.get();
    }

    public ObjectProperty<RelationType> typeProperty() {
        return type;
    }

    public boolean isIsActive() {
        return isActive.get();
    }

    public BooleanProperty isActiveProperty() {
        return isActive;
    }

    /*
        SETTERS
     */

    public void setId(long id) {
        this.id.set(id);
    }

    public void setLeft(Member left) {
        this.left.set(left);
    }

    public void setRight(Member right) {
        this.right.set(right);
    }

    public void setChildren(ObservableList<Member> children) {
        this.children = children;
    }

    public void setType(RelationType type) {
        this.type.set(type);
    }

    public void setIsActive(boolean isActive) {
        this.isActive.set(isActive);
    }
}

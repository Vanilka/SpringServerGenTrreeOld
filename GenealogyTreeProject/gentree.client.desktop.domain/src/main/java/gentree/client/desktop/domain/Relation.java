package gentree.client.desktop.domain;

import gentree.client.desktop.configuration.wrappers.SimMarshaller;
import gentree.client.desktop.domain.enums.RelationType;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Observable;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */
@XmlType(name = "relation")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Relation extends Observable implements Serializable {

    private static final long serialVersionUID = 7679518429380405561L;

    private LongProperty id;

    @XmlTransient
    private LongProperty referenceNumber;

    private ObjectProperty<Member> left;
    private ObjectProperty<Member> right;
    private ObservableList<Member> children;
    private ObjectProperty<RelationType> type;
    private BooleanProperty active;

    {
        this.id = new SimpleLongProperty();
        this.referenceNumber = new SimpleLongProperty();
        this.left = new SimpleObjectProperty<>();
        this.right = new SimpleObjectProperty<>();
        this.children = FXCollections.observableArrayList();
        this.type = new SimpleObjectProperty<>(RelationType.NEUTRAL);
        this.active = new SimpleBooleanProperty();

    }

    public Relation() {
        this(null);
    }

    public Relation(Member child) {
        this(null, null, RelationType.NEUTRAL, true, child);
    }

    public Relation(Member left, Member right, RelationType type, boolean active, Member... children) {
        setLeft(left);
        setRight(right);
        setType(type);
        setActive(!type.equals(RelationType.NEUTRAL) && active);
        addChildren(children);
    }


    private void invalidate() {
        setChanged();
        notifyObservers();
    }

    public void addChildren(Member... children) {
        if (children != null) {
            for (Member child : children) {
                if (!this.children.contains(child)) {
                    this.children.add(child);
                }
            }
        }
    }

    public boolean compareLeft(Object o) {
        if (getLeft() == null && o == null) return true;
        if (getLeft() == null) return false;
        if (o == null) return false;
        if (getLeft() == o) return true;
        Member other = (Member) o;
        return getLeft().equals(other);
    }

    public boolean compareRight(Object o) {
        if (getRight() == null && o == null) return true;
        if (getRight() == null) return false;
        if (o == null) return false;
        if (getRight() == o) return true;
        Member other = (Member) o;
        return getRight().equals(other);
    }

    public boolean isRoot() {
        return getRight() == null && getLeft() == null;
    }


    /*
        GETTERS AND SETTERS
     */

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    @XmlIDREF
    public Member getLeft() {
        return left.get();
    }

    public void setLeft(Member left) {
        this.left.set(left);
    }

    public ObjectProperty<Member> leftProperty() {
        return left;
    }

    @XmlIDREF
    public Member getRight() {
        return right.get();
    }

    public void setRight(Member right) {
        this.right.set(right);
    }

    public ObjectProperty<Member> rightProperty() {
        return right;
    }

    @XmlElementWrapper
    @XmlElement(name = "child")
    @XmlJavaTypeAdapter(SimMarshaller.class)
    public ObservableList<Member> getChildren() {
        return children;
    }

    public void setChildren(ObservableList<Member> children) {
        this.children = children;
    }


    public RelationType getType() {
        return type.get() == null ? RelationType.NEUTRAL : type.get();
    }

    public void setType(RelationType type) {
        this.type.set(type == null ? RelationType.NEUTRAL : type);
    }

    public ObjectProperty<RelationType> typeProperty() {
        return type;
    }

    public boolean getActive() {
        return active.get();
    }

    public void setActive(boolean active) {
        this.active.set(type.get() != RelationType.NEUTRAL && active);
        invalidate();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public Long getReferenceNumber() {
        return referenceNumber.get();
    }

    public LongProperty referenceNumberProperty() {
        return referenceNumber;
    }

    public void setReferenceNumber(long referenceNumber) {
        this.referenceNumber.set(referenceNumber);
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber.setValue(referenceNumber);
    }
}

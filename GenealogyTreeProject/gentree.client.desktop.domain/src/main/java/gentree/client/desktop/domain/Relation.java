package gentree.client.desktop.domain;

import gentree.client.desktop.configuration.wrappers.SimMarshaller;
import gentree.common.configuration.enums.RelationType;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Objects;
import java.util.Observable;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */

@XmlType(name = "relation", propOrder = {"id", "type", "active", "left", "right", "children"})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Relation extends Observable implements Serializable, Comparable<Relation> {

    private static final long serialVersionUID = 7679518429380405561L;
    @XmlTransient
    private LongProperty version;
    private LongProperty id;
    @XmlTransient
    private final LongProperty referenceNumber;

    private final ObjectProperty<Member> left;
    private final ObjectProperty<Member> right;
    private final ObservableList<Member> children;
    private final ObjectProperty<RelationType> type;
    private final BooleanProperty active;


    {
        this.version = new SimpleLongProperty();
        this.id = new SimpleLongProperty();
        this.referenceNumber = new SimpleLongProperty();
        this.left = new SimpleObjectProperty<>();
        this.right = new SimpleObjectProperty<>();
        this.children = FXCollections.observableArrayList();
        this.type = new SimpleObjectProperty<>(RelationType.NEUTRAL);
        this.active = new SimpleBooleanProperty();


    }

    public Relation() {
        setType(RelationType.NEUTRAL);
        setActive(true);

    }

    public Relation(Member child) {
        this(null, null, RelationType.NEUTRAL, true, child);
    }

    public Relation(Member left, Member right, RelationType type, boolean active, Member... children) {
        setLeft(left);
        setRight(right);
        setType(type);
        setActive(active);
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

    @XmlTransient
    public long getVersion() {
        return version.get();
    }

    public void setVersion(long version) {
        this.version.set(version);
    }

    public LongProperty versionProperty() {
        return version;
    }

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.setValue(id);
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
        this.children.clear();
        this.children.addAll(children);
    }


    @XmlElement(name = "type")
    public RelationType getType() {
        return type.get() == null ? RelationType.NEUTRAL : type.get();
    }

    public void setType(RelationType type) {

        if (type == null) {
            type = RelationType.NEUTRAL;
        }

        this.type.set(type);
        if (type == RelationType.NEUTRAL) {
            active.set(false);
        }
        invalidate();
    }

    public ObjectProperty<RelationType> typeProperty() {
        return type;
    }


    @XmlElement(name = "active")
    public boolean getActive() {
        return active.get();
    }

    public void setActive(boolean active) {
        this.active.set(active);
        invalidate();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    @XmlTransient
    public Long getReferenceNumber() {
        return referenceNumber.get();
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber.setValue(referenceNumber);
    }

    public void setReferenceNumber(long referenceNumber) {
        this.referenceNumber.set(referenceNumber);
    }

    public LongProperty referenceNumberProperty() {
        return referenceNumber;
    }


    @Override
    public int compareTo(Relation o) {
        if (!this.equals(o)) return -1;
        return 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Relation{");
        sb.append("id=").append(id);
        sb.append(", referenceNumber=").append(referenceNumber);
        sb.append(", left=").append(left);
        sb.append(", right=").append(right);
        sb.append(", children=").append(children);
        sb.append(", type=").append(type);
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }

    public String printRelation() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Relation id: ").append(getId()).append(" -> {");
        sb.append("type = ").append(getType());
        sb.append("active = ").append(getActive());
        sb.append(", left ID =").append(getLeft().getId());
        sb.append(", right ID=").append(getRight().getId());
        sb.append(", children = {");
        children.forEach(c -> {
            sb.append(c.getId()).append(", ");
        });
        sb.append("}");
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Relation)) return false;

        Relation r = (Relation) obj;
        return Objects.equals(getId(), r.getId())
                && Objects.equals(getLeft(), r.getLeft())
                && Objects.equals(getRight(), r.getRight())
                && Objects.equals(getType(), r.getType())
                && getActive() == r.getActive()
                && Objects.equals(getChildren(), r.getChildren());
    }
}

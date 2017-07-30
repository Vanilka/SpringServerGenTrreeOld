package gentree.client.desktop.domain;

import gentree.client.desktop.configurations.helper.SimMarshaller;
import gentree.client.desktop.domain.enums.RelationType;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    private ObjectProperty<Member> left;
    private ObjectProperty<Member> right;
    private ObservableList<Member> children;
    private ObjectProperty<RelationType> type;
    private BooleanProperty active;

    {
        this.id = new SimpleLongProperty();
        this.left = new SimpleObjectProperty<>();
        this.right = new SimpleObjectProperty<>();
        this.children = FXCollections.observableArrayList();
        this.type = new SimpleObjectProperty<>();
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
        setActive(type.equals(RelationType.NEUTRAL) || active);
        addChildren(children);

    }


    public void addChildren(Member... children) {
        for(Member child : children) {
            if( ! this.children.contains(child)) {
                this.children.add(child);
            }
        }
    }

    public boolean compareLeft(Object o) {
        if(getLeft() == null && o == null) return true;
        if(getLeft() == o) return  true;
        Member other = (Member) o;
        return getLeft().equals(o);
    }

    public boolean compareRight(Object o) {
        if(getRight() == null && o == null) return true;
        if(getRight() == o ) return true;
        Member other = (Member) o;
        return getRight().equals(o);
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

    @XmlIDREF
    public Member getLeft() {
        return left.get();
    }

    public ObjectProperty<Member> leftProperty() {
        return left;
    }

    @XmlIDREF
    public Member getRight() {
        return right.get();
    }

    public ObjectProperty<Member> rightProperty() {
        return right;
    }

    @XmlElementWrapper
    @XmlElement(name="child")
    @XmlJavaTypeAdapter(SimMarshaller.class)
    public ObservableList<Member> getChildren() {
        return children;
    }

    public RelationType getType() {
        return type.get();
    }

    public ObjectProperty<RelationType> typeProperty() {
        return type;
    }

    public boolean getActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
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

    public void setActive(boolean active) {
        this.active.set(active);
    }
}

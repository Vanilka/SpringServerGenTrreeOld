package genealogytree.domain;

import genealogytree.domain.beans.RelationBean;
import genealogytree.domain.enums.RelationType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vanilka on 31/12/2016.
 */
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
        setType(RelationType.NEUTRAL);
        setIsActive(true);
    }

    public GTX_Relation(GTX_Member simLeft, GTX_Member simRight, GTX_Member child, RelationType type, boolean isActive) {
        setSimLeft(simLeft);
        setSimRight(simRight);
        addChild(child);
        setType(type);
        setIsActive(isActive);
    }

    public GTX_Relation(GTX_Member simLeft, GTX_Member simRight, GTX_Member child) {
        this(simLeft, simRight, child, RelationType.NEUTRAL, true);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void updateFromBean(RelationBean bean) {
        setId(bean.getId());
        setVersion(bean.getVersion());
        setType(bean.getRelationType());
        setIsActive(bean.getActive());
    }
    /*
    * GETTERS
     */

    public void addChild(GTX_Member child) {
        this.children.add(child);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public RelationType getType() {
        return type.get();
    }

    public void setType(RelationType type) {
        this.type.set(type != null ? type : RelationType.NEUTRAL);
    }

    public ObjectProperty<RelationType> typeProperty() {
        return type;
    }

    public GTX_Member getSimLeft() {
        return simLeft.get();
    }

    public void setSimLeft(GTX_Member simLeft) {
        this.simLeft.set(simLeft);
    }

    public ObjectProperty<GTX_Member> simLeftProperty() {
        return simLeft;
    }

    public GTX_Member getSimRight() {
        return simRight.get();
    }

    public void setSimRight(GTX_Member simRight) {
        this.simRight.set(simRight);
    }

    /*
    *  SETTERS
     */

    public ObjectProperty<GTX_Member> simRightProperty() {
        return simRight;
    }

    public ObservableList<GTX_Member> getChildren() {
        return children;
    }

    public void setChildren(ObservableList<GTX_Member> children) {
        this.children = children;
    }

    public List<GTX_Member> getChildrenList() {
        return children;
    }

    public void setChildrenList(List<GTX_Member> children) {
        this.children.clear();
        this.children.addAll(children);
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
        if (simLeft != null ? !simLeft.equals(that.simLeft) : that.simLeft != null) return false;
        if (simRight != null ? !simRight.equals(that.simRight) : that.simRight != null) return false;
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

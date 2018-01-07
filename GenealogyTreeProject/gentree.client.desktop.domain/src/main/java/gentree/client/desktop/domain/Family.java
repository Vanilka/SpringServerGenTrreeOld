package gentree.client.desktop.domain;

import gentree.exception.NotUniqueBornRelationException;
import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import lombok.extern.log4j.Log4j2;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
@Log4j2
@XmlRootElement
public class Family implements Serializable {

    private static final long serialVersionUID = 1903864641139878342L;


    private LongProperty version = new SimpleLongProperty();
    private LongProperty id = new SimpleLongProperty();
    private StringProperty name = new SimpleStringProperty();
    private ObservableList<Member> members = FXCollections.observableArrayList();
    private ObservableList<Relation> relations = FXCollections.observableArrayList();

    public Family() {
        super();
    }


    public Family(String name) {
        this.name.setValue(name);
    }

    private static Callback<Member, Observable[]> memberCallback() {
        return (Member m) -> new Observable[]{
                m.idProperty(),
                m.nameProperty(),
                m.surnameProperty(),
                m.bornnameProperty(),
                m.aliveProperty(),
                m.deathCauseProperty(),
                m.ageProperty(),
                m.genderProperty(),
                m.photoProperty()
        };
    }

    private Callback<Relation, Observable[]> relationCallback() {
        return r -> new Observable[]{
                /* r.idProperty(),*/
                r.typeProperty(),
                r.activeProperty(),
                r.leftProperty(),
                r.rightProperty(),
                r.getChildren()
        };
    }

    public void addMember(Member member) {
        this.members.add(member);
    }


    public void addRelation(Relation relation) {
        this.relations.add(relation);
    }

    public Relation findBornRelation(Member m) throws NotUniqueBornRelationException {
        List<Relation> list = relations.stream()
                .filter(relation -> relation.getChildren().contains(m))
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            return null;
        }

        if (list.size() > 1) {
            throw new NotUniqueBornRelationException();
        }

        return list.get(0);
    }

    /**
     * Verify if parameter sim is Ascendant of parameter grain
     *
     * @param grain
     * @param sim
     * @return
     */
    public boolean isAscOf(Member grain, Member sim) {
        if (grain == null || sim == null) return false;

        try {
            Relation r = findBornRelation(grain);
            if (r.compareLeft(sim) || isAscOf(r.getLeft(), sim)) return true;
            if (r.compareRight(sim) || isAscOf(r.getRight(), sim)) return true;

        } catch (NotUniqueBornRelationException e) {
            return false;
        }
        return false;
    }


    /*
        GETTERS
     */

    /**
     * Verify if parameter sim is Descendant of parameter grain
     *
     * @param grain
     * @param sim
     * @return
     */
    public boolean isDescOf(Member grain, Member sim) {
        if (grain == null || sim == null) return false;

        List<Relation> list = relations
                .stream()
                .filter(r -> r.compareLeft(grain) || r.compareRight(grain))
                .collect(Collectors.toList());

        for (Relation r : list) {
            if (r.getChildren().contains(sim)) return true;

            for (Member m : r.getChildren()) {
                if (isDescOf(m, sim)) return true;
            }
        }
        return false;
    }

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

    @XmlElement
    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    /*
        SETTERS
     */

    @XmlElement
    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @XmlElementWrapper(name = "members")
    @XmlElements({@XmlElement(name = "sim", type = Member.class)})
    public ObservableList<Member> getMembers() {
        return members;
    }

    public void setMembers(ObservableList<Member> members) {
        this.members = members;
    }

    @XmlElementWrapper(name = "relations")
    @XmlElements({@XmlElement(name = "relation", type = Relation.class)})
    public ObservableList<Relation> getRelations() {
        return relations;
    }

    public void setRelations(ObservableList<Relation> relations) {
        this.relations = relations;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Family)) return false;
        if (obj == this) return true;

        Family f = (Family) obj;

        return Objects.equals(getVersion(), f.getVersion())
                && Objects.equals(getId(), f.getId())
                && Objects.equals(getName(), f.getName())
                && Objects.equals(getMembers(), f.getMembers())
                && Objects.equals(getRelations(), f.getRelations());
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Family{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", members=").append(members);
        sb.append(", relations=").append(relations);
        sb.append(", versionProperty=").append(versionProperty());
        sb.append(", idProperty=").append(idProperty());
        sb.append(", nameProperty=").append(nameProperty());
        sb.append('}');
        return sb.toString();
    }
}


package gentree.client.desktop.domain;

import gentree.client.desktop.exception.NotUniqueBornRelationException;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
@Log4j2
@XmlRootElement
public class Family implements Serializable {

    private static final long serialVersionUID = 1903864641139878342L;

    private LongProperty version;
    private LongProperty id;
    private StringProperty name;

    private ObservableList<Member> members;
    private ObservableList<Relation> relations;


    {
        version = new SimpleLongProperty();
        id = new SimpleLongProperty();
        name = new SimpleStringProperty();
        members = FXCollections.observableArrayList();
        relations = FXCollections.observableArrayList();
    }

    public Family() {
        super();
    }

    public Family(String name) {
        this.name.setValue(name);
    }


    public void addMember(Member member) {
        this.members.add(member);
    }

    public void addRelation(Relation relation) {
        this.relations.add(relation);
    }


    public Relation findBornRelation(Member m) throws NotUniqueBornRelationException {
        List<Relation> list = relations.filtered(relation -> relation.getChildren().contains(m));

        if (list.isEmpty()) {
            return null;
        }

        if (list.size() > 1) {
            throw new NotUniqueBornRelationException();
        }

        return list.get(0);
    }



    /*
        GETTERS
     */

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

    @XmlElement
    public void setName(String name) {
        this.name.set(name);
    }

/*
        SETTERS
     */

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
}

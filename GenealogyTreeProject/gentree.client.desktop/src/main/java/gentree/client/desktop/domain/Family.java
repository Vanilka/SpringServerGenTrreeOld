package gentree.client.desktop.domain;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
@Getter
@Setter
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



    /*
        GETTERS
     */

    public LongProperty versionProperty() {
        return version;
    }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }


    /*
        SETTERS
     */

    public void setVersion(long version) {
        this.version.set(version);
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }
}

package com.genealogytree.client.desktop.domain;


import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
 * Created by vanilka on 22/11/2016.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)

@Log4j2
@XmlRootElement
public class GTX_Family implements Serializable {


    private static final long serialVersionUID = -7356882826049849553L;

    private LongProperty version;
    private LongProperty id;
    private StringProperty name;
    private ObservableList<GTX_Member> membersList;
    private ObservableList<GTX_Relation> relationsList;

    {
        version = new SimpleLongProperty();
        id = new SimpleLongProperty();
        name = new SimpleStringProperty();
        membersList = FXCollections.observableArrayList();
        relationsList = FXCollections.observableArrayList();
    }

    public GTX_Family() {
        super();
    }



    public GTX_Family(String name) {
        this.version.setValue(null);
        this.id.setValue(null);
        this.name.setValue(name);
    }


    public void addMember(GTX_Member member) {
        membersList.add(member);
    }

    public void removeMember(GTX_Member member) {
        membersList.remove(member);
    }

    public void addRelation(GTX_Relation relation) {
        relationsList.add(relation);
    }

    public void removeRelation(GTX_Relation relation) {
        relationsList.remove(relation);
    }

    public GTX_Relation getBornRelation(GTX_Member member) {

        GTX_Relation relation = relationsList.stream()
                .filter(r -> r.getChildren().contains(member))
                .findFirst().orElse(null);


        if( relation == null) {
            relation = new GTX_Relation(null, null, member);
            relationsList.add(relation);
        }
        return  relation;
    }

    /*
     * GETTER
     */

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getVersion() {
        return version.get();
    }

    public LongProperty versionProperty() {
        return version;
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    @XmlElementWrapper(name="members")
    @XmlElements({ @XmlElement(name = "member", type = GTX_Member.class) })
    public ObservableList<GTX_Member> getMembersList() {
        return membersList;
    }


    @XmlElementWrapper(name="relations")
    @XmlElements({ @XmlElement(name = "relation", type = GTX_Relation.class) })
    public ObservableList<GTX_Relation> getRelationsList() {
        return relationsList;
    }

/*
     * SETTER
     */

    public void setVersion(long version) {
        this.version.set(version);
    }

    @XmlElement
    public void setId(long id) {
        this.id.set(id);
    }

    @XmlElement
    public void setName(String name) {
        this.name.set(name);
    }

    public void setMembersList(ObservableList<GTX_Member> membersList) {
        this.membersList = membersList;
    }

    public void setRelationsList(ObservableList<GTX_Relation> relationsList) {
        this.relationsList = relationsList;
    }


    @Override
    public String toString() {
        return "GTX_Family{" +
                "version=" + version +
                ", id=" + id +
                ", name=" + name +
                ", membersList=" + membersList +
                ", relationsList=" + relationsList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GTX_Family)) return false;

        GTX_Family that = (GTX_Family) o;

        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (membersList != null ? !membersList.equals(that.membersList) : that.membersList != null)
            return false;
        return relationsList != null ? relationsList.equals(that.relationsList) : that.relationsList == null;

    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (membersList != null ? membersList.hashCode() : 0);
        result = 31 * result + (relationsList != null ? relationsList.hashCode() : 0);
        return result;
    }
}

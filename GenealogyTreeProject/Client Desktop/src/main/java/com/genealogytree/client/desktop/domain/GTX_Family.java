package com.genealogytree.client.desktop.domain;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vanilka on 22/11/2016.
 */
public class GTX_Family implements Serializable, Observable {


    private static final long serialVersionUID = -7356882826049849553L;

    private LongProperty version;
    private LongProperty id;
    private StringProperty name;

    private ObservableList<GTX_Member> gtx_membersList;
    private ObservableList<GTX_Relation> gtx_relations;

    {
        version = new SimpleLongProperty();
        id = new SimpleLongProperty();
        name = new SimpleStringProperty();
        gtx_membersList = FXCollections.observableArrayList();
        gtx_relations = FXCollections.observableArrayList();
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
        gtx_membersList.add(member);
    }

    public void addRelation(GTX_Relation relation) {
        gtx_relations.add(relation);
    }

    public GTX_Relation getBornRelation(GTX_Member member) {
        GTX_Relation relation = gtx_relations.stream().filter(r -> r.getChildren().contains(member)).findFirst()
                .orElse(null);

        if( relation == null) {
            relation = new GTX_Relation(null, null, member);
            gtx_relations.add(relation);
        }
        return  relation;
    }

    /**
     * GETTER
     */

    public Long getVersion() {
        return version.getValue();
    }

    /**
     * SETTER
     */

    public void setVersion(Long version) {
        this.version.setValue(version);
    }

    public Long getId() {
        return id.getValue();
    }

    public void setId(Long id) {

        this.id.setValue(id);
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public StringProperty getNameProperty() {
        return this.name;
    }

    public ObservableList<GTX_Member> getGtx_membersList() {
        return gtx_membersList;
    }

    public void setGtx_membersList(ObservableList<GTX_Member> gtx_membersList) {
        this.gtx_membersList = gtx_membersList;
    }

    public ObservableList<GTX_Relation> getGtx_relations() {
        return gtx_relations;
    }

    public void setGtx_relations(List<GTX_Relation> gtx_relations) {
        this.gtx_relations.clear();
        this.gtx_relations.addAll(gtx_relations);
    }


    public void setGtx_membersList(List<GTX_Member> gtx_membersList) {
        this.gtx_membersList.clear();
        this.gtx_membersList.addAll(gtx_membersList);
    }

    public void setGtx_relations(ObservableList<GTX_Relation> gtx_relations) {
        this.gtx_relations = gtx_relations;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }


    @Override
    public String toString() {
        return "GTX_Family{" +
                "version=" + version +
                ", id=" + id +
                ", name=" + name +
                ", gtx_membersList=" + gtx_membersList +
                ", gtx_relations=" + gtx_relations +
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
        if (gtx_membersList != null ? !gtx_membersList.equals(that.gtx_membersList) : that.gtx_membersList != null)
            return false;
        return gtx_relations != null ? gtx_relations.equals(that.gtx_relations) : that.gtx_relations == null;

    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gtx_membersList != null ? gtx_membersList.hashCode() : 0);
        result = 31 * result + (gtx_relations != null ? gtx_relations.hashCode() : 0);
        return result;
    }
}

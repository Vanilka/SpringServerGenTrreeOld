package com.genealogytree.domain;

import com.genealogytree.configuration.Listenable;

import com.genealogytree.domain.beans.FamilyBean;
import com.genealogytree.domain.beans.UserBean;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeSupport;
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
    {
        version = new SimpleLongProperty();
        id = new SimpleLongProperty();
        name = new SimpleStringProperty();
        gtx_membersList = FXCollections.observableArrayList();
    }

    public GTX_Family() {
        super();
    }

    public GTX_Family(FamilyBean bean) {
        this.id.setValue(bean.getId());
        this.name.setValue(bean.getName());
        this.version.setValue(bean.getVersion());
    };

    public  GTX_Family(String name) {
        this.version.setValue(null);
        this.id.setValue(null);
        this.name.setValue(name);
    }


    public void addMember(GTX_Member member) {
        gtx_membersList.add(member);
    }

    /**
     * GETTER
     */

    public Long getVersion() {
        return version.getValue();
    }

    public Long getId() {
        return id.getValue();
    }

    public String getName() {
        return name.getValue();
    }
    public StringProperty getNameProperty() {
        return this.name;
    }

    public ObservableList<GTX_Member> getGtx_membersList() {
        return gtx_membersList;
    }

    public FamilyBean getFamilyBean() {
        FamilyBean bean = new FamilyBean();
        bean.setVersion(getVersion());
        bean.setId(getId());
        bean.setName(getName());
        return bean;
    }

    /**
     * SETTER
     */

    public void setVersion(Long version) {
        this.version.setValue(version);
    }

    public void setId(Long id) {

        this.id.setValue(id);
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setGtx_membersList(List<GTX_Member> gtx_membersList) {
        this.gtx_membersList.clear();
        this.gtx_membersList.addAll(gtx_membersList);
    }

    public void updateFromFamilyBean(FamilyBean bean) {
        this.id.setValue(bean.getId());
        this.name.setValue(bean.getName());
        this.version.setValue(bean.getVersion());
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
}

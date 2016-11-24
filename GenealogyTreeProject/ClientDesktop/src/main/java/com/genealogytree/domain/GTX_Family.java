package com.genealogytree.domain;

import com.genealogytree.configuration.Listenable;

import com.genealogytree.domain.beans.FamilyBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.List;

/**
 * Created by vanilka on 22/11/2016.
 */
public class GTX_Family extends FamilyBean implements Serializable, Listenable {


    private static final long serialVersionUID = -7356882826049849553L;

    private ObservableList<GTX_Member> gtx_membersList;
    private PropertyChangeSupport pcs;

    {
        pcs = new PropertyChangeSupport(this);
        gtx_membersList = FXCollections.observableArrayList();
    }

    public GTX_Family() {
        super();
    }

    public GTX_Family(String name) {
        super(name);
    }

    public void addMember(GTX_Member member) {
        gtx_membersList.add(member);
    }

    /**
     * GETTER
     */

    public Long getVersion() {
        return version;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ObservableList<GTX_Member> getGtx_membersList() {
        return gtx_membersList;
    }

    /**
     * SETTER
     */

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        pcs.firePropertyChange("name", oldName, this.name);
    }

    public void setGtx_membersList(List<GTX_Member> gtx_membersList) {
        this.gtx_membersList.clear();
        this.gtx_membersList.addAll(gtx_membersList);
    }

    @Override
    public PropertyChangeSupport getPropertyChangeSupport() {
        return this.pcs;
    }
}

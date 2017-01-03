/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.domain.beans;

import java.io.Serializable;

/**
 * @author vanilka
 */
public class FamilyBean implements Serializable {

    private static final long serialVersionUID = 7150538245398932223L;

    protected Long version;
    protected Long id;
    protected String name;
    private UserBean owner;

    /**
     Constructor
     */
    public FamilyBean() {
        this(null);
    }

    public FamilyBean(String name) {
        super();
        this.name = name;
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

    public UserBean getOwner() {
        return owner;
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
        this.name = name;
    }

    public void setOwner(UserBean owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "FamilyBean{" +
                "version=" + version +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                '}';
    }
}

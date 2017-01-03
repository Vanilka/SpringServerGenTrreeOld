/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.domain.beans;

import com.genealogytree.domain.enums.RelationType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vanilka
 */
public class RelationBean {

    protected Long version;
    protected Long id;
    protected Boolean active;
    protected RelationType relationType;
    private MemberBean SimLeft;
    private MemberBean SimRight;
    private List<MemberBean> children;
    private FamilyBean ownerF;

    {
        children = new ArrayList<>();
    }

    public  RelationBean() {
        this.relationType = RelationType.NEUTRAL;
        this.active = true;
    }

    public RelationBean(RelationType type, Boolean active) {
        if (type == null) {
            type = RelationType.NEUTRAL;
        }
        if (active == null) {
            active = true;
        }
        this.relationType = type;
        this.active = active;
    }

    /*
    * GETTERS
     */

    public Long getVersion() {
        return version;
    }

    public Long getId() {
        return id;
    }

    public MemberBean getSimLeft() {
        return SimLeft;
    }

    public MemberBean getSimRight() {
        return SimRight;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public Boolean getActive() {
        return active;
    }

    public List<MemberBean> getchildren() {
        return children;
    }

    public FamilyBean getOwnerF() {
        return ownerF;
    }
    /*
    * SETTERS
     */

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSimLeft(MemberBean simLeft) {
        SimLeft = simLeft;
    }

    public void setSimRight(MemberBean simRight) {
        SimRight = simRight;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setchildren(List<MemberBean> children) {
        this.children = children;
    }

    public void setOwnerF(FamilyBean owner) {
        this.ownerF = owner;
    }

    @Override
    public String toString() {
        return "RelationBean{" +
                "version=" + version +
                ", id=" + id +
                ", active=" + active +
                ", relationType=" + relationType +
                ", SimLeft=" + SimLeft +
                ", SimRight=" + SimRight +
                ", children=" + children +
                ", ownerF=" + ownerF +
                '}';
    }
}

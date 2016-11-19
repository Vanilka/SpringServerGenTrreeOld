/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.domain.beans;

/**
 * @author vanilka
 */
public class RelationBean {

    private Long id;
    private MemberBean SimLeft;
    private MemberBean SimRight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MemberBean getSimLeft() {
        return SimLeft;
    }

    public void setSimLeft(MemberBean SimLeft) {
        this.SimLeft = SimLeft;
    }

    public MemberBean getSimRight() {
        return SimRight;
    }

    public void setSimRight(MemberBean SimRight) {
        this.SimRight = SimRight;
    }


}

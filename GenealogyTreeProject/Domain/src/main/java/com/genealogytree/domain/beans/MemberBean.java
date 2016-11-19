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
public class MemberBean implements Serializable {

    private static final long serialVersionUID = -8338241168394692965L;

    protected Long id;
    protected String name;
    protected String surname;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}

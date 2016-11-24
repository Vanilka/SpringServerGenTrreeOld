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

    public enum Age {
        BABY, CHILD, ADO, YOUNG_ADULT, ADULT, SENIOR;
    }

    public enum Sex {
        MALE, FEMALE
    }

    protected Long version;
    protected Long id;
    protected String name;
    protected String surname;
    protected Age age;
    protected Sex sex;


    /**
     Constructor
     */
    public MemberBean() {
        this(null, null, null, null);
    }

    public MemberBean(String name, String surname, Age age, Sex sex) {
        if(age == null) {
            age = Age.YOUNG_ADULT;
        }

        if(sex == null) {
            sex = Sex.MALE;
        }

        this.name = name;
        this.surname = surname;
        this.age = age;
        this.sex = sex;



    }



    /*
    *  GETTERS
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

    public String getSurname() {
        return surname;
    }

    public Age getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    /*
    *  SETTERS
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

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}

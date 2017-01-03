/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.domain.beans;

import com.genealogytree.domain.enums.Age;
import com.genealogytree.domain.enums.Sex;

import java.io.Serializable;

/**
 * @author vanilka
 */
public class MemberBean implements Serializable {

    private static final long serialVersionUID = -8338241168394692965L;

    protected Long version;
    protected Long id;
    protected String name;
    protected String surname;
    protected Age age;
    protected Sex sex;
    private FamilyBean ownerF;
    private ImageBean image;


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

    public FamilyBean getOwnerF() {
        return ownerF;
    }

    public ImageBean getImage() {
        return image;
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

    public void setOwnerF(FamilyBean ownerF) {
        this.ownerF = ownerF;
    }

    public void setImage(ImageBean image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "MemberBean{" +
                "version=" + version +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", ownerF=" + ownerF +
                ", image=" + image +
                '}';
    }
}

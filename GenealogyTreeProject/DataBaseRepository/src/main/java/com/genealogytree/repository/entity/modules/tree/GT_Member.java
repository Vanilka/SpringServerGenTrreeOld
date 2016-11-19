package com.genealogytree.repository.entity.modules.tree;

import com.genealogytree.domain.beans.MemberBean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Member")
public class GT_Member extends MemberBean implements Serializable {


    /**
     * This is an Entity Class represent  Member in Database
     */
    @Version
    private Long version;

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String age;

    @Column
    private String sex;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }


}

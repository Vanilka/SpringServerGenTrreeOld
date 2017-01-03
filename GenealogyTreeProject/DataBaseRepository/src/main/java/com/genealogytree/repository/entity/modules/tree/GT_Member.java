package com.genealogytree.repository.entity.modules.tree;

import com.genealogytree.domain.beans.MemberBean;
import com.genealogytree.domain.enums.Age;
import com.genealogytree.domain.enums.Sex;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Member")
public class GT_Member extends MemberBean implements Serializable {

    /**
     * This is an Entity Class represent  Member in Database
     */
    private GT_Family ownerF;
    private GT_Images image;

    /**
     * Constructor
     */
    public GT_Member() {
        super();
    }

    public GT_Member(String name, String surname, Age age, Sex sex, GT_Family ownerF) {
        super(name, surname, age, sex);
        this.ownerF = ownerF;
    }

    public GT_Member(String name, String surname, Age age, Sex sex, GT_Family ownerF, GT_Images image) {
        super(name, surname, age, sex);
        this.ownerF = ownerF;
        this.image = image;
    }

    /*
    *  GETTERS
    */
    @Override
    @Version
    public Long getVersion() {
        return version;
    }

    @Override
    @Id
    @GeneratedValue(generator = "InvSeqMember")
    @SequenceGenerator(name = "InvSeqMember", sequenceName = "INV_SEQMember", allocationSize = 5)
    public Long getId() {
        return id;
    }

    @Override
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @Override
    @Column(nullable = false)
    public String getSurname() {
        return surname;
    }

    @Override
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    public Age getAge() {
        return age;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "ownerF_ID", nullable = false)
    public GT_Family getOwnerF() {
        return ownerF;
    }

    @Override
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    public Sex getSex() {
        return sex;
    }


    @OneToOne(optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Image_ID", nullable = true)
    public GT_Images getImage() {
        return image;
    }

    /*
    * SETTERS
    */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public void setAge(Age age) {
        this.age = age;
    }

    @Override
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setOwnerF(GT_Family ownerF) {
        this.ownerF = ownerF;
    }

    public void setImage(GT_Images image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "GT_Member{} " + super.toString();
    }
}

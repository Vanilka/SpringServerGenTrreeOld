package com.genealogytree.domain;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.configuration.ImageFiles;
import com.genealogytree.domain.beans.ImageBean;
import com.genealogytree.domain.beans.MemberBean;
import com.genealogytree.domain.enums.Age;
import com.genealogytree.domain.enums.Sex;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by vanilka on 22/11/2016.
 */
public class GTX_Member {

    private LongProperty version;
    private LongProperty id;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty photo;
    private Age age;
    private Sex sex;

    {
        this.version = new SimpleLongProperty();
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.photo = new SimpleStringProperty();
    }

    /*
    *  CONSTRUCTOR
     */

    public GTX_Member() {
        this(null, null, Age.YOUNG_ADULT, Sex.MALE, null);
    }

    public GTX_Member(String name, String surname, Age age, Sex sex, String photo) {
        setName(name);
        setSurname(surname);
        setAge(age);
        setSex(sex);
        setPhoto(photo);
    }

    /*
     *  GETTER
     */

    public Long getVersion() {
        return version.get();
    }

    public LongProperty versionProperty() {
        return version;
    }

    public Long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public Age getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public String getPhoto() {
        if (photo.getValue() != null) {
            return photo.get();
        } else if (this.getSex().equals(Sex.FEMALE)) {
            return ImageFiles.GENERIC_FEMALE.toString();
        } else {
            return ImageFiles.GENERIC_MALE.toString();
        }
    }

    public StringProperty photoProperty() {
        return photo;
    }

    /*
    * SETTER
     */
    public void setVersion(long version) {
        this.version.set(version);
    }

    public void setId(long id) {
        this.id.set(id);
    }


    public void setName(String name) {
        this.name.set(name);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setPhoto(String photo) {
        this.photo.set(photo);
    }

    @Override
    public String toString() {
        return "GTX_Member{" +
                "version=" + version +
                ", id=" + id +
                ", name=" + name +
                ", surname=" + surname +
                ", photo=" + photo +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GTX_Member)) return false;

        GTX_Member that = (GTX_Member) o;

        if (version.getValue() != null ? !version.getValue().equals(that.version.getValue()) : that.version.getValue() != null) return false;
        if (id.getValue() != null ? !id.getValue().equals(that.id.getValue()) : that.id.getValue() != null) return false;
        if (name.getValue() != null ? !name.getValue().equals(that.name.getValue()) : that.name.getValue() != null) return false;
        if (surname.getValue() != null ? !surname.getValue().equals(that.surname.getValue()) : that.surname.getValue() != null) return false;
        if (photo.getValue() != null ? !photo.getValue().equals(that.photo.getValue()) : that.photo.getValue() != null) return false;
        if (age != that.age) return false;
        return sex == that.sex;

    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        return result;
    }
}


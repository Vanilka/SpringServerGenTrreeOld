package com.genealogytree.domain;

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

    public GTX_Member(MemberBean bean) {
        setVersion(bean.getVersion());
        setId(bean.getId());
        setName(bean.getName());
        setSurname(bean.getSurname());
        setAge(bean.getAge());
        setSex(bean.getSex());
        if (bean.getImage() != null) {
            try {
                System.out.println("Test open");
                Path file = Paths.get(bean.getImage().getName() + ".png");
                Files.write(file, bean.getImage().getContent());
                setPhoto(file.toAbsolutePath().toString());
            } catch (Exception e) {
                System.out.println("test error");
                e.printStackTrace();
            }
        }
    }

    /*
     *  GETTER
     */

    public MemberBean getMemberBean() {
        MemberBean bean = new MemberBean();
        bean.setVersion(getVersion());
        bean.setId(getId());
        bean.setName(getName());
        bean.setSurname(getSurname());
        bean.setSex(getSex());
        bean.setAge(getAge());
        if (photo.getValue() != null) {
            if (Files.exists(Paths.get(photo.get()))) {
                Path path = Paths.get(photo.get());
                try {
                    bean.setImage(new ImageBean(convertFileToArray(path)));
                } catch (Exception e) {

                }

            }
        }
        return bean;
    }

    public long getVersion() {
        return version.get();
    }

    public LongProperty versionProperty() {
        return version;
    }

    public long getId() {
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

    public void updateFromBean(MemberBean bean) {
        setVersion(bean.getVersion());
        setId(bean.getId());
        setName(bean.getName());
        setSurname(bean.getSurname());
        setAge(bean.getAge());
        setSex(bean.getSex());
        if (bean.getImage() != null) {
            try {
                System.out.println("Test open");
                Path file = Paths.get(bean.getImage().getName());
                Files.write(file, bean.getImage().getContent());
                setPhoto(file.toAbsolutePath().toString());
            } catch (Exception e) {
                System.out.println("test error");
                e.printStackTrace();
            }
        }
    }

    private byte[] convertFileToArray(File file) throws IOException {
        byte[] byteFile = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(byteFile);
        fileInputStream.close();
        return byteFile;
    }

    private byte[] convertFileToArray(Path file) throws IOException {
        byte[] byteFile;
        byteFile = Files.readAllBytes(file);
        return byteFile;
    }


}


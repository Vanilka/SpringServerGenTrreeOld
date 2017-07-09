package gentree.client.desktop.domain;

import gentree.client.desktop.domain.enums.Age;
import gentree.client.desktop.domain.enums.Gender;
import gentree.client.desktop.domain.enums.Race;
import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */
public class Member implements Serializable {

    private static final long serialVersionUID = 7165660080715738518L;

    private LongProperty id;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty bornname;
    private StringProperty photo;

    private ObjectProperty<Age> age;
    private ObjectProperty<Race> race;
    private ObjectProperty<Gender> gender;

    {
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.bornname = new SimpleStringProperty();
        this.photo = new SimpleStringProperty();
        this.age = new SimpleObjectProperty<>();
        this.race = new SimpleObjectProperty<>();
        this.gender = new SimpleObjectProperty<>();
    }

    public Member(String name, String surname, String bornname, Age age, Gender gender, String photoPath) {
        setName(name);
        setSurname(surname);
        setBornname(bornname);
        setAge(age);
        setGender(gender);
        setPhoto(photoPath);
    }

    /*
            GETTERS
     */

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

    public String getBornname() {
        return bornname.get();
    }

    public StringProperty bornnameProperty() {
        return bornname;
    }

    public String getPhoto() {
        return photo.get();
    }

    public StringProperty photoProperty() {
        return photo;
    }

    public Age getAge() {
        return age.get();
    }

    public ObjectProperty<Age> ageProperty() {
        return age;
    }

    public Race getRace() {
        return race.get();
    }

    public ObjectProperty<Race> raceProperty() {
        return race;
    }

    public Gender getGender() {
        return gender.get();
    }

    public ObjectProperty<Gender> genderProperty() {
        return gender;
    }

    /*
            SETTERS
     */

    public void setId(long id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setBornname(String bornname) {
        this.bornname.set(bornname);
    }

    public void setPhoto(String photo) {
        this.photo.set(photo);
    }

    public void setAge(Age age) {
        this.age.set(age);
    }

    public void setRace(Race race) {
        this.race.set(race);
    }

    public void setGender(Gender gender) {
        this.gender.set(gender);
    }
}

package gentree.client.desktop.domain;

import gentree.client.desktop.configuration.wrappers.LongAdapter;
import gentree.client.desktop.configuration.wrappers.PhotoMarshaller;
import gentree.client.desktop.domain.enums.Age;
import gentree.client.desktop.domain.enums.DeathCauses;
import gentree.client.desktop.domain.enums.Gender;
import gentree.client.desktop.domain.enums.Race;
import javafx.beans.property.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */
@XmlType(name = "sim")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Member implements Serializable {

    private static final long serialVersionUID = 7165660080715738518L;
    private static String DEFAULT_FEMALE_LOCATION = "";
    private static String DEFAULT_MALE_LOCATION = "";

    private LongProperty id;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty bornname;
    private StringProperty photo;

    private ObjectProperty<Age> age;
    private ObjectProperty<Race> race;
    private ObjectProperty<Gender> gender;
    private ObjectProperty<DeathCauses> deathCause;
    private BooleanProperty alive;


    {
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.bornname = new SimpleStringProperty();
        this.photo = new SimpleStringProperty();
        this.age = new SimpleObjectProperty<>(Age.YOUNG_ADULT);
        this.race = new SimpleObjectProperty<>(Race.HUMAIN);
        this.gender = new SimpleObjectProperty<>(Gender.M);
        this.deathCause = new SimpleObjectProperty<>();
        this.alive = new SimpleBooleanProperty(true);
    }

    public Member() {
        this(null, null, null, null, null, null, null, false, null);
    }

    public Member(String name, String surname, String bornname, String photoPath,
                  Age age, Gender gender, Race race, Boolean isAlive, DeathCauses deathCause) {
        setName(name);
        setSurname(surname);
        setBornname(bornname);
        setPhoto(photoPath);
        setAge(age);
        setGender(gender);
        setRace(race);
        setAlive(isAlive);
        if (!isAlive) {
            setDeathCause(deathCause);
        }

    }

    /*
            GETTERS
     */

    public static void setDefaultFemaleLocation(String defaultFemaleLocation) {
        DEFAULT_FEMALE_LOCATION = defaultFemaleLocation;
    }

    public static void setDefaultMaleLocation(String defaultMaleLocation) {
        DEFAULT_MALE_LOCATION = defaultMaleLocation;
    }

    @XmlID
    @XmlJavaTypeAdapter(type = long.class, value = LongAdapter.class)
    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public String getBornname() {
        return bornname.get();
    }

    public void setBornname(String bornname) {
        this.bornname.set(bornname);
    }

    public StringProperty bornnameProperty() {
        return bornname;
    }

    @XmlJavaTypeAdapter(PhotoMarshaller.class)
    @XmlElement(nillable = true)
    public String getPhoto() {
        if (photo.getValue() != null && !photo.getValue().equals("")) {
            return photo.get();
        } else if (getGender().equals(Gender.F)) {
            return DEFAULT_FEMALE_LOCATION;
        } else {
            return DEFAULT_MALE_LOCATION;
        }
    }

    public void setPhoto(String photo) {
        this.photo.set(photo);
    }

    public StringProperty photoProperty() {
        return photo;
    }

    public Age getAge() {
        return age.get();
    }

    public void setAge(Age age) {
        this.age.set(age);
    }

    public ObjectProperty<Age> ageProperty() {
        return age;
    }

    /*
            SETTERS
     */

    public Race getRace() {
        return race.get();
    }

    public void setRace(Race race) {
        this.race.set(race);
    }

    public ObjectProperty<Race> raceProperty() {
        return race;
    }

    public Gender getGender() {
        return gender.get();
    }

    public void setGender(Gender gender) {
        this.gender.set(gender);
    }

    public ObjectProperty<Gender> genderProperty() {
        return gender;
    }

    public DeathCauses getDeathCause() {
        return deathCause.get();
    }

    public void setDeathCause(DeathCauses deathCause) {
        this.deathCause.set(deathCause);

        if (deathCause != null) {
            setAlive(false);
        }
    }

    public ObjectProperty<DeathCauses> deathCauseProperty() {
        return deathCause;
    }

    public boolean isAlive() {
        return alive.get();
    }

    public void setAlive(boolean alive) {
        this.alive.set(alive);
        if (alive) {
            setDeathCause(null);
        }
    }

    public BooleanProperty aliveProperty() {
        return alive;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Member{");
        sb.append("id=").append(getId());
        sb.append(", name=").append(getName());
        sb.append(", surname=").append(getSurname());
        sb.append(", bornname=").append(getBornname());
        sb.append(", photo=").append(getPhoto());
        sb.append(", age=").append(getAge());
        sb.append(", race=").append(getRace());
        sb.append(", gender=").append(getGender());
        sb.append(", deathCause=").append(getDeathCause());
        sb.append(", alive=").append(isAlive());
        sb.append('}');
        return sb.toString();
    }


    public Stream<? extends Property> getProperties() {
        return Stream.of(this.id, this.name, this.surname, this.bornname, this.gender, this.age, this.race, this.deathCause);
    }
}

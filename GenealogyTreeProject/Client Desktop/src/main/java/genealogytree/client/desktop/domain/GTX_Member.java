package genealogytree.client.desktop.domain;

import genealogytree.client.desktop.configuration.enums.ImageFiles;
import genealogytree.client.desktop.configuration.helper.PhotoMarshaller;
import genealogytree.client.desktop.configuration.helper.WSLongAdapter;
import genealogytree.domain.enums.Age;
import genealogytree.domain.enums.Sex;
import javafx.beans.property.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.stream.Stream;


/**
 * Created by vanilka on 22/11/2016.
 */

@XmlType(name = "member")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class GTX_Member implements Serializable {


    private static final long serialVersionUID = 4538142934143496380L;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private LongProperty version;

    private LongProperty id;

    private StringProperty name;

    private StringProperty surname;

    private StringProperty bornname;

    private StringProperty photo;

    private ObjectProperty<Age> age;

    private ObjectProperty<Sex> sex;

    {
        this.version = new SimpleLongProperty();
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.bornname = new SimpleStringProperty();
        this.photo = new SimpleStringProperty();
        this.age = new SimpleObjectProperty<>();
        this.sex = new SimpleObjectProperty<>();
    }

    /*
    *  CONSTRUCTOR
     */

    public GTX_Member() {
        this(null, null, null, Age.YOUNG_ADULT, Sex.MALE, null);
    }

    public GTX_Member(String name, String surname, String bornname, Age age, Sex sex, String photo) {
        setName(name);
        setSurname(surname);
        setBornname(bornname);
        setAge(age);
        setSex(sex);
        setPhoto(photo);
    }


    /*
     *  GETTER
     */

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void insertValues(GTX_Member to, GTX_Member from) {
        to.setId(from.getId());
        to.setVersion(from.getVersion());
        to.setName(from.getName());
        to.setSurname(from.getSurname());
        to.setBornname(from.getBornname());
        if (from.getPhoto().equals(ImageFiles.GENERIC_MALE.toString())
                || from.getPhoto().equals(ImageFiles.GENERIC_FEMALE.toString())) {
            to.setPhoto(null);
        } else {
            to.setPhoto(from.getPhoto().replace("file:///", ""));
        }
        to.setSex(from.getSex());
        to.setAge(from.getAge());
    }

    @XmlJavaTypeAdapter(PhotoMarshaller.class)
    @XmlElement(nillable = true)
    public String getPhoto() {
        if (photo.getValue() != null && ! photo.getValue().equals("")) {
            return "file:///" + photo.get();
        } else if (this.getSex().equals(Sex.FEMALE)) {
            return ImageFiles.GENERIC_FEMALE.toString();
        } else {
            return ImageFiles.GENERIC_MALE.toString();
        }
    }

    public void setPhoto(String photo) {
        this.photo.set(photo);

    }

    public String getBornname() {
        return bornname.get();
    }

    public void setBornname(String bornname) {
        this.bornname.set(bornname);

    }

    public long getVersion() {
        return version.get();
    }

    public void setVersion(long version) {
        this.version.set(version);


    }

    public LongProperty versionProperty() {
        return version;
    }

    @XmlID
    @XmlJavaTypeAdapter(type=long.class, value=WSLongAdapter.class)
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

    /*
    * SETTER
     */

    public StringProperty surnameProperty() {
        return surname;
    }

    public StringProperty bornnameProperty() {
        return bornname;
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

    public Sex getSex() {
        return sex.get();
    }

    public void setSex(Sex sex) {
        this.sex.set(sex);
    }

    public ObjectProperty<Sex> sexProperty() {
        return sex;
    }

    public void addChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removeChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
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
        if (version.getValue() != null ? !version.getValue().equals(that.version.getValue()) : that.version.getValue() != null)
            return false;
        if (id.getValue() != null ? !id.getValue().equals(that.id.getValue()) : that.id.getValue() != null)
            return false;
        if (name.getValue() != null ? !name.getValue().equals(that.name.getValue()) : that.name.getValue() != null)
            return false;
        if (surname.getValue() != null ? !surname.getValue().equals(that.surname.getValue()) : that.surname.getValue() != null)
            return false;
        if (age.getValue() != that.age.getValue()) return false;
        return sex.getValue() == that.sex.getValue();
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

    public Stream<? extends Property> getProperties() {
        return Stream.of(this.version, this.id, this.name, this.surname, this.bornname, this.sex, this.age);
    }
}


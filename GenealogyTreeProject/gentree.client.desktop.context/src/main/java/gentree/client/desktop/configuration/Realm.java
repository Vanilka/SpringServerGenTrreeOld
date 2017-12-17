package gentree.client.desktop.configuration;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Martyna SZYMKOWIAK on 20/10/2017.
 */
@XmlType(name = "realm")
public class Realm {

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();


    public Realm() {
    }

    public Realm(String name, String address) {
        this.name.setValue(name);
        this.address.setValue(address);
    }

    /*
            GETTERS AND SETTERS
     */

    @XmlAttribute
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @XmlAttribute
    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Realm)) return false;

        Realm that = (Realm) o;

        if (name.getValue() != null ? !name.getValue().equals(that.name.getValue()) : that.name.getValue() != null)
            return false;
        return (address.getValue() != null ? !address.getValue().equals(that.address.getValue()) : that.address.getValue() != null);
    }
}

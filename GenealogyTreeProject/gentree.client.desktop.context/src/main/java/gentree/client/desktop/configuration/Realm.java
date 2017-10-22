package gentree.client.desktop.configuration;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Martyna SZYMKOWIAK on 20/10/2017.
 */
@Getter
@Setter
@XmlType(name = "realm")
public class Realm {

    @XmlElement
    private final StringProperty name = new SimpleStringProperty();

    @XmlElement
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
}

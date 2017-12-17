package gentree.client.desktop.configuration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import javax.xml.bind.annotation.*;

/**
 * Created by Martyna SZYMKOWIAK on 20/10/2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RealmConfig {

    @Getter
    @XmlElementWrapper(name = "Realms")
    @XmlElements({@XmlElement(name = "realm", type = Realm.class)})
    private final ObservableList<Realm> realms = FXCollections.observableArrayList();

    public RealmConfig() {
    }

    private void addRealm(String name, String address) {
        realms.add(new Realm(name, address));
    }

    private void addRealm(Realm realm) {
        if (realm == null) throw new NullPointerException();
        realms.add(realm);
    }

    public RealmConfig copy() {

        RealmConfig newRealmConfig = new RealmConfig();

        realms.forEach(element -> {
            newRealmConfig.addRealm(new Realm(
                    element.getName(),
                    element.getAddress()
            ));
        });

        return newRealmConfig;
    }

}

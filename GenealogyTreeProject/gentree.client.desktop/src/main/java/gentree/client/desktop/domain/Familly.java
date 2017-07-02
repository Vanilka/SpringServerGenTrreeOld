package gentree.client.desktop.domain;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
@Getter
@Setter
@Log4j2
@XmlRootElement
public class Familly implements Serializable {

    private static final long serialVersionUID = 1903864641139878342L;

    private LongProperty version;
    private LongProperty id;
    private StringProperty name;

    {
        version = new SimpleLongProperty();
        id = new SimpleLongProperty();
        name = new SimpleStringProperty();
    }

    public Familly() {
        super();
    }

    public Familly(String name) {
        this.name.setValue(name);
    }


}

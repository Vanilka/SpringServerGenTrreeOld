package gentree.client.desktop.domain;

import gentree.common.configuration.enums.RoleEnum;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */

public class Owner implements Serializable {

    private static final long serialVersionUID = 2461656975856786975L;


    private Long version;
    private Long id;
    private StringProperty login = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private ObjectProperty<RoleEnum> role = new SimpleObjectProperty<>(RoleEnum.USER);

    /*
        GETTERS AND SETTERS
     */

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public RoleEnum getRole() {
        return role.get();
    }

    public ObjectProperty<RoleEnum> roleProperty() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role.set(role);
    }
}

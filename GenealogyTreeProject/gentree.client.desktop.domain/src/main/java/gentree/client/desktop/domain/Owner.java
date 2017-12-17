package gentree.client.desktop.domain;

import gentree.common.configuration.enums.RoleEnum;
import javafx.beans.property.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */

public class Owner implements Serializable {

    private static final long serialVersionUID = 2461656975856786975L;


    private LongProperty version = new SimpleLongProperty();
    private LongProperty id = new SimpleLongProperty();
    private StringProperty login = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private ObjectProperty<RoleEnum> role = new SimpleObjectProperty<>(RoleEnum.USER);

    /*
        GETTERS AND SETTERS
     */

    public long getVersion() {
        return version.get();
    }

    public void setVersion(long version) {
        this.version.set(version);
    }

    public LongProperty versionProperty() {
        return version;
    }

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public StringProperty loginProperty() {
        return login;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public RoleEnum getRole() {
        return role.get();
    }

    public void setRole(RoleEnum role) {
        this.role.set(role);
    }

    public ObjectProperty<RoleEnum> roleProperty() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Owner)) return false;
        if (obj == this) return true;

        Owner owner = (Owner) obj;

        return Objects.equals(getVersion(), owner.getVersion())
                && Objects.equals(getId(), owner.getId())
                && Objects.equals(getLogin(), owner.getLogin())
                && Objects.equals(getPassword(), owner.getPassword())
                && Objects.equals(getRole(), owner.getRole());
    }
}

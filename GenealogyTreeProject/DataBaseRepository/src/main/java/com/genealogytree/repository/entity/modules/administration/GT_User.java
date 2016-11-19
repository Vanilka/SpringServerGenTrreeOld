package com.genealogytree.repository.entity.modules.administration;

import com.genealogytree.domain.beans.UserBean;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Users")
public class GT_User extends UserBean implements Serializable {


    private static final long serialVersionUID = -4000453169268602110L;

    /**
     * This is an Entity Class represent User in Database
     */

    private String role;

    public GT_User() {
        super();
    }

    public GT_User(Long id) {
        super();
        this.id = id;
    }

    public GT_User(Long id, String login, String password) {
        super();
        this.id = id;
        this.login = login;
        this.password = password;

    }


    /**
     * GETTERS
     */

    @Version
    public Long getVersion() {
        return version;
    }

    /**
     * SETTERS
     */

    public void setVersion(Long version) {
        this.version = version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqU")
    @SequenceGenerator(name = "InvSeqU", sequenceName = "INV_SEQU", allocationSize = 5)
    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

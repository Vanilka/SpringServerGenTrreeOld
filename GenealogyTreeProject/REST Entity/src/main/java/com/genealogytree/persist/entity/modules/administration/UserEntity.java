package com.genealogytree.persist.entity.modules.administration;


import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Users")
@Setter
public class UserEntity implements Serializable {


    private static final long serialVersionUID = -4000453169268602110L;

    /**
     * This is an Entity Class represent User in Database
     */
    private Long version;
    private Long id;
    //  private String role;
    private String login;
    private String password;
    private String email;

    public UserEntity() {
        super();
    }

    public UserEntity(Long id) {
        super();
        this.id = id;
    }

    public UserEntity(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;

    }


    /*
     * GETTERS
     */

    @Version
    public Long getVersion() {
        return version;
    }


    @Id
    @GeneratedValue(generator = "InvSeqU")
    @SequenceGenerator(name = "InvSeqU", sequenceName = "INV_SEQU", allocationSize = 5)
    public Long getId() {

        return id;
    }

    @Column(nullable = false, unique = true)
    public String getLogin() {
        return login;
    }


    @Column(nullable = false)
    public String getPassword() {
        return password;
    }


    @Column(nullable = false)
    public String getEmail() {
        return email;
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.domain.beans;

import java.io.Serializable;

/**
 * @author vanilka
 */
public class UserBean implements Serializable{

    private static final long serialVersionUID = 969243196813782424L;

    protected Long version;
    protected Long id;
    protected String login;
    protected String email;
    protected String password;


    public UserBean() {
        this(null, null, null, null, null);
    }

    public UserBean(String email, String login, String password) {
        this(null, null, login, email, password);
    }

    public UserBean(Long version, Long id, String login, String email, String password) {
        this.version = version;
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
    }


    /**
     * GETTERS
     */
    public Long getVersion() {

        return version;
    }

    /**
     * SETTERS
     */
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
        return login;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }


}

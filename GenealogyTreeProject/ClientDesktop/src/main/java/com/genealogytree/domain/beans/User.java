/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.domain.beans;

/**
 *
 * @author vanilka
 */
public class User {
	private Long version;
    private Long id;
    private String login;
    private String email;
    private String password;
    
    
    public User() {
    	this(null, null, null, null, null);
    }
    
    public User(String email, String login, String password) {
    	this(null, null, login, email, password);
    }
    
    public User(Long version, Long id, String login, String email, String password ) {
    	this.version = version;
    	this.id = id;
    	this.login = login;
    	this.email = email;
    	this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
    
	
    
 
}

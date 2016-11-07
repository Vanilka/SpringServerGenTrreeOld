package com.genealogytree.repository.entity.modules.administration;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.usertype.UserCollectionType;

import com.genealogytree.repository.entity.modules.tree.GT_Family;


@Entity
@Table(name = "Users")
public class GT_User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4000453169268602110L;

	@Version
	private Long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String login;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;
	
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}




	
	
}

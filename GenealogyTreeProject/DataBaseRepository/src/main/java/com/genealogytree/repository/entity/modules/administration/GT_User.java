package com.genealogytree.repository.entity.modules.administration;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.genealogytree.repository.entity.modules.tree.GT_Family;

@Entity
@Table(name="Users")
public class GT_User {
	
	@Id
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String login;
	
	@Column(nullable=false)
	private String email;
	
	@OneToMany(mappedBy="owner")
	private List<GT_Family> projectList;
	
	private String role;

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

	public List<GT_Family> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<GT_Family> projectList) {
		this.projectList = projectList;
	}
	
	

}

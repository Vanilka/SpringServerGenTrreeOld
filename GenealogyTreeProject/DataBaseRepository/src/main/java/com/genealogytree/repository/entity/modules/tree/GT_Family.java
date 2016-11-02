package com.genealogytree.repository.entity.modules.tree;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.genealogytree.repository.entity.modules.administration.GT_User;

@Entity
@Table(name = "Family")
public class GT_Family  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -510208873710440627L;

	@Version
	private Long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@ManyToOne(optional=false, fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name="owner_id", nullable=false)
	private GT_User owner;

	public GT_Family() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public GT_User getOwner() {
		return owner;
	}

	public void setOwner(GT_User owner) {
		this.owner = owner;
	}
	
	public Long getOwnerId() {
		return this.owner.getId();
	}
	
	public void setOwnerById(Long id) {
		this.owner = new GT_User(id);
	}
}

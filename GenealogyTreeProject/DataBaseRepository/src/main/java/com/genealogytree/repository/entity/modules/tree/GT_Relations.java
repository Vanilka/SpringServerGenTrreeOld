package com.genealogytree.repository.entity.modules.tree;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Relations")
public class GT_Relations {

	@Id
	private Long id;
	
//	//@Column
//	//@ManyToOne	
//	private GT_Member simLeft;
//	
//	//@Column
//	//@ManyToOne
//	private GT_Member simRight;
	
	private String relationType;
	
	private Boolean isActive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public GT_Member getSimLeft() {
//		return simLeft;
//	}
//
//	public void setSimLeft(GT_Member simLeft) {
//		this.simLeft = simLeft;
//	}
//
//	public GT_Member getSimRight() {
//		return simRight;
//	}
//
//	public void setSimRight(GT_Member simRight) {
//		this.simRight = simRight;
//	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
}

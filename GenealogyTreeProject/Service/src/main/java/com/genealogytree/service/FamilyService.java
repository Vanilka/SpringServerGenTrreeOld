package com.genealogytree.service;

import java.util.List;

import com.genealogytree.repository.entity.modules.tree.GT_Family;

public interface FamilyService {
	

	public List<GT_Family> getAllFamilies();
	public GT_Family addFamily(GT_Family family);
	

}

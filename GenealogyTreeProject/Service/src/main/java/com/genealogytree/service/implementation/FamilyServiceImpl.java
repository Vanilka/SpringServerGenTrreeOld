package com.genealogytree.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genealogytree.repository.FamilyRepository;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.service.FamilyService;

@Service
@Transactional
@ComponentScan("com.genealogytree")
public class FamilyServiceImpl implements FamilyService {

	@Autowired
	FamilyRepository repository;

	public List<GT_Family> getAllFamilies() {
		 List<GT_Family> list = this.repository.findAll();
		 System.out.println("Wielkosc listy: " + list.size());
		return list;
	}

	public GT_Family addFamily(GT_Family family) {
		
		return this.repository.save(family);
	}
	
}

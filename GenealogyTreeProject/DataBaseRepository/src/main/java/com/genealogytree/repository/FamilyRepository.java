package com.genealogytree.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.repository.entity.modules.tree.GT_Family;

@Repository
public interface FamilyRepository extends JpaRepository<GT_Family, Long>{
	


}

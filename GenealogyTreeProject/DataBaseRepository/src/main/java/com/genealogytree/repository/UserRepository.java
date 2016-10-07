package com.genealogytree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genealogytree.repository.entity.GT_User;

@Repository
public interface UserRepository extends JpaRepository<GT_User, Long> {
	
}

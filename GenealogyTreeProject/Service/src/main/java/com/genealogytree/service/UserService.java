package com.genealogytree.service;

import java.util.List;

import com.genealogytree.repository.entity.modules.administration.GT_User;

public interface UserService {
	
		public GT_User addUser(GT_User user);
		public GT_User getUser(GT_User user);
		public GT_User findUserById(Long id);
		public List<GT_User> findAllUsersByName(String sample);

}

package com.genealogytree.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.genealogytree.repository.UserRepository;
import com.genealogytree.repository.entity.GT_User;
import com.genealogytree.service.UserService;

@Service
@ComponentScan("com.genealogytree")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	public GT_User addUser(GT_User user) {
		return this.repository.save(user);
		
	}

}

package com.genealogytree.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.genealogytree.repository.UserRepository;
import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.service.UserService;

@Service
@ComponentScan("com.genealogytree")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	public GT_User addUser(GT_User user) {
		return this.repository.save(user);
		
	}

	public GT_User getUser(GT_User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<GT_User> getAllUsers() {
		
		List<GT_User> list = repository.findAll();
		
		return list;
	}

	public GT_User findUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<GT_User> findAllUsersByName(String sample) {
		// TODO Auto-generated method stub
		return null;
	}

	public GT_User findUserByLogin(String login) {
		// TODO Auto-generated method stub
		GT_User user = repository.findByLogin(login);
		return user;
	}

	
	public boolean exist(String login) {		
		return this.repository.exist(login);
	}

}

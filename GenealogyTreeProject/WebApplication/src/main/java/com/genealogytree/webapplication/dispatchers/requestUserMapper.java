package com.genealogytree.webapplication.dispatchers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.service.UserService;

@RestController
@ComponentScan("com.genealogytree")
@RequestMapping("/user")
public class requestUserMapper {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResponseEntity<GT_User> addUser(@RequestBody GT_User newUser) {
		GT_User addesUser = this.userService.addUser(newUser);
		return new ResponseEntity<GT_User>(addesUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestBody String s) {
		
		return new ResponseEntity<String>(s, HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity <List<GT_User>> getAllUsers() {
		List<GT_User> list = this.userService.getAllUsers();
		return new ResponseEntity<List<GT_User>>(list, HttpStatus.OK);
	}

}

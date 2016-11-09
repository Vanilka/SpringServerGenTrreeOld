package com.genealogytree.webapplication;

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
import com.genealogytree.service.implementation.UserServiceImpl;


@RestController
@ComponentScan("com.genealogytree")
@RequestMapping("/")
public class MainRequestDispatcher {
	


	@RequestMapping(value = "", method= RequestMethod.GET)
	public ResponseEntity<String> getStartPage() {
		
		return new ResponseEntity<String>("Hallo World", HttpStatus.OK);
	}

}

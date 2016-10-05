package com.genealogytree.webapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RequestMapper {
	
	@RequestMapping(value = "", method=RequestMethod.GET)
	public ResponseEntity<String> getAllUsers() {
		return new ResponseEntity<String> ("Hello World", HttpStatus.OK);
	}
	

}

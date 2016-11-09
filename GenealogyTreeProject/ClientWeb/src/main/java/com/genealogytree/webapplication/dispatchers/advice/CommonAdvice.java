package com.genealogytree.webapplication.dispatchers.advice;


import javax.servlet.ServletException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.genealogytree.ExceptionManager.config.Causes;
import com.genealogytree.ExceptionManager.exception.ExceptionBean;
import com.genealogytree.ExceptionManager.exception.NotUniqueUserLoginException;


@ControllerAdvice(annotations={RestController.class, Controller.class}, assignableTypes=BasicAuthenticationEntryPoint.class)

@Order(Ordered.LOWEST_PRECEDENCE)
public class CommonAdvice {

	
	@ExceptionHandler(ServletException.class)
	public ResponseEntity<ExceptionBean> unauthorise(Exception e) {
		System.out.println(e.getMessage());
		ExceptionBean exception = new ExceptionBean(Causes.ANOTHER_CAUSE);
		return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionBean> handleException(Exception e) {
		System.out.println("Error Handle");
		System.out.println(e.getStackTrace().toString());
		System.out.println("Skrocona wersja");
		System.out.println(e.getCause());
		System.out.println(e.getMessage());
		ExceptionBean exception = new ExceptionBean(Causes.ANOTHER_CAUSE);
		return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}

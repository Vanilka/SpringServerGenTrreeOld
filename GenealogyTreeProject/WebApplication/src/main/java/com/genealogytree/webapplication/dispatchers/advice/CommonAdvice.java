package com.genealogytree.webapplication.dispatchers.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.genealogytree.ExceptionManager.config.Causes;
import com.genealogytree.ExceptionManager.exception.ExceptionBean;
import com.genealogytree.ExceptionManager.exception.NotUniqueUserLoginException;

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.LOWEST_PRECEDENCE)
public class CommonAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionBean> handleException(Exception e) {
		ExceptionBean exception = new ExceptionBean(Causes.ANOTHER_CAUSE);
		return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

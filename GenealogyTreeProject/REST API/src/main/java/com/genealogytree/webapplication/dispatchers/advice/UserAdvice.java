package com.genealogytree.webapplication.dispatchers.advice;

import com.genealogytree.ExceptionManager.config.Causes;
import com.genealogytree.ExceptionManager.exception.ExceptionBean;
import com.genealogytree.ExceptionManager.exception.NotUniqueUserLoginException;
import com.genealogytree.webapplication.dispatchers.requestUserMapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {requestUserMapper.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserAdvice {

    @ExceptionHandler(NotUniqueUserLoginException.class)
    public ResponseEntity<ExceptionBean> handleAlreadyFound(NotUniqueUserLoginException e) {
        ExceptionBean exception = new ExceptionBean(Causes.USER_ALREADY_EXIST);
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

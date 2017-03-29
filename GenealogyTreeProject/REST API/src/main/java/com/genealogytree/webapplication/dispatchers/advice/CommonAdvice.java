package com.genealogytree.webapplication.dispatchers.advice;


import com.genealogytree.ExceptionManager.config.Causes;
import com.genealogytree.ExceptionManager.exception.ExceptionBean;
import com.genealogytree.ExceptionManager.exception.NoVersionFieldInEntity;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;


@ControllerAdvice(annotations = {RestController.class, Controller.class}, assignableTypes = BasicAuthenticationEntryPoint.class)

@Order(Ordered.LOWEST_PRECEDENCE)
public class CommonAdvice {


    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ExceptionBean> unauthorise(Exception e) {
        System.out.println(e.getMessage());
        ExceptionBean exception = new ExceptionBean(Causes.ANOTHER_CAUSE);
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionBean> handleException(UsernameNotFoundException e) {
        ExceptionBean exception = new ExceptionBean(Causes.LOGIN_PASSWORD_INCORRECT);
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoVersionFieldInEntity.class)
    public ResponseEntity<ExceptionBean> handleException(NoVersionFieldInEntity e) {
        ExceptionBean exceptionBean = new ExceptionBean(e.getCausesInstance());
        return new ResponseEntity<ExceptionBean>(exceptionBean, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ExceptionBean> handleException(ObjectOptimisticLockingFailureException e) {
        ExceptionBean exceptionBean = new ExceptionBean(Causes.OPTIMISTIC_LOCK);
        return new ResponseEntity<ExceptionBean>(exceptionBean, HttpStatus.LOCKED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBean> handleException(Exception e) {
        System.out.println("Error Handle");
        System.out.println(e.getStackTrace().toString());
        e.printStackTrace();
        System.out.println("Skrocona wersja");
        System.out.println(e.getClass());
        System.out.println(e.getClass());
        System.out.println(e.getSuppressed());
        System.out.println(e.getMessage());
        ExceptionBean exception = new ExceptionBean(Causes.ANOTHER_CAUSE);
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

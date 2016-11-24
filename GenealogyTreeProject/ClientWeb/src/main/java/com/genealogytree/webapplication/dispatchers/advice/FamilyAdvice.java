package com.genealogytree.webapplication.dispatchers.advice;

import com.genealogytree.ExceptionManager.config.Causes;
import com.genealogytree.ExceptionManager.exception.ExceptionBean;
import com.genealogytree.ExceptionManager.exception.NotFoundFamilyException;
import com.genealogytree.ExceptionManager.exception.NotFoundUserException;
import com.genealogytree.ExceptionManager.exception.UserInstanceWithoutIdException;
import com.genealogytree.webapplication.dispatchers.requestFamilyMapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by vanilka on 09/11/2016.
 */

@ControllerAdvice(assignableTypes = {requestFamilyMapper.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FamilyAdvice {

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ExceptionBean> NotFoundUser(NotFoundUserException e) {
        ExceptionBean exception = new ExceptionBean(Causes.NOT_FOUND_USER);
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UserInstanceWithoutIdException.class)
    public ResponseEntity<ExceptionBean> UserInstanceWithoutIdInFamilyInistance(UserInstanceWithoutIdException e) {
        ExceptionBean exception = new ExceptionBean(Causes.USER_INISTANCE_WITHOUT_ID);
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundFamilyException.class)
    public ResponseEntity<ExceptionBean> NotFoundProject(NotFoundFamilyException e) {
        ExceptionBean exception = new ExceptionBean(Causes.PROJECT_NOT_FOUND);
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}


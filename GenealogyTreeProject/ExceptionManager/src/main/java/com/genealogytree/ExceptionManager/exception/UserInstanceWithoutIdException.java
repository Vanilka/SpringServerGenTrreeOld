package com.genealogytree.ExceptionManager.exception;

import com.genealogytree.ExceptionManager.config.Causes;

/**
 * Created by vanilka on 09/11/2016.
 */
public class UserInstanceWithoutIdException extends Exception {

    public  UserInstanceWithoutIdException() {this(Causes.USER_INISTANCE_WITHOUT_ID.toString());}

    public UserInstanceWithoutIdException(String message) {
        super(message);
    }

    public UserInstanceWithoutIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public Causes getCausesInstance() {
        return Causes.USER_INISTANCE_WITHOUT_ID;
    }
}

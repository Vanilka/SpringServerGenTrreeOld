package com.genealogytree.ExceptionManager.exception;

import com.genealogytree.ExceptionManager.config.Causes;

/**
 * Created by vanilka on 23/11/2016.
 */
public class UserOrPasswordIncorrectException extends Exception {

    private static final long serialVersionUID = -2893133059967822043L;

    public UserOrPasswordIncorrectException() {
        this(Causes.LOGIN_PASSWORD_INCORRECT.toString());
    }

    public UserOrPasswordIncorrectException(String message) {
        super(message);
    }

    public UserOrPasswordIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }
}

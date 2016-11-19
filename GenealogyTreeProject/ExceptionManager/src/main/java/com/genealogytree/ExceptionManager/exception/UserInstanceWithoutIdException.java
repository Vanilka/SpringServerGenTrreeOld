package com.genealogytree.ExceptionManager.exception;

/**
 * Created by vanilka on 09/11/2016.
 */
public class UserInstanceWithoutIdException extends Exception {

    public UserInstanceWithoutIdException(String message) {
        super(message);
    }

    public UserInstanceWithoutIdException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.genealogytree.ExceptionManager.exception;

/**
 * Created by vanilka on 09/11/2016.
 */
public class NotFoundUserException extends Exception {

    public NotFoundUserException(String message) { super(message); }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }
}

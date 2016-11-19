package com.genealogytree.ExceptionManager.exception;

/**
 * Created by vanilka on 18/11/2016.
 */
public class NotFoundProjectException extends Exception {

    public NotFoundProjectException(String message) {
        super(message);
    }

    public NotFoundProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}

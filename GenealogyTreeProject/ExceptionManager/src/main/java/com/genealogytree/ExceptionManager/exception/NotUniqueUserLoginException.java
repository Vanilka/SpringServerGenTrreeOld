package com.genealogytree.ExceptionManager.exception;

public class NotUniqueUserLoginException extends Exception {

    private static final long serialVersionUID = -4304127536239301741L;

    public NotUniqueUserLoginException(String message) {
        super(message);
    }

    public NotUniqueUserLoginException(String message, Throwable cause) {
        super(message, cause);
    }

}


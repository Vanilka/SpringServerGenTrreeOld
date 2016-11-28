package com.genealogytree.ExceptionManager.exception;

import com.genealogytree.ExceptionManager.config.Causes;

public class NotUniqueUserLoginException extends Exception {

    private static final long serialVersionUID = -4304127536239301741L;

    public NotUniqueUserLoginException() {
        this(Causes.USER_ALREADY_EXIST.toString());
    }

    public NotUniqueUserLoginException(String message) {
        super(message);
    }

    public NotUniqueUserLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public Causes getCausesInstance() {
        return Causes.USER_ALREADY_EXIST;
    }

}


package com.genealogytree.ExceptionManager.exception;

import com.genealogytree.ExceptionManager.config.Causes;

/**
 * Created by Martyna SZYMKOWIAK on 23/03/2017.
 */
public class IncorrectStatus extends Exception {

    public IncorrectStatus() {
        this(Causes.INCORRECT_SEX.toString());
    }

    public IncorrectStatus(String message) {
        super(message);
    }

    public IncorrectStatus(String message, Throwable cause) {
        super(message, cause);
    }

    public Causes getCausesInstance() {
        return Causes.INCORRECT_SEX;
    }
}

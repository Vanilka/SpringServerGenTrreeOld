package com.genealogytree.ExceptionManager.exception;

import com.genealogytree.ExceptionManager.config.Causes;

/**
 * Created by vanilka on 31/12/2016.
 */
public class IncorrectSex extends Exception {

    public IncorrectSex() {
        this(Causes.INCORRECT_SEX.toString());
    }

    public IncorrectSex(String message) {
        super(message);
    }

    public IncorrectSex(String message, Throwable cause) {
        super(message, cause);
    }

    public Causes getCausesInstance() {
        return Causes.INCORRECT_SEX;
    }
}

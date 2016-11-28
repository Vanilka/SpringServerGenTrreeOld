package com.genealogytree.ExceptionManager.exception;

import com.genealogytree.ExceptionManager.config.Causes;

/**
 * Created by vanilka on 24/11/2016.
 */
public class NoVersionFieldInEntity extends Exception {
    public NoVersionFieldInEntity() {
        this(Causes.NO_VERSION_FIELD.toString());
    }

    public NoVersionFieldInEntity(String message) {
        super(message);
    }

    public NoVersionFieldInEntity(String message, Throwable cause) {
        super(message, cause);
    }

    public Causes getCausesInstance() {
        return Causes.NO_VERSION_FIELD;
    }
}

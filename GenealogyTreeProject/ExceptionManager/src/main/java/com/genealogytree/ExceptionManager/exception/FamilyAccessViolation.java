package com.genealogytree.ExceptionManager.exception;

import com.genealogytree.ExceptionManager.config.Causes;

/**
 * Created by vanilka on 24/11/2016.
 */
public class FamilyAccessViolation extends Exception {

    public FamilyAccessViolation() {
        this(Causes.PROJECT_ACCESS_VIOLATION.toString());
    }

    public FamilyAccessViolation(String message) {
        super(message);
    }

    public FamilyAccessViolation(String message, Throwable cause) {
        super(message, cause);
    }

    public Causes getCausesInstance() {
        return Causes.PROJECT_ACCESS_VIOLATION;
    }
}

package com.genealogytree.ExceptionManager.exception;

import com.genealogytree.ExceptionManager.config.Causes;

public class ExceptionBean {
    private String cause;
    private int code;

    public ExceptionBean() {
        this(Causes.ANOTHER_CAUSE);
    }

    public ExceptionBean(int code, String cause) {
        this.code = code;
        this.cause = cause;
    }

    public ExceptionBean(Causes cause) {
        this.cause = cause.toString();
        this.code = cause.getCode();
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

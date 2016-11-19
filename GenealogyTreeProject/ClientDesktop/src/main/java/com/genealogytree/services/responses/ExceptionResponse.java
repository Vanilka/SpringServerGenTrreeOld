package com.genealogytree.services.responses;

import com.genealogytree.exception.ExceptionBean;

public class ExceptionResponse extends ServerResponse {

    private ExceptionBean exception;

    public ExceptionResponse() {
        this(null);
    }

    public ExceptionResponse(ExceptionBean exception) {
        super(ResponseStatus.FAIL);
        this.exception = exception;
    }

    public ExceptionBean getException() {
        return exception;
    }

    public void setException(ExceptionBean exception) {
        this.exception = exception;
    }

}

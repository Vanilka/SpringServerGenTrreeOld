package genealogytree.services.responses;

import genealogytree.exception.ExceptionBean;

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

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "exception=" + exception +
                "} " + super.toString();
    }
}

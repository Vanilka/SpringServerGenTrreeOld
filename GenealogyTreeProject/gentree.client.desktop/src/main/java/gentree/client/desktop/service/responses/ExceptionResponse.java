package gentree.client.desktop.service.responses;

import gentree.client.desktop.responses.ServiceResponse;
import gentree.exception.ExceptionBean;

/**
 * Created by Martyna SZYMKOWIAK on 24/10/2017.
 */
public class ExceptionResponse extends ServiceResponse {

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
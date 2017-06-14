package genealogytree.client.desktop.service.responses;

import genealogytree.client.desktop.exception.ExceptionBean;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExceptionResponse extends ServiceResponse {

    private ExceptionBean exception;

    public ExceptionResponse() {
        this(null);
    }

    public ExceptionResponse(ExceptionBean exception) {
        super(ResponseStatus.FAIL);
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "exception=" + exception +
                "} " + super.toString();
    }
}

package gentree.exception;

import gentree.exception.configuration.ExceptionCauses;

/**
 * Created by Martyna SZYMKOWIAK on 26/10/2017.
 */
public class TooManyNullFieldsException extends Exception {

    public TooManyNullFieldsException() {
        this(ExceptionCauses.TOO_MANY_NULL_FIELDS.toString());
    }

    public TooManyNullFieldsException(String message) {
        super(message);
    }

    public TooManyNullFieldsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionCauses getCausesInstance() {
        return ExceptionCauses.TOO_MANY_NULL_FIELDS;
    }
}
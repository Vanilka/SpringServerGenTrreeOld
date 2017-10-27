package gentree.exception;

import gentree.exception.configuration.ExceptionCauses;

/**
 * Created by Martyna SZYMKOWIAK on 26/10/2017.
 */
public class TooManyNullFields extends Exception {

    public TooManyNullFields() {
        this(ExceptionCauses.TOO_MANY_NULL_FIELDS.toString());
    }

    public TooManyNullFields(String message) {
        super(message);
    }

    public TooManyNullFields(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionCauses getCausesInstance() {
        return ExceptionCauses.TOO_MANY_NULL_FIELDS;
    }
}
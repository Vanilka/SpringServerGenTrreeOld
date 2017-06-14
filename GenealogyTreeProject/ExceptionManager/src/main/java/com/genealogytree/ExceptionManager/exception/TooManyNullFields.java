package genealogytree.ExceptionManager.exception;

import genealogytree.ExceptionManager.config.Causes;

/**
 * Created by vanilka on 31/12/2016.
 */
public class TooManyNullFields extends Exception {

    public TooManyNullFields() {
        this(Causes.TOO_MANY_NULL_FIELDS.toString());
    }

    public TooManyNullFields(String message) {
        super(message);
    }

    public TooManyNullFields(String message, Throwable cause) {
        super(message, cause);
    }

    public Causes getCausesInstance() {
        return Causes.TOO_MANY_NULL_FIELDS;
    }
}

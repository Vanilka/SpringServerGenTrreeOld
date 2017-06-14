package genealogytree.ExceptionManager.exception;

import genealogytree.ExceptionManager.config.Causes;

/**
 * Created by vanilka on 09/11/2016.
 */
public class NotFoundUserException extends Exception {

    public NotFoundUserException() {
        this(Causes.NOT_FOUND_USER.toString());
    }

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public Causes getCausesInstance() {
        return Causes.NOT_FOUND_USER;
    }
}

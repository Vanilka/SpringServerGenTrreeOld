package genealogytree.ExceptionManager.exception;

import genealogytree.ExceptionManager.config.Causes;

/**
 * Created by vanilka on 18/11/2016.
 */
public class NotFoundFamilyException extends Exception {

    public NotFoundFamilyException() {
        this(Causes.PROJECT_NOT_FOUND.toString());
    }

    public NotFoundFamilyException(String message) {
        super(message);
    }

    public NotFoundFamilyException(String message, Throwable cause) {
        super(message, cause);
    }

    public Causes getCausesInstance() {
        return Causes.PROJECT_NOT_FOUND;
    }
}

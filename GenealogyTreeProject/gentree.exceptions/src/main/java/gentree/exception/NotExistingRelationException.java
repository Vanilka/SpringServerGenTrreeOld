package gentree.exception;

import gentree.exception.configuration.ExceptionCauses;

/**
 * Created by vanilka on 16/01/2018.
 */
public class NotExistingRelationException extends Exception{

    public NotExistingRelationException() {
        this(ExceptionCauses.NOT_EXISTING_RELATION.toString());
    }

    public NotExistingRelationException(String message) {
        super(message);
    }

    public NotExistingRelationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistingRelationException(Throwable cause) {
        super(cause);
    }
}

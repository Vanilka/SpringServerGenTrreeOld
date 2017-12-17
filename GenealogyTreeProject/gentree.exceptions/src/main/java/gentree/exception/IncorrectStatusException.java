package gentree.exception;

import gentree.exception.configuration.ExceptionCauses;

/**
 * Created by Martyna SZYMKOWIAK on 02/11/2017.
 */
public class IncorrectStatusException extends Exception {

    public IncorrectStatusException() {
        this(ExceptionCauses.NOT_EXISTING_MEMBER.toString());
    }

    public IncorrectStatusException(String message) {
        super(message);
    }

    public IncorrectStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionCauses getCausesInstance() {
        return ExceptionCauses.INCORRECT_STATUS;
    }

}

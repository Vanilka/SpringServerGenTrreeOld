package gentree.exception;

import gentree.exception.configuration.ExceptionCauses;

/**
 * Created by Martyna SZYMKOWIAK on 26/10/2017.
 */
public class DescendanceViolationException extends Exception {


    public DescendanceViolationException() {
        this(ExceptionCauses.DESCENDANCE_VIOLATION.toString());
    }

    public DescendanceViolationException(String message) {
        super(message);
    }

    public DescendanceViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DescendanceViolationException(Throwable cause) {
        super(cause);
    }


}

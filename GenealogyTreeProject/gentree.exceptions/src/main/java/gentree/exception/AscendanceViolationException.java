package gentree.exception;

import gentree.exception.configuration.ExceptionCauses;

/**
 * Created by Martyna SZYMKOWIAK on 26/10/2017.
 */
public class AscendanceViolationException extends Exception {


    public AscendanceViolationException() {
        this(ExceptionCauses.ASCENDANCE_VIOLATION.toString());
    }

    public AscendanceViolationException(String message) {
        super(message);
    }

    public AscendanceViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AscendanceViolationException(Throwable cause) {
        super(cause);
    }


}

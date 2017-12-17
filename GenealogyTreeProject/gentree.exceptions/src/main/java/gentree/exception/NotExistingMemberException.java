package gentree.exception;

import gentree.exception.configuration.ExceptionCauses;

/**
 * Created by Martyna SZYMKOWIAK on 02/11/2017.
 */
public class NotExistingMemberException extends Exception {


    public NotExistingMemberException() {
        this(ExceptionCauses.NOT_EXISTING_MEMBER.toString());
    }

    public NotExistingMemberException(String message) {
        super(message);
    }

    public NotExistingMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistingMemberException(Throwable cause) {
        super(cause);
    }

}

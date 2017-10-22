package gentree.exception;

import gentree.exception.configuration.ExceptionCauses;

/**
 * Created by Martyna SZYMKOWIAK on 19/10/2017.
 */

public class FamilyAccessDeniedException extends Exception {

    public FamilyAccessDeniedException() {
        this(ExceptionCauses.FAMILY_ACCESS_DENIED.toString());
    }

    public FamilyAccessDeniedException(String message) {
        super(message);
    }

    public FamilyAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionCauses getCausesInstance() {
        return ExceptionCauses.FAMILY_ACCESS_DENIED;
    }
}
package gentree.exception;


import gentree.exception.configuration.ExceptionCauses;

/**
 * Created by Martyna SZYMKOWIAK on 21/07/2017.
 */
public class NotUniqueBornRelationException extends Exception {


    public NotUniqueBornRelationException() {
        this(ExceptionCauses.NOT_UNIQUE_BORN_RELATION.toString());
    }

    public NotUniqueBornRelationException(String message) {
        super(message);
    }

    public NotUniqueBornRelationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueBornRelationException(Throwable cause) {
        super(cause);
    }


}

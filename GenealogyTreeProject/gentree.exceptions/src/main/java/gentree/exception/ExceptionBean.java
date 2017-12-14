package gentree.exception;

import gentree.exception.configuration.ExceptionCauses;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 */
public class ExceptionBean implements Serializable {

    private static final long serialVersionUID = -5740072421590163426L;
    private String cause;

    public ExceptionBean() {
        this(ExceptionCauses.ANOTHER_CAUSE);
    }

    public ExceptionBean(ExceptionCauses cause) {
        this.cause = cause.toString();
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

}

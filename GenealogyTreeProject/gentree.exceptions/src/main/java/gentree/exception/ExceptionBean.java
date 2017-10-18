package gentree.exception;

import gentree.exception.configuration.ExceptionCauses;

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 */
public class ExceptionBean {
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

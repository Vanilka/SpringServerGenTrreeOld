package genealogytree.ExceptionManager.exception;

import genealogytree.ExceptionManager.config.Causes;

/**
 * Created by Martyna SZYMKOWIAK on 24/03/2017.
 */
public class IntegrationViolation extends Exception{

    public IntegrationViolation() {
        this(Causes.INTEGRATION_VILATION.toString());
    }

    public IntegrationViolation(String message) {
        super(message);
    }

    public IntegrationViolation(String message, Throwable cause) {
        super(message, cause);
    }

    public Causes getCausesInstance() {
        return Causes.INTEGRATION_VILATION;
    }
}

package gentree.exception.configuration;

/**
 * Created by Martyna SZYMKOWIAK on 21/07/2017.
 */
public enum ExceptionCauses {
    NOT_UNIQUE_BORN_RELATION("Not unique born relation");

    private String cause;

    private ExceptionCauses(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return cause;
    }
}

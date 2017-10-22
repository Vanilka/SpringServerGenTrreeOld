package gentree.exception.configuration;

/**
 * Created by Martyna SZYMKOWIAK on 21/07/2017.
 */
public enum ExceptionCauses {
    NOT_UNIQUE_BORN_RELATION("Not unique born relation"),
    ANOTHER_CAUSE("Another Cause"),
    NOT_FOUND_USER("User not found"),
    OPTIMISTIC_LOCK("The row was updated or deleted by another transaction"),
    LOGIN_PASSWORD_INCORRECT("Login or Password Incorrect"),
    FAMILY_ACCESS_DENIED("You must be family owner to take this action ");

    private String cause;

    private ExceptionCauses(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return cause;
    }
}

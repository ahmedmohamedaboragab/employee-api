package employee_api.exception;

public class DuplicateUsernameException
        extends RuntimeException {

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
package board.server.common.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Duplicate email: " + email);
    }
}

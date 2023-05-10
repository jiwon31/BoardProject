package board.server.common.exception;

public class DuplicateUserNameException extends RuntimeException {

    public DuplicateUserNameException(String userName) {
        super("Duplicate userName: " + userName);
    }
}

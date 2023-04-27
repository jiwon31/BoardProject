package board.server.common.exception;

public class UserNotBoardAuthorException extends RuntimeException {

    public UserNotBoardAuthorException(Long id) {
        super("User not author of a board: " + id);
    }
}

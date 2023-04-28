package board.server.common.exception;

public class UserNotCommentAuthorException extends RuntimeException {
    public UserNotCommentAuthorException(Long id) {
        super("User not author of a comment: " + id);
    }
}

package board.server.common.exception;

public class CommentAlreadyDeletedException extends RuntimeException {
    public CommentAlreadyDeletedException(Long id) {
        super("Comment has already been deleted: " + id);
    }
}

package board.server.common.exception;

public class NotLikedBoardException extends IllegalStateException {
    public NotLikedBoardException(Long id) {
        super("This board has not been liked: " + id);
    }
}

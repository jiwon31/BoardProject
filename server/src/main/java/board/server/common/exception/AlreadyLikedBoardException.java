package board.server.common.exception;

public class AlreadyLikedBoardException extends IllegalStateException {
    public AlreadyLikedBoardException(Long id) {
        super("This board has already been liked: " + id);
    }
}

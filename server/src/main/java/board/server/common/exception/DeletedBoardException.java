package board.server.common.exception;

public class DeletedBoardException extends IllegalStateException {
    public DeletedBoardException(Long boardId) {
        super("Cannot comment on deleted boards. boardId: " + boardId);
    }
}

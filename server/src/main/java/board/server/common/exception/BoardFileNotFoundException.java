package board.server.common.exception;

public class BoardFileNotFoundException extends RuntimeException {
    public BoardFileNotFoundException(Long id) {
        super("File not found: " + id);
    }
}

package board.server.domain.board.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetBoardListResponse<T> {

    private int totalPages;

    private T boards;

    @Builder
    public GetBoardListResponse(int totalPages, T boards) {
        this.totalPages = totalPages;
        this.boards = boards;
    }
}

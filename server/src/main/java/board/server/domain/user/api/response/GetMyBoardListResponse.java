package board.server.domain.user.api.response;

import board.server.domain.board.api.response.GetBoardListResult;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetMyBoardListResponse {

    private Boolean isLast;

    private List<GetBoardListResult> boards;

    @Builder
    public GetMyBoardListResponse(Boolean isLast, List<GetBoardListResult> boards) {
        this.isLast = isLast;
        this.boards = boards;
    }
}

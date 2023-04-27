package board.server.domain.board.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateBoardResponse {

    private final String title;
    private final String content;

    @Builder
    public UpdateBoardResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

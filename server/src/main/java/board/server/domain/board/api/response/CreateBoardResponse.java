package board.server.domain.board.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateBoardResponse {

    private final Long id;

    @Builder
    public CreateBoardResponse(Long id) {
        this.id = id;
    }
}

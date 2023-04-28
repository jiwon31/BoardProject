package board.server.domain.comment.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateCommentResponse {

    private Long id;

    @Builder
    public CreateCommentResponse(Long id) {
        this.id = id;
    }
}

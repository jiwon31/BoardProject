package board.server.domain.comment.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateCommentResponse {

    private final String content;

    @Builder
    public UpdateCommentResponse(String content) {
        this.content = content;
    }
}

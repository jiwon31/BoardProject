package board.server.domain.comment.api.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateCommentRequest {

    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;
}

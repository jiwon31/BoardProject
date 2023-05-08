package board.server.domain.board.api.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateBoardRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}

package board.server.domain.board.api.request;

import board.server.domain.board.entity.BoardFile;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter



public class UpdateBoardRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private List<BoardFile> files;
}

package board.server.domain.board.api.request;

import lombok.Getter;

@Getter
public class CreateBoardRequest {
    private String title;
    private String content;
}

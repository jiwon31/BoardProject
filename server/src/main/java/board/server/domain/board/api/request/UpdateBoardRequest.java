package board.server.domain.board.api.request;

import lombok.Getter;

@Getter
public class UpdateBoardRequest {

    private String title;
    private String content;
}

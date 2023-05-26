package board.server.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardFileDto {

    private final Long id;

    private final String originFileName;

    @Builder
    public BoardFileDto(Long id, String originFileName) {
        this.id = id;
        this.originFileName = originFileName;
    }
}

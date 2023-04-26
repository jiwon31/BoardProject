package board.server.domain.board.mapper;

import board.server.domain.board.api.request.CreateBoardRequest;
import board.server.domain.board.api.response.CreateBoardResponse;
import board.server.domain.board.dto.BoardDto;
import org.mapstruct.Mapper;

@Mapper
public interface BoardDtoMapper {

    BoardDto fromCreateRequest(CreateBoardRequest request);
}

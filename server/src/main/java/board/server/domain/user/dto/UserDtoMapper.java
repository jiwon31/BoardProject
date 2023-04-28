package board.server.domain.user.dto;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.user.api.response.GetMyBoardListResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserDtoMapper {

    GetMyBoardListResponse toGetResponse(BoardDto boardDto);
}

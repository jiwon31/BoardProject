package board.server.domain.like.mapper;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.like.api.response.GetUserLikedBoardListResponse;
import org.mapstruct.Mapper;

@Mapper
public interface LikeDtoMapper {

    GetUserLikedBoardListResponse toGetUserLikedBoardListResponse(BoardDto boardDto);

}

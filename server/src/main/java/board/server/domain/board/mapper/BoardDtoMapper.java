package board.server.domain.board.mapper;

import board.server.domain.board.api.request.CreateBoardRequest;
import board.server.domain.board.api.request.UpdateBoardRequest;
import board.server.domain.board.api.response.GetBoardResponse;
import board.server.domain.board.api.response.UpdateBoardResponse;
import board.server.domain.board.dto.BoardDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BoardDtoMapper {

    BoardDto fromCreateRequest(CreateBoardRequest request);

    @Mapping(source = "boardId", target = "id")
    BoardDto fromUpdateRequest(Long boardId, UpdateBoardRequest request);

    UpdateBoardResponse toUpdateResponse(BoardDto boardDto);

    GetBoardResponse toGetResponse(BoardDto boardDto);
}

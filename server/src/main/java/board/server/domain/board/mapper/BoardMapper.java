package board.server.domain.board.mapper;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.entity.Board;
import board.server.domain.user.entitiy.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BoardMapper {

    @Mapping(source = "boardDto.isDeleted", target = "isDeleted")
    Board toEntity(BoardDto boardDto, User user);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.userName", target = "userName")
    BoardDto toDto(Board board);
}

package board.server.domain.board.mapper;

import board.server.domain.board.dto.BoardFileDto;
import board.server.domain.board.entity.BoardFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BoardFileMapper {

    BoardFileDto toDto(BoardFile boardFile);

    List<BoardFileDto> toDtoList(List<BoardFile> boardFileList);
}
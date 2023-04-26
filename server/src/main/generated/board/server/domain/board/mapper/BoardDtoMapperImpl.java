package board.server.domain.board.mapper;

import board.server.domain.board.api.request.CreateBoardRequest;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.dto.BoardDto.BoardDtoBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-26T17:25:21+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
public class BoardDtoMapperImpl implements BoardDtoMapper {

    @Override
    public BoardDto fromCreateRequest(CreateBoardRequest request) {
        if ( request == null ) {
            return null;
        }

        BoardDtoBuilder boardDto = BoardDto.builder();

        boardDto.title( request.getTitle() );
        boardDto.content( request.getContent() );

        return boardDto.build();
    }
}

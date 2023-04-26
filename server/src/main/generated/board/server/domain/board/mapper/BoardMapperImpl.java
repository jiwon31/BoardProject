package board.server.domain.board.mapper;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.dto.BoardDto.BoardDtoBuilder;
import board.server.domain.board.entity.Board;
import board.server.domain.board.entity.Board.BoardBuilder;
import board.server.domain.user.entitiy.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-26T17:02:24+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board toEntity(BoardDto boardDto, User user) {
        if ( boardDto == null && user == null ) {
            return null;
        }

        BoardBuilder board = Board.builder();

        if ( boardDto != null ) {
            board.isDeleted( boardDto.getIsDeleted() );
            board.title( boardDto.getTitle() );
            board.content( boardDto.getContent() );
        }
        if ( user != null ) {
            board.user( user );
        }

        return board.build();
    }

    @Override
    public BoardDto toDto(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDtoBuilder boardDto = BoardDto.builder();

        boardDto.userId( boardUserId( board ) );
        boardDto.userName( boardUserUserName( board ) );
        boardDto.id( board.getId() );
        boardDto.title( board.getTitle() );
        boardDto.content( board.getContent() );
        boardDto.isDeleted( board.getIsDeleted() );

        return boardDto.build();
    }

    private Long boardUserId(Board board) {
        if ( board == null ) {
            return null;
        }
        User user = board.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String boardUserUserName(Board board) {
        if ( board == null ) {
            return null;
        }
        User user = board.getUser();
        if ( user == null ) {
            return null;
        }
        String userName = user.getUserName();
        if ( userName == null ) {
            return null;
        }
        return userName;
    }
}

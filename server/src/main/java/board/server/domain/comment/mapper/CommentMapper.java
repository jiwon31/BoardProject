package board.server.domain.comment.mapper;

import board.server.domain.board.entity.Board;
import board.server.domain.comment.dto.CommentDto;
import board.server.domain.comment.entity.Comment;
import board.server.domain.user.entitiy.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Mapping(source = "commentDto.content", target = "content")
    @Mapping(source = "commentDto.isDeleted", target = "isDeleted")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "board", target = "board")
    Comment toEntity(CommentDto commentDto, User user, Board board);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.userName", target = "userName")
    CommentDto toDto(Comment comment);

    List<CommentDto> toDtoList(List<Comment> commentList);
}

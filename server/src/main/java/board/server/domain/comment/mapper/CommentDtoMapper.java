package board.server.domain.comment.mapper;

import board.server.domain.comment.api.request.CreateCommentRequest;
import board.server.domain.comment.dto.CommentDto;
import org.mapstruct.Mapper;

@Mapper
public interface CommentDtoMapper {

    CommentDto fromCreateRequest(CreateCommentRequest request);
}

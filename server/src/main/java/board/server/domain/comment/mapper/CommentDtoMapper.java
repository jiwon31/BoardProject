package board.server.domain.comment.mapper;

import board.server.domain.comment.api.request.CreateCommentRequest;
import board.server.domain.comment.api.request.UpdateCommentRequest;
import board.server.domain.comment.api.response.GetCommentListResponse;
import board.server.domain.comment.api.response.UpdateCommentResponse;
import board.server.domain.comment.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentDtoMapper {

    CommentDto fromCreateRequest(CreateCommentRequest request);

    @Mapping(source = "commentId", target = "id")
    CommentDto fromUpdateRequest(Long commentId, UpdateCommentRequest request);

    UpdateCommentResponse toUpdateResponse(CommentDto commentDto);

    GetCommentListResponse toGetResponse(CommentDto commentDto);
}

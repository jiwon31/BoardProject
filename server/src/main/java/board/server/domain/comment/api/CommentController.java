package board.server.domain.comment.api;

import board.server.domain.comment.api.request.CreateCommentRequest;
import board.server.domain.comment.api.request.UpdateCommentRequest;
import board.server.domain.comment.api.response.CreateCommentResponse;
import board.server.domain.comment.api.response.UpdateCommentResponse;
import board.server.domain.comment.dto.CommentDto;
import board.server.domain.comment.mapper.CommentDtoMapper;
import board.server.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentDtoMapper dtoMapper = Mappers.getMapper(CommentDtoMapper.class);

    /**
     * 댓글 생성
     */
    @PostMapping("/boards/{boardId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(@PathVariable Long boardId,
                                                               @RequestHeader Long userId,
                                                               @RequestBody @Valid CreateCommentRequest request) {
        CommentDto commentDto = dtoMapper.fromCreateRequest(request);
        Long commentId = commentService.create(userId, boardId, commentDto).getId();
        return ResponseEntity.ok(new CreateCommentResponse(commentId));
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable Long commentId,
                                                               @RequestHeader Long userId,
                                                               @RequestBody @Valid UpdateCommentRequest request) {
        CommentDto requestDto = dtoMapper.fromUpdateRequest(commentId, request);
        commentService.checkCommentAuthor(commentId, userId);
        CommentDto commentDto = commentService.update(requestDto);
        return ResponseEntity.ok(dtoMapper.toUpdateResponse(commentDto));
    }

    /**
     * 댓글 삭제
     */
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<Objects> deleteComment(@PathVariable Long commentId, @RequestHeader Long userId) {
        commentService.checkCommentAuthor(commentId, userId);
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}

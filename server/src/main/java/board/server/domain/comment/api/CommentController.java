package board.server.domain.comment.api;

import board.server.domain.comment.api.request.CreateCommentRequest;
import board.server.domain.comment.api.response.CreateCommentResponse;
import board.server.domain.comment.dto.CommentDto;
import board.server.domain.comment.mapper.CommentDtoMapper;
import board.server.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentDtoMapper dtoMapper = Mappers.getMapper(CommentDtoMapper.class);

    /**
     * 댓글 생성
     */
    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(@RequestHeader Long userId,
                                                               @PathVariable Long boardId,
                                                               @RequestBody @Valid CreateCommentRequest createCommentRequest) {
        CommentDto commentDto = dtoMapper.fromCreateRequest(createCommentRequest);
        Long commentId = commentService.create(userId, boardId, commentDto).getId();
        return ResponseEntity.ok(new CreateCommentResponse(commentId));
    }
}

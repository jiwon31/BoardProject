package board.server.domain.comment.api;

import board.server.domain.comment.api.request.CreateCommentRequest;
import board.server.domain.comment.api.request.UpdateCommentRequest;
import board.server.domain.comment.api.response.CreateCommentResponse;
import board.server.domain.comment.api.response.GetCommentListResponse;
import board.server.domain.comment.api.response.UpdateCommentResponse;
import board.server.domain.comment.dto.CommentDto;
import board.server.domain.comment.mapper.CommentDtoMapper;
import board.server.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static board.server.common.util.SecurityUtil.*;

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
    public ResponseEntity<CreateCommentResponse> createComment(@PathVariable Long boardId, @RequestBody @Valid CreateCommentRequest request) {
        CommentDto commentDto = dtoMapper.fromCreateRequest(request);
        Long commentId = commentService.create(getUserId(), boardId, commentDto).getId();
        return ResponseEntity.ok(new CreateCommentResponse(commentId));
    }

    /**
     * 대댓글 생성
     */
    @PostMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CreateCommentResponse> createNestedComment(@PathVariable Long boardId,
                                                                     @PathVariable Long commentId,
                                                                     @RequestBody @Valid CreateCommentRequest request) {
        CommentDto commentDto = dtoMapper.fromCreateRequest(request);
        Long id = commentService.createNested(getUserId(), boardId, commentId, commentDto).getId();
        return ResponseEntity.ok(new CreateCommentResponse(id));
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable Long boardId,
                                                               @PathVariable Long commentId,
                                                               @RequestBody @Valid UpdateCommentRequest request) {
        commentService.checkCommentAuthor(commentId, getUserId());
        CommentDto requestDto = dtoMapper.fromUpdateRequest(commentId, request);
        CommentDto commentDto = commentService.update(boardId, requestDto);
        return ResponseEntity.ok(dtoMapper.toUpdateResponse(commentDto));
    }

    /**
     * 댓글 삭제
     */
    @PatchMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Objects> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        commentService.checkCommentAuthor(commentId, getUserId());
        commentService.delete(boardId, commentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 댓글 리스트 조회
     */
    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<GetCommentListResponse>> getCommentList(@PathVariable Long boardId) {
        List<CommentDto> commentList = commentService.findAll(boardId);
        return ResponseEntity.ok(commentList.stream()
                .map(dtoMapper::toGetResponse)
                .collect(Collectors.toList()));
    }
}

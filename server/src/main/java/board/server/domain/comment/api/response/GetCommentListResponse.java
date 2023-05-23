package board.server.domain.comment.api.response;

import board.server.domain.comment.dto.CommentDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GetCommentListResponse {

    private final Long id;

    private final String content;

    private final Long userId;

    private final String userName;

    private final Boolean isDeleted;

    private final LocalDateTime createdAt;

    private List<CommentDto> children = new ArrayList<>();

    @Builder
    public GetCommentListResponse(Long id, String content, Long userId, String userName, Boolean isDeleted, LocalDateTime createdAt, List<CommentDto> children) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.children = children;
    }
}

package board.server.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDto {

    private final Long id;

    private final String content;

    private final Long userId;

    private final String userName;

    private final Boolean isDeleted;

    private final LocalDateTime createdAt;

    @Builder
    public CommentDto(Long id, String content, Long userId, String userName, Boolean isDeleted, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }
}

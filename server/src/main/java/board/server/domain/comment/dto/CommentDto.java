package board.server.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDto {

    private final Long id;

    private final String content;

    private final Boolean isDeleted;

    private final LocalDateTime createdAt;

    @Builder
    public CommentDto(Long id, String content, Boolean isDeleted, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }
}

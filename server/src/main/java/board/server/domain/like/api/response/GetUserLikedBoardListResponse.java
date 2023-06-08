package board.server.domain.like.api.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserLikedBoardListResponse {
    private final Long id;

    private final String title;

    private final String content;

    private final String userName;

    private final Boolean isDeleted;

    private final LocalDateTime createdAt;

    private final int likeCount;

    private final int commentCount;

    @Builder
    public GetUserLikedBoardListResponse(Long id, String title, String content,
                                         String userName, Boolean isDeleted, LocalDateTime createdAt,
                                         int likeCount, int commentCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}

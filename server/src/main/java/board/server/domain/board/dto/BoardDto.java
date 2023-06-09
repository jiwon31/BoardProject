package board.server.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardDto {
    private final Long id;

    private final String title;

    private final String content;

    private final Long userId;

    private final String userName;

    private final Boolean isDeleted;

    private final LocalDateTime createdAt;

    private final List<BoardFileDto> files = new ArrayList<>();

    private final int viewCount;

    private final int likeCount;

    private final int commentCount;

    private final Boolean isLikedByUser;

    @Builder
    public BoardDto(Long id, String title, String content,
                    Long userId, String userName, Boolean isDeleted, LocalDateTime createdAt,
                    int viewCount, int likeCount, int commentCount, Boolean isLikedByUser) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isLikedByUser = isLikedByUser;
    }
}

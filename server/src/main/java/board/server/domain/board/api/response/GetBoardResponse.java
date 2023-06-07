package board.server.domain.board.api.response;

import board.server.domain.board.dto.BoardFileDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetBoardResponse {

    private final Long id;

    private final String title;

    private final String content;

    private final Long userId;

    private final String userName;

    private final Boolean isDeleted;

    private final LocalDateTime createdAt;

    private final List<BoardFileDto> files;

    private final int viewCount;

    private final int likeCount;

    private final boolean isLikedByUser;

    @Builder
    public GetBoardResponse(Long id, String title, String content, Long userId,
                            String userName, Boolean isDeleted, LocalDateTime createdAt,
                            List<BoardFileDto> files, int viewCount, int likeCount, boolean isLikedByUser) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.files = files;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.isLikedByUser = isLikedByUser;
    }
}

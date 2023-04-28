package board.server.domain.user.api.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetMyBoardListResponse {
    private final Long id;

    private final String title;

    private final String userName;

    private final Boolean isDeleted;

    private final LocalDateTime createdAt;

    @Builder
    public GetMyBoardListResponse(Long id, String title, String userName, Boolean isDeleted, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }
}

package board.server.domain.user.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetUserInfoResponse {

    private final Long id;

    private final String email;

    private final String userName;

    @Builder
    public GetUserInfoResponse(Long id, String email, String userName) {
        this.id = id;
        this.email = email;
        this.userName = userName;
    }
}

package board.server.domain.user.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetUserInfoResponse {

    private final String email;

    private final String userName;

    @Builder
    public GetUserInfoResponse(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }
}

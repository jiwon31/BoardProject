package board.server.domain.user.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final String accessToken;

    @Builder
    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}

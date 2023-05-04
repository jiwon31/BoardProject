package board.server.domain.user.api.request;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;
    private String password;
}

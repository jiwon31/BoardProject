package board.server.domain.user.api.request;

import lombok.Getter;

@Getter
public class SignupRequest {

    private String email;
    private String password;
    private String userName;
}

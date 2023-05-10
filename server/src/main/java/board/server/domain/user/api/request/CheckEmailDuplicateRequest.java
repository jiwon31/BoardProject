package board.server.domain.user.api.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class CheckEmailDuplicateRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;
}

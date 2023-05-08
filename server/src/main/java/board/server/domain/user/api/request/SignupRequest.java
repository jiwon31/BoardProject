package board.server.domain.user.api.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequest {

    @NotBlank(message = "이메일을 필수로 입력해주세요.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호를 필수로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
            message = "비밀번호는 최소 8자리에 숫자, 문자, 특수문자가 각 1개 이상 포함되어야 합니다.")
    private String password;

    @NotBlank(message = "닉네임을 필수로 입력해주세요.")
    private String userName;
}

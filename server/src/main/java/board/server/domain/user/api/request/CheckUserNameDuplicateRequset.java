package board.server.domain.user.api.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CheckUserNameDuplicateRequset {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String userName;
}

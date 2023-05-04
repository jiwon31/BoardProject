package board.server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccessTokenDto {

    private final String accessToken;

    @Builder
    public AccessTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}

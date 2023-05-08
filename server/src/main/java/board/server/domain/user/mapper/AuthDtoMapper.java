package board.server.domain.user.mapper;

import board.server.domain.user.api.response.AuthResponse;
import board.server.domain.user.dto.TokenDto;
import org.mapstruct.Mapper;

@Mapper
public interface AuthDtoMapper {

    AuthResponse toAuthResponse(TokenDto tokenDto);
}

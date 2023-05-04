package board.server.domain.user.mapper;

import board.server.domain.user.api.response.LoginResponse;
import board.server.domain.user.dto.TokenDto;
import org.mapstruct.Mapper;

@Mapper
public interface AuthDtoMapper {

    LoginResponse toLoginResponse(TokenDto tokenDto);
}

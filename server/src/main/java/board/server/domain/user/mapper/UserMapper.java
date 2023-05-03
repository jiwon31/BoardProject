package board.server.domain.user.mapper;

import board.server.domain.user.dto.UserDto;
import board.server.domain.user.entitiy.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto toDto(User user);
}

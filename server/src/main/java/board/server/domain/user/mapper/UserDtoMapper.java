package board.server.domain.user.mapper;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.user.api.request.LoginRequest;
import board.server.domain.user.api.request.SignupRequest;
import board.server.domain.user.api.request.UpdateUserInfoRequest;
import board.server.domain.user.api.response.GetMyBoardListResponse;
import board.server.domain.user.api.response.GetUserInfoResponse;
import board.server.domain.user.api.response.UpdateUserInfoResponse;
import board.server.domain.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserDtoMapper {

    UserDto fromSignupRequest(SignupRequest request);

    UserDto fromLoginRequest(LoginRequest request);

    UserDto fromUpdateUserInfoRequest(UpdateUserInfoRequest request);

    GetUserInfoResponse toGetUserInfoResponse(UserDto userDto);

    UpdateUserInfoResponse toUpdateUserInfoResponse(UserDto userDto);

    GetMyBoardListResponse toGetMyBoardListResponse(BoardDto boardDto);
}

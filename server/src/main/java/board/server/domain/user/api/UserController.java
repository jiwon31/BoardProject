package board.server.domain.user.api;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.user.api.request.UpdateUserInfoRequest;
import board.server.domain.user.api.response.GetMyBoardListResponse;
import board.server.domain.user.api.response.GetUserInfoResponse;
import board.server.domain.user.api.response.UpdateUserInfoResponse;
import board.server.domain.user.dto.UserDto;
import board.server.domain.user.mapper.UserDtoMapper;
import board.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static board.server.common.util.SecurityUtil.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper = Mappers.getMapper(UserDtoMapper.class);

    /**
     * 사용자 정보 조회
     */
    @GetMapping("/info")
    public ResponseEntity<GetUserInfoResponse> getUserInfo() {
        UserDto userDto = userService.findUserInfo(getUserId());
        return ResponseEntity.ok(userDtoMapper.toGetUserInfoResponse(userDto));
    }

    /**
     * 사용자 정보 수정
     */
    @PutMapping("/info")
    public ResponseEntity<UpdateUserInfoResponse> updateUserInfo(@RequestBody @Valid UpdateUserInfoRequest request) {
        UserDto requestDto = userDtoMapper.fromUpdateUserInfoRequest(request);
        UserDto userDto = userService.updateUserInfo(getUserId(), requestDto);
        return ResponseEntity.ok(userDtoMapper.toUpdateUserInfoResponse(userDto));
    }


    /**
     * 사용자가 작성한 게시글 리스트 조회
     */
    @GetMapping("/boards")
    public ResponseEntity<List<GetMyBoardListResponse>> getMyBoardList() {
        List<BoardDto> myBoardList = userService.findMyBoardList(getUserId());
        return ResponseEntity.ok(myBoardList.stream().map(userDtoMapper::toGetMyBoardListResponse).collect(Collectors.toList()));
    }
}

package board.server.domain.user.api;

import board.server.domain.board.api.response.GetBoardListResult;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.mapper.BoardDtoMapper;
import board.server.domain.user.api.request.CheckEmailDuplicateRequest;
import board.server.domain.user.api.request.CheckUserNameDuplicateRequset;
import board.server.domain.user.api.request.UpdateUserInfoRequest;
import board.server.domain.user.api.response.GetMyBoardListResponse;
import board.server.domain.user.api.response.GetUserInfoResponse;
import board.server.domain.user.api.response.UpdateUserInfoResponse;
import board.server.domain.user.dto.UserDto;
import board.server.domain.user.mapper.UserDtoMapper;
import board.server.domain.user.service.UserService;
import board.server.domain.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final BoardDtoMapper boardDtoMapper = Mappers.getMapper(BoardDtoMapper.class);

    private final UserUtil userUtil;

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
     * 이메일 중복 검사
     */
    @PostMapping("/email/exists")
    public ResponseEntity checkEmailDuplicate(@RequestBody @Valid CheckEmailDuplicateRequest request) {
        userUtil.checkEmailDuplicate(request.getEmail());
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }

    /**
     * 닉네임 중복 검사
     */
    @PostMapping("/username/exists")
    public ResponseEntity checkUserNameDuplicate(@RequestBody @Valid CheckUserNameDuplicateRequset request) {
        userUtil.checkUserNameDuplicate(request.getUserName());
        return ResponseEntity.ok("사용 가능한 닉네임입니다.");
    }

    /**
     * 사용자가 작성한 게시글 리스트 조회
     */
    @GetMapping("/boards")
    public ResponseEntity<GetMyBoardListResponse> getMyBoardList(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardDto> myBoardList = userService.findMyBoardList(getUserId(), pageable);
        List<GetBoardListResult> collect = myBoardList.getContent()
                .stream()
                .map(boardDtoMapper::toGetListResult)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new GetMyBoardListResponse(myBoardList.isLast(), collect));
    }
}

package board.server.domain.user.api;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.user.api.response.GetMyBoardListResponse;
import board.server.domain.user.mapper.UserDtoMapper;
import board.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static board.server.common.util.SecurityUtil.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper = Mappers.getMapper(UserDtoMapper.class);

    @GetMapping("/users/boards")
    public ResponseEntity<List<GetMyBoardListResponse>> getMyBoardList() {
        List<BoardDto> myBoardList = userService.findMyBoardList(getUserId());
        return ResponseEntity.ok(myBoardList.stream().map(userDtoMapper::toGetMyBoardListResponse).collect(Collectors.toList()));
    }
}

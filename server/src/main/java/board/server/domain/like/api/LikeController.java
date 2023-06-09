package board.server.domain.like.api;

import board.server.domain.board.api.response.GetBoardListResult;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.mapper.BoardDtoMapper;
import board.server.domain.like.api.response.GetUserLikedBoardListResponse;
import board.server.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static board.server.common.util.SecurityUtil.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final BoardDtoMapper boardDtoMapper = Mappers.getMapper(BoardDtoMapper.class);


    /**
     * 게시글 좋아요
     */
    @PostMapping("/likes/{boardId}")
    public ResponseEntity<String> addLike(@PathVariable Long boardId) {
        likeService.add(boardId);
        return ResponseEntity.ok("좋아요 반영되었습니다");
    }

    /**
     * 좋아요 취소
     */
    @DeleteMapping("/likes/{boardId}")
    public ResponseEntity<String> cancelLike(@PathVariable Long boardId) {
        likeService.delete(boardId);
        return ResponseEntity.ok("좋아요 취소되었습니다.");
    }

    /**
     * 유저가 좋아요한 게시글만 조회
     */
    @GetMapping("/users/likes/boards")
    public ResponseEntity<GetUserLikedBoardListResponse> getUserLikedBoardList(@PageableDefault(size = 10) Pageable pageable) {
        Page<BoardDto> boardList = likeService.findUserLikedBoardList(getUserId(), pageable);
        List<GetBoardListResult> collect = boardList.getContent()
                .stream()
                .map(boardDtoMapper::toGetListResult)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new GetUserLikedBoardListResponse(boardList.isLast(), collect));
    }
}

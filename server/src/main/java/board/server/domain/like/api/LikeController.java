package board.server.domain.like.api;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.like.api.response.GetUserLikedBoardListResponse;
import board.server.domain.like.mapper.LikeDtoMapper;
import board.server.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static board.server.common.util.SecurityUtil.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final LikeDtoMapper likeDtoMapper = Mappers.getMapper(LikeDtoMapper.class);


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
    public ResponseEntity<List<GetUserLikedBoardListResponse>> getUserLikedBoardList() {
        List<BoardDto> boardList = likeService.findUserLikedBoardList(getUserId());
        return ResponseEntity.ok(boardList.stream()
                .map(likeDtoMapper::toGetUserLikedBoardListResponse)
                .collect(Collectors.toList()));
    }
}

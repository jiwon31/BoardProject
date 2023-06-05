package board.server.domain.like.api;

import board.server.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    /**
     * 게시글 좋아요
     */
    @PostMapping("/{boardId}")
    public ResponseEntity<String> addLike(@PathVariable Long boardId) {
        likeService.add(boardId);
        return ResponseEntity.ok("좋아요 반영되었습니다");
    }

    /**
     * 좋아요 취소
     */
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> cancelLike(@PathVariable Long boardId) {
        likeService.delete(boardId);
        return ResponseEntity.ok("좋아요 취소되었습니다.");
    }
}

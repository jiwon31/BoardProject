package board.server.domain.board.api;

import board.server.domain.board.api.request.CreateBoardRequest;
import board.server.domain.board.api.request.UpdateBoardRequest;
import board.server.domain.board.api.response.CreateBoardResponse;
import board.server.domain.board.api.response.GetBoardResponse;
import board.server.domain.board.api.response.UpdateBoardResponse;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.mapper.BoardDtoMapper;
import board.server.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardDtoMapper dtoMapper = Mappers.getMapper(BoardDtoMapper.class);

    /**
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<CreateBoardResponse> createBoard(@RequestHeader Long userId, @RequestBody @Valid CreateBoardRequest createBoardRequest) {
        BoardDto requestDto = dtoMapper.fromCreateRequest(createBoardRequest);
        Long boardId = boardService.create(userId, requestDto).getId();
        return ResponseEntity.ok(new CreateBoardResponse(boardId));
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{boardId}")
    public ResponseEntity<UpdateBoardResponse> updateBoard(@PathVariable Long boardId,
                                                           @RequestHeader Long userId,
                                                           @RequestBody @Valid UpdateBoardRequest updateBoardRequest) {
        boardService.checkBoardAuthor(boardId, userId);
        BoardDto requestDto = dtoMapper.fromUpdateRequest(boardId, updateBoardRequest);
        BoardDto boardDto = boardService.update(requestDto);
        return ResponseEntity.ok(dtoMapper.toUpdateResponse(boardDto));
    }

    /**
     * 게시글 삭제
     */
    @PatchMapping("/{boardId}")
    public ResponseEntity<Objects> deleteBoard(@PathVariable Long boardId, @RequestHeader Long userId) {
        boardService.checkBoardAuthor(boardId, userId);
        boardService.delete(boardId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 게시글 1개 조회
     */
    @GetMapping("/{boardId}")
    public ResponseEntity<GetBoardResponse> getSingleBoard(@PathVariable Long boardId) {
        BoardDto boardDto = boardService.fineOne(boardId);
        return ResponseEntity.ok(dtoMapper.toGetResponse(boardDto));
    }
}

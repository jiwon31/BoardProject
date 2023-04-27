package board.server.domain.board.api;

import board.server.domain.board.api.request.CreateBoardRequest;
import board.server.domain.board.api.request.UpdateBoardRequest;
import board.server.domain.board.api.response.CreateBoardResponse;
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
    @PutMapping("/{id}")
    public ResponseEntity<UpdateBoardResponse> updateBoard(@PathVariable Long id, @RequestBody @Valid UpdateBoardRequest updateBoardRequest) {
        BoardDto requestDto = dtoMapper.fromUpdateRequest(id, updateBoardRequest);
        BoardDto boardDto = boardService.update(requestDto);
        return ResponseEntity.ok(dtoMapper.toUpdateResponse(boardDto));
    }

    /**
     * 게시글 삭제
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Objects> deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.ok().build();
    }
}

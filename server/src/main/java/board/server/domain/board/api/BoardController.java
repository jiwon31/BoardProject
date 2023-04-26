package board.server.domain.board.api;

import board.server.domain.board.api.request.CreateBoardRequest;
import board.server.domain.board.api.response.CreateBoardResponse;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.mapper.BoardDtoMapper;
import board.server.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardDtoMapper dtoMapper = Mappers.getMapper(BoardDtoMapper.class);

    /**
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<CreateBoardResponse> createBoard(@RequestHeader Long userId, @RequestBody @Valid CreateBoardRequest createBoardRequest) {
        BoardDto boardDto = dtoMapper.fromCreateRequest(createBoardRequest);
        Long boardId = boardService.create(userId, boardDto).getId();
        return ResponseEntity.ok(new CreateBoardResponse(boardId));
    }
}

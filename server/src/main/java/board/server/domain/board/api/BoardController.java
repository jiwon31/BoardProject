package board.server.domain.board.api;

import board.server.domain.board.api.request.CreateBoardRequest;
import board.server.domain.board.api.request.UpdateBoardRequest;
import board.server.domain.board.api.response.CreateBoardResponse;
import board.server.domain.board.api.response.GetBoardListResponse;
import board.server.domain.board.api.response.GetBoardResponse;
import board.server.domain.board.api.response.UpdateBoardResponse;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.mapper.BoardDtoMapper;
import board.server.domain.board.service.BoardSearchService;
import board.server.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardSearchService boardSearchService;
    private final BoardDtoMapper dtoMapper = Mappers.getMapper(BoardDtoMapper.class);

    /**
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<CreateBoardResponse> createBoard(@AuthenticationPrincipal User user, @RequestBody @Valid CreateBoardRequest request) {
        BoardDto requestDto = dtoMapper.fromCreateRequest(request);
        Long boardId = boardService.create(parseLong(user.getUsername()), requestDto).getId();
        return ResponseEntity.ok(new CreateBoardResponse(boardId));
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{boardId}")
    public ResponseEntity<UpdateBoardResponse> updateBoard(@PathVariable Long boardId,
                                                           @RequestHeader Long userId,
                                                           @RequestBody @Valid UpdateBoardRequest request) {
        boardService.checkBoardAuthor(boardId, userId);
        BoardDto requestDto = dtoMapper.fromUpdateRequest(boardId, request);
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

    /**
     * 게시글 리스트 조회 / 검색
     *
     * @param title    : 제목
     * @param content  : 본문
     * @param pageable : 페이징
     * @return
     */
    @GetMapping
    public ResponseEntity<List<GetBoardListResponse>> getBoardList(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @PageableDefault(size = 16, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        List<BoardDto> boardList = boardSearchService.getBoardListBySearchConditions(title, content, pageable);
        return ResponseEntity.ok(boardList.stream().map(dtoMapper::toGetListResponse).collect(Collectors.toList()));
    }
}

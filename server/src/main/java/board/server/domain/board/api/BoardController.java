package board.server.domain.board.api;

import board.server.domain.board.api.request.CreateBoardRequest;
import board.server.domain.board.api.request.UpdateBoardRequest;
import board.server.domain.board.api.response.*;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.entity.BoardFile;
import board.server.domain.board.mapper.BoardDtoMapper;
import board.server.domain.board.mapper.BoardFileMapper;
import board.server.domain.board.repository.BoardFileRepository;
import board.server.domain.board.service.BoardFileService;
import board.server.domain.board.service.BoardSearchService;
import board.server.domain.board.service.BoardService;
import board.server.domain.board.util.BoardUtil;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static board.server.common.util.SecurityUtil.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardSearchService boardSearchService;
    private final BoardFileService boardFileService;
    private final BoardDtoMapper dtoMapper = Mappers.getMapper(BoardDtoMapper.class);
    private final BoardFileMapper fileMapper = Mappers.getMapper(BoardFileMapper.class);
    private final BoardUtil boardUtil;

    /**
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<CreateBoardResponse> createBoard(@RequestPart @Valid CreateBoardRequest request,
                                                           @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        BoardDto requestDto = dtoMapper.fromCreateRequest(request);
        Long boardId = boardService.create(getUserId(), requestDto).getId();

        if (files != null) {
            boardFileService.storeFiles(boardId, files);
        }
        return ResponseEntity.ok(new CreateBoardResponse(boardId));
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{boardId}")
    public ResponseEntity<UpdateBoardResponse> updateBoard(@PathVariable Long boardId, @RequestPart @Valid UpdateBoardRequest request,
                                                           @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        boardService.checkBoardAuthor(boardId, getUserId());
        BoardDto requestDto = dtoMapper.fromUpdateRequest(boardId, request);

        if (!CollectionUtils.isEmpty(boardUtil.findFilesByBoardId(boardId))) {
            if (request.getFiles() != null) {
                for (BoardFile file : request.getFiles()) {
                    requestDto.getFiles().add(fileMapper.toDto(file));
                }
                boardFileService.update(boardId, requestDto);
            } else {
                boardFileService.deleteAll(boardId);
            }
        }

        if (files != null) {
            boardFileService.storeFiles(boardId, files);
        }

        BoardDto boardDto = boardService.update(requestDto);
        return ResponseEntity.ok(dtoMapper.toUpdateResponse(boardDto));
    }

    /**
     * 게시글 삭제
     */
    @PatchMapping("/{boardId}")
    public ResponseEntity<Objects> deleteBoard(@PathVariable Long boardId) {
        boardService.checkBoardAuthor(boardId, getUserId());
        boardService.delete(boardId);
        boardFileService.deleteFiles(boardId);
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
    public ResponseEntity<GetBoardListResponse> getBoardList(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardDto> boards = boardSearchService.getBoardListBySearchConditions(title, content, pageable);
        List<GetBoardListResult> collect = boards.getContent()
                .stream()
                .map(dtoMapper::toGetListResult)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new GetBoardListResponse(boards.getTotalPages(), collect));
    }
}

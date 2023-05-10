package board.server.domain.board.service;

import board.server.common.exception.UserNotBoardAuthorException;
import board.server.common.util.CommonUtil;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.entity.Board;
import board.server.domain.board.mapper.BoardMapper;
import board.server.domain.board.repository.BoardRepository;
import board.server.domain.user.entitiy.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);
    private final CommonUtil commonUtil;

    /**
     * 게시글 생성
     *
     * @param userId   : 유저 식별자
     * @param boardDto : 게시글 정보
     */
    @Transactional
    public BoardDto create(Long userId, BoardDto boardDto) {
        User user = commonUtil.findUser(userId);
        Board board = boardMapper.toEntity(boardDto, user);
        return boardMapper.toDto(boardRepository.save(board));
    }

    /**
     * 게시글 수정
     *
     * @param boardDto : 수정한 게시글 정보
     */
    @Transactional
    public BoardDto update(BoardDto boardDto) {
        Board board = commonUtil.findBoard(boardDto.getId());
        board.update(boardDto.getTitle(), boardDto.getContent());
        return boardMapper.toDto(board);
    }

    /**
     * 게시글 삭제
     *
     * @param boardId : 게시글 식별자
     */
    @Transactional
    public void delete(Long boardId) {
        Board board = commonUtil.findBoard(boardId);
        board.delete();
    }

    /**
     * 게시글 한 개 조회
     *
     * @param boardId : 게시글 식별자
     */
    public BoardDto fineOne(Long boardId) {
        Board board = commonUtil.findBoard(boardId);
        return boardMapper.toDto(board);
    }

    /**
     * 회원 권한 검증 (수정, 삭제 시)
     *
     * @param boardId : 게시글 식별자
     * @param userId  : 유저 식별자
     */
    public void checkBoardAuthor(Long boardId, Long userId) {
        User author = commonUtil.findBoard(boardId).getUser();
        User user = commonUtil.findUser(userId);
        if (!author.equals(user)) {
            throw new UserNotBoardAuthorException(user.getId());
        }
    }
}

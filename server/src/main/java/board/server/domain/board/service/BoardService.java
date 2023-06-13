package board.server.domain.board.service;

import board.server.common.exception.UserNotBoardAuthorException;
import board.server.common.util.CommonUtil;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.dto.BoardFileDto;
import board.server.domain.board.entity.Board;
import board.server.domain.board.entity.BoardFile;
import board.server.domain.board.mapper.BoardFileMapper;
import board.server.domain.board.mapper.BoardMapper;
import board.server.domain.board.repository.BoardFileRepository;
import board.server.domain.board.repository.BoardRepository;
import board.server.domain.like.entity.Like;
import board.server.domain.like.repository.LikeRepository;
import board.server.domain.user.entitiy.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static board.server.common.util.SecurityUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final LikeRepository likeRepository;
    private final BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);
    private final BoardFileMapper boardFileMapper = Mappers.getMapper(BoardFileMapper.class);
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
        board.update(boardDto);
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
    @Transactional
    public BoardDto fineOne(Long boardId) {
        Board board = commonUtil.findBoard(boardId);
        if (board.getUser().getId() != getUserId()) {
            board.increaseViewCount(); // 조회수 증가
        }

        // 게시글에 대한 유저의 좋아요 상태
        List<Like> likes = likeRepository.findAllByBoardId(boardId);
        for (Like like : likes) {
            if (like.getUser().getId() == getUserId()) {
                board.updateIsLikedByUser(true);
                break;
            }
        }
        BoardDto boardDto = boardMapper.toDto(board);

        // 첨부 파일
        List<BoardFile> files = boardFileRepository.findAllByBoardId(boardId);
        for (BoardFile file : files) {
            BoardFileDto boardFileDto = boardFileMapper.toDto(file);
            boardDto.getFiles().add(boardFileDto);
        }
        return boardDto;
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

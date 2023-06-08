package board.server.domain.comment.service;

import board.server.common.exception.CommentAlreadyDeletedException;
import board.server.common.exception.CommentNotFoundException;
import board.server.common.exception.DeletedBoardException;
import board.server.common.exception.UserNotCommentAuthorException;
import board.server.common.util.CommonUtil;
import board.server.domain.board.entity.Board;
import board.server.domain.comment.dto.CommentDto;
import board.server.domain.comment.entity.Comment;
import board.server.domain.comment.mapper.CommentMapper;
import board.server.domain.comment.repository.CommentRepository;
import board.server.domain.comment.repository.querydsl.CommentQueryRepository;
import board.server.domain.user.entitiy.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;
    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);
    private final CommonUtil commonUtil;

    /**
     * 댓글 생성
     *
     * @param userId     : 유저 식별자
     * @param boardId    : 게시글 식별자
     * @param commentDto : 댓글 정보
     */
    @Transactional
    public CommentDto create(Long userId, Long boardId, CommentDto commentDto) {
        Board board = checkDeletedBoard(boardId);
        User user = commonUtil.findUser(userId);
        Comment comment = commentRepository.save(commentMapper.toEntity(commentDto, user, board));
        board.increaseCommentCount();
        return commentMapper.toDto(comment);
    }

    /**
     * 대댓글 생성
     *
     * @param userId     : 유저 식별자
     * @param boardId    : 게시글 식별자
     * @param commentId  : 부모 댓글 식별자
     * @param commentDto : 댓글 정보
     * @return
     */
    @Transactional
    public CommentDto createNested(Long userId, Long boardId, Long commentId, CommentDto commentDto) {
        Board board = checkDeletedBoard(boardId);
        User user = commonUtil.findUser(userId);
        Comment child = commentMapper.toEntity(commentDto, user, board);

        Comment parent = findComment(commentId);
        isCommentDeleted(parent); // 삭제된 댓글인지 검사 (삭제된 댓글엔 대댓글을 작성할 수 없다.)
        parent.addChildComment(child);

        Comment comment = commentRepository.save(child);
        board.increaseCommentCount(); // 댓글수 증가
        return commentMapper.toDto(comment);
    }

    /**
     * 댓글 수정
     *
     * @param commentDto : 수정한 댓글 정보
     */
    @Transactional
    public CommentDto update(Long boardId, CommentDto commentDto) {
        checkDeletedBoard(boardId);
        Comment comment = findComment(commentDto.getId());
        comment.update(commentDto);
        return commentMapper.toDto(comment);
    }

    /**
     * 댓글 삭제
     *
     * @param commentId : 댓글 식별자
     */
    @Transactional
    public void delete(Long boardId, Long commentId) {
        Board board = checkDeletedBoard(boardId);
        Comment comment = findComment(commentId);
        comment.delete();
        board.decreaseCommentCount();
    }

    /**
     * 댓글 리스트 조회
     *
     * @param boardId : 게시글 식별자
     */
    public List<CommentDto> findAll(Long boardId) {
        commonUtil.findBoard(boardId);
        List<Comment> commentList = commentQueryRepository.findCommentByBoardId(boardId);
        return convertToNestedStructure(commentList);
    }

    /**
     * 회원 권한 검증 (수정, 삭제 시)
     */
    public void checkCommentAuthor(Long commentId, Long userId) {
        User author = findComment(commentId).getUser();
        User user = commonUtil.findUser(userId);
        if (!author.equals(user)) {
            throw new UserNotCommentAuthorException(user.getId());
        }
    }

    /**
     * 삭제된 게시글인지 검사
     * (삭제된 게시글에선 댓글과 관련된 작업을 할 수 없다.)
     */
    private Board checkDeletedBoard(Long boardId) {
        Board board = commonUtil.findBoard(boardId);
        if (board.getIsDeleted()) {
            throw new DeletedBoardException(boardId);
        }
        return board;
    }

    /**
     * 특정 댓글 검색
     */
    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }

    /**
     * 이미 삭제된 댓글인지 검사
     */
    private void isCommentDeleted(Comment parent) {
        if (parent.getIsDeleted()) {
            throw new CommentAlreadyDeletedException(parent.getId());
        }
    }

    /**
     * 댓글리스트를 중첩구조로 바꾼다.
     */
    private List<CommentDto> convertToNestedStructure(List<Comment> comments) {
        List<CommentDto> result = new ArrayList<>();
        Map<Long, CommentDto> map = new HashMap<>();
        comments.stream().forEach(comment -> {
            CommentDto dto = commentMapper.toDto(comment);
            map.put(dto.getId(), dto);
            if (comment.getParent() != null) {
                map.get(comment.getParent().getId()).getChildren().add(dto);
            } else {
                result.add(dto);
            }
        });
        return result;
    }
}

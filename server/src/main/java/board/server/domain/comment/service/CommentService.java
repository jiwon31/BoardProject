package board.server.domain.comment.service;

import board.server.common.exception.CommentNotFoundException;
import board.server.common.exception.UserNotCommentAuthorException;
import board.server.common.util.CommonUtil;
import board.server.domain.board.entity.Board;
import board.server.domain.comment.dto.CommentDto;
import board.server.domain.comment.entity.Comment;
import board.server.domain.comment.mapper.CommentMapper;
import board.server.domain.comment.repository.CommentRepository;
import board.server.domain.user.entitiy.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
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
        User user = commonUtil.findUser(userId);
        Board board = commonUtil.findBoard(boardId);
        Comment comment = commentMapper.toEntity(commentDto, user, board);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    /**
     * 댓글 수정
     *
     * @param commentDto : 수정한 댓글 정보
     */
    @Transactional
    public CommentDto update(CommentDto commentDto) {
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
    public void delete(Long commentId) {
        Comment comment = findComment(commentId);
        comment.delete();
    }

    /**
     * 댓글 리스트 조회
     *
     * @param boardId : 게시글 식별자
     */
    public List<CommentDto> findAll(Long boardId) {
        Board board = commonUtil.findBoard(boardId);
        List<Comment> commentList = commentRepository.findAllByBoard(board);
        return commentMapper.toDtoList(commentList);
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
     * 특정 댓글 검색
     */
    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }
}

package board.server.domain.comment.service;

import board.server.common.exception.BoardNotFoundException;
import board.server.common.exception.UserNotFoundException;
import board.server.domain.board.entity.Board;
import board.server.domain.board.repository.BoardRepository;
import board.server.domain.comment.dto.CommentDto;
import board.server.domain.comment.entity.Comment;
import board.server.domain.comment.mapper.CommentMapper;
import board.server.domain.comment.repository.CommentRepository;
import board.server.domain.user.entitiy.User;
import board.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    /**
     * 댓글 생성
     * @param userId : 유저 식별자
     * @param boardId : 게시글 식별자
     * @param commentDto : 댓글 정보
     */
    @Transactional
    public CommentDto create(Long userId, Long boardId, CommentDto commentDto) {
        User user = findUser(userId);
        Board board = findBoard(boardId);
        Comment comment = commentMapper.toEntity(commentDto, user, board);
        return commentMapper.toDto(commentRepository.save(comment));
    }


    /**
     * 특정 유저 검색
     */
    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 특정 게시글 검색
     */
    private Board findBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id));
    }
}

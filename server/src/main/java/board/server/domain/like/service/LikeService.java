package board.server.domain.like.service;

import board.server.common.exception.AlreadyLikedBoardException;
import board.server.common.exception.NotLikedBoardException;
import board.server.common.util.CommonUtil;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.entity.Board;
import board.server.domain.like.entity.Like;
import board.server.domain.like.repository.LikeRepository;
import board.server.domain.like.repository.querydsl.LikeQueryRepository;
import board.server.domain.user.entitiy.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static board.server.common.util.SecurityUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeQueryRepository likeQueryRepository;

    private final CommonUtil commonUtil;

    /**
     * 게시글 좋아요
     *
     * @param boardId : 게시글 식별자
     */
    @Transactional
    public void add(Long boardId) {
        User user = commonUtil.findUser(getUserId());
        Board board = commonUtil.findBoard(boardId);

        if (likeRepository.existsByUserAndBoard(user, board)) {
            throw new AlreadyLikedBoardException(boardId);
        }

        likeRepository.save(Like.builder()
                .user(user)
                .board(board)
                .build());
        board.increaseLikeCount();
    }

    /**
     * 게시글 좋아요 취소
     *
     * @param boardId : 게시글 식별자
     */
    @Transactional
    public void delete(Long boardId) {
        User user = commonUtil.findUser(getUserId());
        Board board = commonUtil.findBoard(boardId);

        Like like = likeRepository.findByUserAndBoard(user, board)
                .orElseThrow(() -> new NotLikedBoardException(boardId));

        likeRepository.deleteById(like.getId());
        board.decreaseLikeCount();
    }

    /**
     * 유저가 좋아요한 게시글만 조회
     *
     * @param userId : 유저 식별자
     */
    public List<BoardDto> findUserLikedBoardList(Long userId) {
        return likeQueryRepository.findUserLikedBoardList(userId);
    }
}

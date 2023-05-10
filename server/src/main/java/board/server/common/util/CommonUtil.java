package board.server.common.util;

import board.server.common.exception.BoardNotFoundException;
import board.server.common.exception.UserNotFoundException;
import board.server.domain.board.entity.Board;
import board.server.domain.board.repository.BoardRepository;
import board.server.domain.user.entitiy.User;
import board.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommonUtil {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /**
     * 특정 유저 검색
     */
    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 특정 게시글 검색
     */
    public Board findBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id));
    }
}

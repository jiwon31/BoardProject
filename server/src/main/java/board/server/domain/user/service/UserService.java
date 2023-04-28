package board.server.domain.user.service;

import board.server.common.exception.UserNotFoundException;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.entity.Board;
import board.server.domain.board.mapper.BoardMapper;
import board.server.domain.board.repository.BoardRepository;
import board.server.domain.user.entitiy.User;
import board.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);

    /**
     * 테스트 유저 생성
     */
    // TODO: 회원가입 구현 후 삭제
    @Transactional
    public void createTestUser() {
        userRepository.save(new User("user1@gmail.com", "1234", "user1", false));
        userRepository.save(new User("user2@gmail.com", "5678", "user2", false));
        userRepository.save(new User("user3@gmail.com", "9012", "user3", false));
    }

    /**
     * 사용자가 작성한 게시글 리스트 조회
     * @param userId : 유저 식별자
     */
    public List<BoardDto> findMyBoardList(Long userId) {
        User user = findUser(userId);
        List<Board> boardList = boardRepository.findAllByUser(user);
        return boardMapper.toDtoList(boardList);
    }

    /**
     * 특정 유저 검색
     */
    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}

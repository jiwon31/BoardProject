package board.server.domain.board.service;

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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);

    /**
     * 게시글 생성
     * @param userId 유저 식별자
     * @param boardDto
     * @return
     */
    @Transactional
    public BoardDto create(Long userId, BoardDto boardDto) {
        User user = findUser(userId);
        Board board = boardMapper.toEntity(boardDto, user);
        return boardMapper.toDto(boardRepository.save(board));
    }

    // 수정
    // 삭제
    // 조회

    /**
     * 유저 검색
     */
    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}

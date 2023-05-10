package board.server.domain.user.service;

import board.server.common.util.CommonUtil;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.entity.Board;
import board.server.domain.board.mapper.BoardMapper;
import board.server.domain.board.repository.BoardRepository;
import board.server.domain.user.dto.UserDto;
import board.server.domain.user.entitiy.User;
import board.server.domain.user.mapper.UserMapper;
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
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);
    private final CommonUtil commonUtil;

    /**
     * 사용자 정보 조회
     *
     * @param userId
     */
    public UserDto findUserInfo(Long userId) {
        User user = commonUtil.findUser(userId);
        return userMapper.toDto(user);
    }

    /**
     * 사용자가 작성한 게시글 리스트 조회
     *
     * @param userId : 유저 식별자
     */
    public List<BoardDto> findMyBoardList(Long userId) {
        User user = commonUtil.findUser(userId);
        List<Board> boardList = boardRepository.findAllByUser(user);
        return boardMapper.toDtoList(boardList);
    }
}

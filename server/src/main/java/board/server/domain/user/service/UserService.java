package board.server.domain.user.service;

import board.server.common.util.CommonUtil;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.entity.Board;
import board.server.domain.board.mapper.BoardMapper;
import board.server.domain.board.repository.BoardRepository;
import board.server.domain.user.dto.UserDto;
import board.server.domain.user.entitiy.User;
import board.server.domain.user.mapper.UserMapper;
import board.server.domain.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final BoardRepository boardRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);
    private final CommonUtil commonUtil;
    private final UserUtil userUtil;

    /**
     * 사용자 정보 조회
     *
     * @param userId : 유저 식별자
     */
    public UserDto findUserInfo(Long userId) {
        User user = commonUtil.findUser(userId);
        return userMapper.toDto(user);
    }

    /**
     * 사용자 정보 수정
     *
     * @param userId  : 유저 식별자
     * @param userDto : 수정한 유저 정보
     */
    @Transactional
    public UserDto updateUserInfo(Long userId, UserDto userDto) {
        User user = commonUtil.findUser(userId);
        checkDuplicateEmailAndUsername(userDto, user);
        user.update(userDto);
        return userMapper.toDto(user);
    }

    /**
     * 사용자가 작성한 게시글 리스트 조회
     *
     * @param userId : 유저 식별자
     */
    public Page<BoardDto> findMyBoardList(Long userId, Pageable pageable) {
        User user = commonUtil.findUser(userId);
        Page<Board> boardList = boardRepository.findAllByUserOrderByCreatedAtDesc(user, pageable);
        return boardList.map((board -> boardMapper.toDto(board)));
    }

    /**
     * 이메일과 닉네임 중복 검사
     */
    private void checkDuplicateEmailAndUsername(UserDto userDto, User user) {
        if (!user.getEmail().equals(userDto.getEmail())) {
            userUtil.checkEmailDuplicate(userDto.getEmail());
        }
        if (!user.getUserName().equals(userDto.getUserName())) {
            userUtil.checkUserNameDuplicate(userDto.getUserName());
        }
    }
}
package board.server.domain.user.util;

import board.server.common.exception.DuplicateEmailException;
import board.server.common.exception.DuplicateUserNameException;
import board.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    /**
     * 이메일 중복 검사
     */
    public void checkEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
    }

    /**
     * 닉네임 중복 검사
     */
    public void checkUserNameDuplicate(String userName) {
        if (userRepository.existsByUserName(userName)) {
            throw new DuplicateUserNameException(userName);
        }
    }
}

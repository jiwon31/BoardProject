package board.server.domain.user.service;

import board.server.domain.user.entitiy.User;
import board.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
}

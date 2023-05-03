package board.server.domain.user.service;

import board.server.common.exception.DuplicateEmailException;
import board.server.common.exception.DuplicateUserNameException;
import board.server.common.jwt.TokenProvider;
import board.server.domain.user.dto.UserDto;
import board.server.domain.user.entitiy.User;
import board.server.domain.user.mapper.UserMapper;
import board.server.domain.user.repository.RefreshTokenRepository;
import board.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Transactional
    public void signup(UserDto userDto) {
        checkEmailDuplicate(userDto.getEmail()); // 이메일 중복 검사
        checkUserNameDuplicate(userDto.getUserName()); // 닉네임 중복 검사
        User user = userDto.toEntity(passwordEncoder);
        userRepository.save(user);
    }

    public boolean checkUserNameDuplicate(String userName) {
        if (userRepository.existsByUserName(userName)) {
            throw new DuplicateUserNameException(userName);
        }
        return false;
    }

    public boolean checkEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
        return false;
    }
}

package board.server.domain.user.service;

import board.server.common.exception.DuplicateEmailException;
import board.server.common.exception.DuplicateUserNameException;
import board.server.common.jwt.TokenProvider;
import board.server.domain.user.dto.TokenDto;
import board.server.domain.user.dto.UserDto;
import board.server.domain.user.entitiy.RefreshToken;
import board.server.domain.user.entitiy.User;
import board.server.domain.user.repository.RefreshTokenRepository;
import board.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
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

    /**
     * 회원가입
     *
     * @param userDto : 이메일, 비밀번호, 닉네임
     */
    @Transactional
    public void signup(UserDto userDto) {
        checkEmailDuplicate(userDto.getEmail()); // 이메일 중복 검사
        checkUserNameDuplicate(userDto.getUserName()); // 닉네임 중복 검사
        User user = userDto.toEntity(passwordEncoder);
        userRepository.save(user);
    }

    /**
     * 로그인
     *
     * @param userDto : 이메일, 비밀번호
     * @return
     */
    @Transactional
    public TokenDto login(UserDto userDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = userDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        // authenticate 메서드가 실행이 될 때 CustomUserDetailsService 의 loadUserByUsername 메서드가 실행된다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        String accessToken = tokenProvider.createAccessToken(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .token(tokenProvider.createRefreshToken(authentication))
                .build();

        refreshTokenRepository.save(refreshToken);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    /**
     * 토큰 재발급
     *
     * @param token : Refresh Token
     * @return
     */
    @Transactional
    public TokenDto reissue(String token) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(token)) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다. 다시 로그인해주세요.");
        }

        // 2. Refresh Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(token);

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getToken().equals(token)) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 Access Token 생성
        String accessToken = tokenProvider.createAccessToken(authentication);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    public void checkUserNameDuplicate(String userName) {
        if (userRepository.existsByUserName(userName)) {
            throw new DuplicateUserNameException(userName);
        }
    }

    public void checkEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
    }
}

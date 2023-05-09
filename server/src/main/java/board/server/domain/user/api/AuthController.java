package board.server.domain.user.api;

import board.server.domain.user.api.request.LoginRequest;
import board.server.domain.user.api.request.SignupRequest;
import board.server.domain.user.api.response.AuthResponse;
import board.server.domain.user.dto.TokenDto;
import board.server.domain.user.dto.UserDto;
import board.server.domain.user.mapper.AuthDtoMapper;
import board.server.domain.user.mapper.UserDtoMapper;
import board.server.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

import static board.server.common.util.SecurityUtil.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserDtoMapper userDtoMapper = Mappers.getMapper(UserDtoMapper.class);
    private final AuthDtoMapper authDtoMapper = Mappers.getMapper(AuthDtoMapper.class);

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest request) {
        UserDto requestDto = userDtoMapper.fromSignupRequest(request);
        authService.signup(requestDto);
        return ResponseEntity.ok("회원가입 완료");
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        UserDto requestDto = userDtoMapper.fromLoginRequest(request);
        TokenDto tokenDto = authService.login(requestDto);

        return ResponseEntity.ok()
                .header("Set-Cookie", generateCookie(tokenDto).toString())
                .body(authDtoMapper.toAuthResponse(tokenDto));
    }

    /**
     * 토큰 재발급
     */
    @PostMapping("/reissue")
    public ResponseEntity<AuthResponse> reissue(@CookieValue(value = "refreshToken") Cookie refreshTokenCookie) {
        TokenDto tokenDto = authService.reissue(refreshTokenCookie.getValue());
        return ResponseEntity.ok(authDtoMapper.toAuthResponse(tokenDto));
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout(getUserId());
        return ResponseEntity.ok()
                .header("Set-Cookie", clearCookie().toString())
                .body("로그아웃 완료");
    }

    /**
     * refresh Token 을 쿠키에 저장
     */
    private static ResponseCookie generateCookie(TokenDto tokenDto) {
        return ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .maxAge(604800) // 일주일
                .path("/")
                .httpOnly(true)
                .secure(false)
                .build();
    }

    /**
     * 로그아웃 시 refresh Token 쿠키를 삭제
     */
    private static ResponseCookie clearCookie() {
        return ResponseCookie.from("refreshToken", "")
                .maxAge(0)
                .path("/")
                .build();
    }
}

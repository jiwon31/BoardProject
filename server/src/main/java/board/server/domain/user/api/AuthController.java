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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserDtoMapper userDtoMapper = Mappers.getMapper(UserDtoMapper.class);
    private final AuthDtoMapper authDtoMapper = Mappers.getMapper(AuthDtoMapper.class);

    /**
     * 회원가입
     *
     * @param request
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest request) {
        UserDto requestDto = userDtoMapper.fromSignupRequest(request);
        authService.signup(requestDto);
        return ResponseEntity.ok("회원가입 완료");
    }

    /**
     * 로그인
     *
     * @param request
     * @return
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
     *
     * @param refreshTokenCookie
     * @return
     */
    @PostMapping("/reissue")
    public ResponseEntity<AuthResponse> reissue(
            @CookieValue(value = "refreshToken", required = true) Cookie refreshTokenCookie) {
        TokenDto tokenDto = authService.reissue(refreshTokenCookie.getValue());
        return ResponseEntity.ok(authDtoMapper.toAuthResponse(tokenDto));
    }

    /**
     * refreshToken 을 쿠키에 저장
     *
     * @param tokenDto
     * @return
     */
    private static ResponseCookie generateCookie(TokenDto tokenDto) {
        return ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .maxAge(604800) // 일주일
                .path("/")
                .httpOnly(true)
                .secure(false)
                .build();
    }
}

package board.server.domain.user.api;

import board.server.domain.user.api.request.LoginRequest;
import board.server.domain.user.api.request.SignupRequest;
import board.server.domain.user.api.response.LoginResponse;
import board.server.domain.user.dto.TokenDto;
import board.server.domain.user.dto.UserDto;
import board.server.domain.user.mapper.AuthDtoMapper;
import board.server.domain.user.mapper.UserDtoMapper;
import board.server.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

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
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        UserDto requestDto = userDtoMapper.fromLoginRequest(request);
        TokenDto tokenDto = authService.login(requestDto);

        return ResponseEntity.ok()
                .header("Set-Cookie", generateCookie(tokenDto).toString())
                .body(authDtoMapper.toLoginResponse(tokenDto));
    }

    /**
     * refreshToken 쿠키에 저장
     *
     * @param tokenDto
     * @return
     */
    private static ResponseCookie generateCookie(TokenDto tokenDto) {
        return ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .maxAge(604800) // 일주일
                .path("/")
                .secure(false)
                .httpOnly(true)
                .build();
    }
}

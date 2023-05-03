package board.server.domain.user.api;

import board.server.domain.user.api.request.SignupRequest;
import board.server.domain.user.dto.UserDto;
import board.server.domain.user.mapper.UserDtoMapper;
import board.server.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
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
    private final UserDtoMapper dtoMapper = Mappers.getMapper(UserDtoMapper.class);

    /**
     * 회원가입
     *
     * @param request
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<Objects> signup(@RequestBody @Valid SignupRequest request) {
        UserDto requestDto = dtoMapper.fromSignupRequest(request);
        authService.signup(requestDto);
        return ResponseEntity.ok().build();
    }
}

package board.server.common.config;

import board.server.common.handler.CustomAccessDeniedHandler;
import board.server.common.handler.CustomAuthenticationEntryPoint;
import board.server.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 설정 Disable
        http.csrf().disable()

                // exception handling
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .cors()

                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 토큰이 없는 상태에서 들어올 수 있는 요청은 permitAll 설정
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/reissue").permitAll()
                .antMatchers(HttpMethod.POST, "/users/email/exists").permitAll()
                .antMatchers(HttpMethod.POST, "/users/username/exists").permitAll()
                .antMatchers(HttpMethod.GET, "/boards").permitAll()
                .antMatchers(HttpMethod.GET, "/boards/{boardId}").permitAll()
                .antMatchers(HttpMethod.GET, "/boards/{boardId}/comments").permitAll()
                .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }
}

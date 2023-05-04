package board.server.common.jwt;

import board.server.domain.user.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 유저 정보로 JWT 토큰을 만들거나 토큰을 바탕으로 유저 정보를 가져옴
 */
@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    // Secret Key
    @Value("${jwt.token.secret}")
    private String secretKey;

    // encrypted Secret Key
    private Key key;

    // AccessToken 유효시간(30분)
    @Value("${jwt.access-token.expire-length}")
    private long accessTokenValidMilSecond;

    // RefreshToken 유효시간(일주일)
    @Value("${jwt.refresh-token.expire-length}")
    private long refreshTokenValidMilSecond;

    // SecretKey 암호화 + 초기화
    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(this.secretKey.getBytes());
    }

    /**
     * 유저 정보를 넘겨받아서 Access Token 과 Refresh Token 을 생성
     *
     * @param authentication
     * @return
     */
    public TokenDto generateToken(Authentication authentication) {
        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();

        String accessToken = createAccessToken(authentication, authorities, now);

        String refreshToken = createRefreshToken(now);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Spring Security 인증토큰 발급
     *
     * @param accessToken
     * @return
     */
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        Collection<? extends GrantedAuthority> authorities = getAuthorities(claims);

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * 토큰 정보를 검증
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /**
     * 클레임에서 권한 정보 가져오기
     *
     * @param claims
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        return Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * 토큰에서 토큰 데이터를 추출
     *
     * @param accessToken
     * @return 토큰 데이터
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * AccessToken 생성
     *
     * @param authentication
     * @param authorities
     * @param now
     * @return AccessToken
     */
    private String createAccessToken(Authentication authentication, String authorities, long now) {
        return Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "USER"
                .setExpiration(new Date(now + accessTokenValidMilSecond))        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS256)    // header "alg": "HS256"
                .compact();
    }

    /**
     * RefreshToken 생성
     *
     * @param now
     * @return RefreshToken
     */
    private String createRefreshToken(long now) {
        return Jwts.builder()
                .setExpiration(new Date(now + refreshTokenValidMilSecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}

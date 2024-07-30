package org.likelion.zagabi.Domain.Account.Jwt.Util;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Account.Jwt.Dto.JwtDto;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.SecurityCustomException;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.TokenErrorCode;
import org.likelion.zagabi.Domain.Account.Jwt.UserDetails.CustomUserDetails;
import org.likelion.zagabi.Domain.Account.Jwt.UserDetails.CustomUserDetailService;
import org.likelion.zagabi.Global.Util.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtProvider {

    private final CustomUserDetailService customUserDetailService; // 사용자 세부 정보를 로드하는 서비스
    private final SecretKey secretKey;
    private final Long accessExpMs;
    private final Long refreshExpMs;
    private final RedisUtil redisUtil;

    public JwtProvider(
            CustomUserDetailService customUserDetailService, @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.token.access-expiration-time}") Long access,
            @Value("${spring.jwt.token.refresh-expiration-time}") Long refresh,
            RedisUtil redis) {
        this.customUserDetailService = customUserDetailService;
        // 비밀 키 초기화
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
        accessExpMs = access;
        refreshExpMs = refresh;
        redisUtil = redis;

    }
    public String createJwtAccessToken(CustomUserDetails customUserDetails) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plusMillis(accessExpMs);

        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .claim("email", customUserDetails.getUsername())
                .claim("is_admin", customUserDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList().contains("ROLE_ADMIN"))
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }

    public String createJwtRefreshToken(CustomUserDetails customUserDetails) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plusMillis(refreshExpMs);

        String refreshToken = Jwts.builder()
                .header()
                .add("alg", "HS256")
                .add("typ", "JWT")
                .and()
                .claim("email", customUserDetails.getUsername())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();

        redisUtil.save(
                customUserDetails.getUsername() + ":refresh",
                refreshToken,
                refreshExpMs,
                TimeUnit.MILLISECONDS
        );
        return refreshToken;
    }

    // jwt 에서 회원 구분 pk 추출
    public String getUserPk(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }


    public boolean validateRefreshToken(String refreshToken) {
        try {
            String username = getUserEmail(refreshToken);

            // 블랙리스트에 있는지 확인
            if (redisUtil.hasKey("blacklist:" + refreshToken)) {
                throw new SecurityCustomException(TokenErrorCode.INVALID_TOKEN);
            }

            // redis에서 사용자 이름으로 저장된 토큰이 있는지 확인
            if (!redisUtil.hasKey(username + ":refresh")) {
                throw new SecurityCustomException(TokenErrorCode.INVALID_TOKEN);
            }
            return true;
        } catch (Exception e) {
            throw new SecurityCustomException(TokenErrorCode.INVALID_TOKEN);
        }
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String authorization = request.getHeader("RefreshToken");
        if (authorization == null || authorization.isEmpty()) {
            log.warn("[*] No Refresh Token in req");
            return null;
        }
        return authorization;
    }

    public Long getRefreshExpTime(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
                .getTime();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {

            log.warn("[*] No Token in req");

            return null;
        }

        log.info("[*] Token exists");

        return authorization.split(" ")[1];
    }

    public String getUserEmail(String token) throws SignatureException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }

    public String reissueToken(String refreshToken) throws SignatureException {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(getUserEmail(refreshToken));

        return createJwtAccessToken((CustomUserDetails)userDetails);
    }

    public Long getExpTime(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
                .getTime();
    }
}

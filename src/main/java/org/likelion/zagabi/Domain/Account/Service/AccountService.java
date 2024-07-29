package org.likelion.zagabi.Domain.Account.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.JwtTokenizer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Account.Dto.Request.UserLoginRequestDto;
import org.likelion.zagabi.Domain.Account.Dto.Request.UserSignUpRequestDto;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserLoginResponseDto;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserSignUpResponseDto;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Account.Jwt.UserDetails.CustomUserDetails;
import org.likelion.zagabi.Domain.Account.Jwt.Util.JwtProvider;
import org.likelion.zagabi.Domain.Account.Repository.UserJpaRepository;
import org.likelion.zagabi.Global.Util.RedisUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisUtil redisUtil;
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {

        // 회원 정보 존재 하는지 확인
        User user = userJpaRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 회원 pw 일치 여부
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // 로그인 성공 시 토큰 생성
        String accessToken = jwtProvider.createJwtAccessToken(customUserDetails);
        String refreshToken = jwtProvider.createJwtRefreshToken(customUserDetails);

        return UserLoginResponseDto.from(user, accessToken, refreshToken);
    }

    public UserSignUpResponseDto signup(UserSignUpRequestDto requestDto) {


        // 이메일 중복 확인
        if (userJpaRepository.existsByEmail(requestDto.email())) {
            throw new IllegalArgumentException("해당 이메일이 존재합니다.");
        }

        String encodedPw = passwordEncoder.encode(requestDto.password());
        User user = requestDto.toEntity(encodedPw);

        return UserSignUpResponseDto.from(userJpaRepository.save(user));
    }

    public void logout(HttpServletRequest request) {
        try {
            String accessToken = jwtProvider.resolveAccessToken(request);

            redisUtil.removeFCMToken(jwtProvider.getUserEmail(accessToken));

            redisUtil.save(
                    accessToken,
                    "logout",
                    jwtProvider.getExpTime(accessToken),
                    TimeUnit.MILLISECONDS
            );

            redisUtil.delete(
                    jwtProvider.getUserEmail(accessToken)
            );
        } catch (ExpiredJwtException e) {
            throw new SecurityCustomException(TokenErrorCode.TOKEN_EXPIRED);
        }
    }

}

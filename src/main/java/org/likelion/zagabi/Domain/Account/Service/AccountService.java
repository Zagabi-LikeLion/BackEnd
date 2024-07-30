package org.likelion.zagabi.Domain.Account.Service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Account.Dto.Request.*;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserLoginResponseDto;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserSignUpResponseDto;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.SecurityCustomException;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.TokenErrorCode;
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

    public void forgotPassword(ForgotPwRequestDto requestDto) {
        User user = userJpaRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(!requestDto.email().equals(user.getEmail())) {
            throw new IllegalArgumentException("입력하신 이메일이 일치하지 않습니다.");
        }

        if(!requestDto.securityAnswer().equals(user.getSecurityAnswer())){
            throw new IllegalArgumentException("입력하신 질문에 대답이 일치하지 않습니다.");
        }
    }
//    public void updatePassword(HttpServletRequest request, ChangePwRequestDto requestDto, User user) {
//        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//        if (requestDto.password().equals(requestDto.newPassword())) {
//            throw new IllegalArgumentException("같은 비밀번호를 사용할 수 없습니다.");
//        }
//        user.updatePassword(passwordEncoder.encode(requestDto.newPassword()));
//        logout(request); // 사용자를 로그아웃합니다.
//    }
    public void updatePassword(HttpServletRequest request, ChangePwRequestDto requestDto) {
        String accessToken = jwtProvider.resolveAccessToken(request);
        String userEmail = jwtProvider.getUserEmail(accessToken);
        User user = userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if (requestDto.password().equals(requestDto.newPassword())) {
            throw new IllegalArgumentException("같은 비밀번호를 사용할 수 없습니다.");
        }
        user.updatePassword(passwordEncoder.encode(requestDto.newPassword()));
        logout(request); // 사용자를 로그아웃합니다.
    }

    public void deleteAccount(HttpServletRequest request) {
        try {
            String accessToken = jwtProvider.resolveAccessToken(request);
            String userEmail = jwtProvider.getUserEmail(accessToken);

            User user = userJpaRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            // 사용자 정보 삭제
            userJpaRepository.delete(user);

            // Redis에 저장된 토큰 정보 삭제
            redisUtil.delete(accessToken);
            redisUtil.delete(userEmail);

        } catch (ExpiredJwtException e) {
            throw new SecurityCustomException(TokenErrorCode.TOKEN_EXPIRED);
        }
    }
    public void changeNickname(HttpServletRequest request, ChangeNicknameRequestDto requestDto) {
        try {
            String accessToken = jwtProvider.resolveAccessToken(request);
            String userEmail = jwtProvider.getUserEmail(accessToken);

            User user = userJpaRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            // 닉네임 변경
            user.updateNickname(requestDto.newNickName());
            userJpaRepository.save(user);

        } catch (ExpiredJwtException e) {
            throw new SecurityCustomException(TokenErrorCode.TOKEN_EXPIRED);
        }
    }

}
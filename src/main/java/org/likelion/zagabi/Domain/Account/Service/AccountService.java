package org.likelion.zagabi.Domain.Account.Service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Account.Dto.Request.*;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserLoginResponseDto;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserSignUpResponseDto;
import org.likelion.zagabi.Domain.Account.Entity.SecurityQuestion;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.SecurityCustomException;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.TokenErrorCode;
import org.likelion.zagabi.Domain.Account.Jwt.UserDetails.CustomUserDetails;
import org.likelion.zagabi.Domain.Account.Jwt.Util.JwtProvider;
import org.likelion.zagabi.Domain.Account.Repository.SecurityQuestionRepository;
import org.likelion.zagabi.Domain.Account.Repository.UserJpaRepository;
import org.likelion.zagabi.Global.Util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final UserJpaRepository userJpaRepository;
    private final SecurityQuestionRepository securityQuestionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisUtil redisUtil;
    private final MailService mailService;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

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

        // 보안 질문 확인
        SecurityQuestion securityQuestion = securityQuestionRepository.findById(requestDto.securityQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("보안 질문을 찾을 수 없습니다."));

        String encodedPw = passwordEncoder.encode(requestDto.password());
        User user = requestDto.toEntity(encodedPw, securityQuestion);

        return UserSignUpResponseDto.from(userJpaRepository.save(user));
    }

    public void logout(HttpServletRequest request) {
        try {
            String accessToken = jwtProvider.resolveAccessToken(request);
            if (accessToken != null) {
                String userEmail = jwtProvider.getUserEmail(accessToken);
                String redisKey = userEmail + ":refresh";
                String refreshToken = (String) redisUtil.get(redisKey);

                // 블랙리스트에 refreshToken 추가
                if (refreshToken != null) {
                    redisUtil.save(
                            "blacklist:" + refreshToken,
                            userEmail,
                            jwtProvider.getRefreshExpTime(refreshToken),
                            TimeUnit.MILLISECONDS
                    );
                    redisUtil.delete(redisKey);
                }

            }
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

    public void updatePassword(HttpServletRequest request, ChangePwRequestDto requestDto) {
        String accessToken = jwtProvider.resolveAccessToken(request);
        String userEmail = jwtProvider.getUserEmail(accessToken);
        User user = userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.getPassword().equals(requestDto.newPassword())) {
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
            String redisKey = userEmail + ":refresh";
            redisUtil.delete(redisKey);

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

        } catch (ExpiredJwtException e) {
            throw new SecurityCustomException(TokenErrorCode.TOKEN_EXPIRED);
        }
    }

    // Email로 Code를 보내는 부분
    public void sendCodeToEmail(String toEmail) {
        Optional<User> user = userJpaRepository.findByEmail(toEmail);
        if (user.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        String title = "자가비 이메일 인증 번호";
        String authCode = this.createCode();
        mailService.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisUtil.save(
                AUTH_CODE_PREFIX + toEmail,
                authCode,
                Duration.ofMillis(this.authCodeExpirationMillis)
        );

    }

    // 무작위로 인증번호 생성 코드
    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("인증 번호 생성을 위한 알고리즘을 찾을 수 없습니다.");
        }
    }

    // 사용자가 받은 인증 코드와 사용자가 보낸 인증코드가 일치하는지 체크하는 부분
    public void verifiedCode(String email, String authCode) {
        Optional<User> user = userJpaRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        String redisAuthCode = (String) redisUtil.get(AUTH_CODE_PREFIX + email);
        //redisAuthCode가 null인지 아닌지 확인하고, redisAuthCode가 authCode와 같은지 체크
        boolean authResult = redisAuthCode != null && redisAuthCode.equals(authCode);


        if (!authResult) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        } else {
            log.info("인증 성공");
        }
    }
}

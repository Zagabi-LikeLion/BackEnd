package org.likelion.zagabi.Domain.Account.Controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Account.Dto.Request.*;
import org.likelion.zagabi.Domain.Account.Dto.Response.SecurityQuestionResponseDto;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserLoginResponseDto;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserSignUpResponseDto;
import org.likelion.zagabi.Domain.Account.Jwt.Dto.JwtDto;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.SecurityCustomException;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.TokenErrorCode;
import org.likelion.zagabi.Domain.Account.Jwt.Util.JwtProvider;
import org.likelion.zagabi.Domain.Account.Service.AccountQueryService;
import org.likelion.zagabi.Domain.Account.Service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountQueryService accountQueryService;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = accountService.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> signup(@RequestBody @Valid UserSignUpRequestDto requestDto) {
        UserSignUpResponseDto responseDto = accountService.signup(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        accountService.logout(request);
        return ResponseEntity.ok().body(Map.of("message", "로그아웃이 성공적으로 완료되었습니다."));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPwRequestDto requestDto) {
        accountService.forgotPassword(requestDto);
        return ResponseEntity.ok().body(Map.of("message", "본인 인증이 완료되었습니다, 비밀번호 변경 창으로 이동합니다."));
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(HttpServletRequest request, @RequestBody @Valid ChangePwRequestDto requestDto) {
        accountService.updatePassword(request, requestDto);
        return ResponseEntity.ok().body(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(HttpServletRequest request) {
        accountService.deleteAccount(request);
        return ResponseEntity.ok().body(Map.of("message", "성공적으로 탈퇴되었습니다."));
    }

    @PatchMapping("/change-nickname")
    public ResponseEntity<?> changeNickname(HttpServletRequest request, @RequestBody @Valid ChangeNicknameRequestDto requestDto) {
        accountService.changeNickname(request, requestDto);
        return ResponseEntity.ok().body(Map.of("message", "닉네임이 성공적으로 변경되었습니다."));
    }

    @GetMapping("/reissue")
    public ResponseEntity<JwtDto> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
        try {
            jwtProvider.validateRefreshToken(refreshToken);
            JwtDto newToken = jwtProvider.reissueToken(refreshToken);
            return ResponseEntity.ok(newToken);
        } catch (ExpiredJwtException eje) {
            throw new SecurityCustomException(TokenErrorCode.TOKEN_EXPIRED, eje);
        } catch (IllegalArgumentException iae) {
            throw new SecurityCustomException(TokenErrorCode.INVALID_TOKEN, iae);
        }
    }

    public ResponseEntity<List<SecurityQuestionResponseDto>> getDiaryQuestions() {
        List<SecurityQuestionResponseDto> responseDtos = accountQueryService.getSecurityQuestions();
        return ResponseEntity.ok(responseDtos);
    }
}

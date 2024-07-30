package org.likelion.zagabi.Domain.Account.Controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Account.Dto.Request.*;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserLoginResponseDto;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserSignUpResponseDto;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Account.Jwt.Dto.JwtDto;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.SecurityCustomException;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.TokenErrorCode;
import org.likelion.zagabi.Domain.Account.Jwt.Util.JwtProvider;
import org.likelion.zagabi.Domain.Account.Service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = accountService.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> signup(@RequestBody UserSignUpRequestDto requestDto) {
        UserSignUpResponseDto responseDto = accountService.signup(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        accountService.logout(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPwRequestDto requestDto) {
        accountService.forgotPassword(requestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(HttpServletRequest request, @RequestBody ChangePwRequestDto requestDto) {
        accountService.updatePassword(request, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<Void> deleteAccount(HttpServletRequest request) {
        accountService.deleteAccount(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-nickname")
    public ResponseEntity<Void> changeNickname(HttpServletRequest request, @RequestBody ChangeNicknameRequestDto requestDto) {
        accountService.changeNickname(request, requestDto);
        return ResponseEntity.ok().build();
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
}

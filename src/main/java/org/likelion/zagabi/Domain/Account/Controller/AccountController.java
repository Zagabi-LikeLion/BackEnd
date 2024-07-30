package org.likelion.zagabi.Domain.Account.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Account.Dto.Request.*;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserLoginResponseDto;
import org.likelion.zagabi.Domain.Account.Dto.Response.UserSignUpResponseDto;
import org.likelion.zagabi.Domain.Account.Service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

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
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        accountService.logout(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody @Valid ForgotPwRequestDto requestDto) {
        accountService.forgotPassword(requestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(HttpServletRequest request, @RequestBody @Valid ChangePwRequestDto requestDto) {
        accountService.updatePassword(request, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<Void> deleteAccount(HttpServletRequest request) {
        accountService.deleteAccount(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/change-nickname")
    public ResponseEntity<Void> changeNickname(HttpServletRequest request, @RequestBody @Valid ChangeNicknameRequestDto requestDto) {
        accountService.changeNickname(request, requestDto);
        return ResponseEntity.ok().build();
    }
}

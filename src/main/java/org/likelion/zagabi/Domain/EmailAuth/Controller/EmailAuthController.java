package org.likelion.zagabi.Domain.EmailAuth.Controller;

import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.EmailAuth.Service.EmailAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/email-auth")
@RequiredArgsConstructor
public class EmailAuthController {
    private final EmailAuthService emailAuthService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmailAuthCode(@RequestParam("email") String email) {
        emailAuthService.sendEmailAuthCode(email); // 이메일 인증 코드 전송 로직을 실행
        return ResponseEntity.ok().body(Map.of("message", "이메일로 인증 코드를 전송했습니다."));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmailAuthCode(@RequestParam("email") String email, @RequestParam("code") String code) {
        // 이메일 인증 코드를 검증하는 요청을 처리합니다.
        emailAuthService.verifyEmailAuthCode(email, code); // 이메일 인증 코드 검증 로직을 실행
        return ResponseEntity.ok().body(Map.of("message", "성공적으로 이메일 인증이 완료되었습니다."));
    }
}

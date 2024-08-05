package org.likelion.zagabi.Domain.EmailAuth.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "이메일 인증 API", description = "이메일 인증 API 입니다.")
public class EmailAuthController {
    private final EmailAuthService emailAuthService;

    @Operation(method = "POST", summary = "이메일에 인증 코드 전송", description = "이메일에 인증 코드를 전송합니다.",
            parameters = {@Parameter(name = "email", description = "인증 코드를 보낼 이메일")})
    @PostMapping("/send")
    public ResponseEntity<?> sendEmailAuthCode(@RequestParam("email") String email) {
        emailAuthService.sendEmailAuthCode(email); // 이메일 인증 코드 전송 로직을 실행
        return ResponseEntity.ok().body(Map.of("message", "이메일로 인증 코드를 전송했습니다."));
    }

    @Operation(method = "POST", summary = "인증 코드 검증", description = "사용자가 작성한 인증 코드를 검증합니다.",
            parameters = {
                    @Parameter(name = "email", description = "검증 할 이메일"),
                    @Parameter(name = "code", description = "이메일로 받은 인증코드 입력")
            })
    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmailAuthCode(@RequestParam("email") String email, @RequestParam("code") String code) {
        // 이메일 인증 코드를 검증하는 요청을 처리합니다.
        emailAuthService.verifyEmailAuthCode(email, code); // 이메일 인증 코드 검증 로직을 실행
        return ResponseEntity.ok().body(Map.of("message", "성공적으로 이메일 인증이 완료되었습니다."));
    }
}

package org.likelion.zagabi.Domain.Email.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record EmailVerifyDto(
        @NotBlank(message = "[ERROR] 이메일 입력은 필수 입니다.")
        @Schema(description = "email", example = "test1234@naver.com")
        String email,

        @NotBlank(message = "[ERROR] 코드 입력은 필수 입니다.")
        String code
) {
}
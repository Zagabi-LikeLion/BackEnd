package org.likelion.zagabi.Domain.Account.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SecurityAnswerRequestDto(
        @NotBlank(message = "[ERROR] 보안질문 답변 입력은 필수입니다.")
        @Schema(description = "securityAnswer", example = "상명대 제1공학관 화장실")
        String securityAnswer
) {
}

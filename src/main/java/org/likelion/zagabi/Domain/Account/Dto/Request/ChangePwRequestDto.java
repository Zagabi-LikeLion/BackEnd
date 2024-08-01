package org.likelion.zagabi.Domain.Account.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangePwRequestDto(
        @NotBlank(message = "[ERROR] 비밀번호 입력은 필수 입니다.")
        @Size(min = 8, message = "[ERROR] 비밀번호는 최소 8자리 이이어야 합니다.")
        @Schema(description = "newPassword", example = "test1234!!!")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,64}$", message = "[ERROR] 비밀번호는 8자 이상, 64자 이하이며 특수문자 한 개를 포함해야 합니다.")
        String newPassword

//        @NotBlank(message = "[ERROR] 비밀번호 재확인 입력은 필수 입니다.")
//        @Schema(description = "passwordCheck", example = "test1234!!!")
//        String passwordCheck
) {
}
package org.likelion.zagabi.Domain.Account.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeNicknameRequestDto(
        @Size(max = 10, message = "닉네임은 최대 10자까지 입력 가능합니다.")
        @Schema(description = "name", example = "멋쟁이슴우")
        @NotBlank(message = "[ERROR] 닉네임 입력은 필수 입니다.")
        String newNickName) {
}

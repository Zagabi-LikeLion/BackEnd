package org.likelion.zagabi.Domain.Account.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.likelion.zagabi.Domain.Account.Entity.User;

public record UserSignUpRequestDto(
        @Size(max = 10, message = "닉네임은 최대 10자까지 입력 가능합니다.")
        @Schema(description = "name", example = "멋쟁이슴우")
        @NotBlank(message = "[ERROR] 닉네임 입력은 필수 입니다.")
        String nickName,

        @NotBlank(message = "[ERROR] 이메일 입력은 필수입니다.")
        @Schema(description = "email", example = "test1234@naver.com")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "[ERROR] 이메일 형식에 맞지 않습니다.")
        String email,

        @NotBlank(message = "[ERROR] 비밀번호 입력은 필수 입니다.")
        @Size(min = 8, message = "[ERROR] 비밀번호는 최소 8자리 이이어야 합니다.")
        @Schema(description = "password", example = "test1234!!")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,64}$", message = "[ERROR] 비밀번호는 8자 이상, 64자 이하이며 특수문자 한 개를 포함해야 합니다.")
        String password,

        String securityAnswer
) {
    public User toEntity(String encodedPw) {
        return User.builder()
                .email(email)
                .password(encodedPw)
                .nickName(nickName)
                .isAdmin(false) // 명시적으로 설정
                .securityAnswer(securityAnswer)
                .build();
    }
}

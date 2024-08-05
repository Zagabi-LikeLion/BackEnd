package org.likelion.zagabi.Domain.Account.Dto.Response;

import lombok.Builder;
import org.likelion.zagabi.Domain.Account.Entity.User;

@Builder
public record ForgotPwResponseDto(
        String securityQuestion,
        String email
) {
    public static ForgotPwResponseDto from(User user) {
        return ForgotPwResponseDto.builder()
                .securityQuestion(user.getSecurityQuestion().getQuestion())
                .email(user.getEmail())
                .build();
    }
}

package org.likelion.zagabi.Domain.Account.Dto.Request;

public record ForgotPwRequestDto(
        String email,
        String securityAnswer
) {
}

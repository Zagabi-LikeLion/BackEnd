package org.likelion.zagabi.Domain.Account.Dto.Request;

public record getTokenForPwRequestDto(
        String email,
        String securityAnswer
) {
}

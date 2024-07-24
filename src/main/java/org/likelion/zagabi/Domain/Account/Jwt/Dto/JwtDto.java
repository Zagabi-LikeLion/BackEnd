package org.likelion.zagabi.Domain.Account.Jwt.Dto;

public record JwtDto(
        String accessToken,
        String refreshToken
) {
}

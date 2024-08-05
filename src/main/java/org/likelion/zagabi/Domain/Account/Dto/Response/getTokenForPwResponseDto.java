package org.likelion.zagabi.Domain.Account.Dto.Response;

import lombok.Builder;
import org.likelion.zagabi.Domain.Account.Entity.User;

@Builder
public record getTokenForPwResponseDto(
        String accessToken
) {
    public static getTokenForPwResponseDto from(String accessToken) {
        return getTokenForPwResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }
}

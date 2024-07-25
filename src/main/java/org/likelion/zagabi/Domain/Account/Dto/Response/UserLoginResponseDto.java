package org.likelion.zagabi.Domain.Account.Dto.Response;

import lombok.Builder;
import org.likelion.zagabi.Domain.Account.Entity.User;

import java.time.LocalDateTime;
@Builder
public record UserLoginResponseDto(
        Long userId,
        LocalDateTime createdAt,
        String accessToken,
        String refreshToken
) {
    public static UserLoginResponseDto from(User user, String accessToken, String refreshToken) {
        return UserLoginResponseDto.builder()
                .userId(user.getId())
                .createdAt(LocalDateTime.now())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

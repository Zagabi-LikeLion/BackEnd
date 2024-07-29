package org.likelion.zagabi.Domain.Account.Dto.Response;

import lombok.Builder;
import org.likelion.zagabi.Domain.Account.Entity.User;

import java.time.LocalDateTime;
@Builder
public record UserSignUpResponseDto(
        Long id,
        String email,
        String nickName,
        LocalDateTime createdAt
) {

    public static UserSignUpResponseDto from(User user) {
        return UserSignUpResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
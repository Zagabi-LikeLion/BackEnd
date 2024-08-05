package org.likelion.zagabi.Domain.Account.Dto.Response;

import lombok.Builder;
import org.likelion.zagabi.Domain.Account.Entity.SecurityQuestion;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record SecurityQuestionResponseDto(
        Long questionId,
        String question
) {
    public static SecurityQuestionResponseDto from(SecurityQuestion securityQuestion) {
        return SecurityQuestionResponseDto.builder()
                .questionId(securityQuestion.getId())
                .question(securityQuestion.getQuestion())
                .build();
    }

    public static List<SecurityQuestionResponseDto> from(List<SecurityQuestion> securityQuestions) {
        return securityQuestions.stream()
                .map(SecurityQuestionResponseDto::from)
                .collect(Collectors.toList());
    }
}

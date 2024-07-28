package org.likelion.zagabi.Domain.Diary.Dto.Response;

import lombok.Builder;
import org.likelion.zagabi.Domain.Diary.Entity.DiaryQuestion;

@Builder
public record DiaryQuestionResponseDto(
        Long questionId,
        String question
) {
    public static DiaryQuestionResponseDto from(DiaryQuestion diaryQuestion) {
        return DiaryQuestionResponseDto.builder()
                .questionId(diaryQuestion.getId())
                .question(diaryQuestion.getQuestion())
                .build();
    }
}

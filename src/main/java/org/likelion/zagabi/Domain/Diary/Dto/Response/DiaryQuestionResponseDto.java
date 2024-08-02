package org.likelion.zagabi.Domain.Diary.Dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.likelion.zagabi.Domain.Diary.Entity.DiaryQuestion;

@Builder
public record DiaryQuestionResponseDto(
        @Schema(description = "제공된 질문의 ID", example = "1")
        Long questionId,

        @Schema(description = "제공된 질문의 내용", example = "오늘의 기분은 어땠나요?")
        String question
) {
    public static DiaryQuestionResponseDto from(DiaryQuestion diaryQuestion) {
        return DiaryQuestionResponseDto.builder()
                .questionId(diaryQuestion.getId())
                .question(diaryQuestion.getQuestion())
                .build();
    }
}

package org.likelion.zagabi.Domain.Diary.Dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.likelion.zagabi.Domain.Diary.Entity.Diary;
import org.likelion.zagabi.Domain.Diary.Entity.Enums.Mood;

import java.time.LocalDateTime;

@Builder
public record DiaryResponseDto(
        @Schema(description = "일기 id", example = "1")
        Long diaryId,

        @Schema(description = "일기 제목", example = "오늘의 일기")
        String title,

        @Schema(description = "일기 본문", example = "오늘 어쩌구 저쩌구")
        String mainText,

        @Schema(description = "기분", example = "HAPPY")
        Mood mood,

        @Schema(description = "느낀점", example = "도커공부를 해야겠다.")
        String impression,

        @Schema(description = "제공된 질문의 내용", example = "오늘의 기분은 어땠나요?")
        String questionContent,

        @Schema(description = "질문에 대한 대답", example = "행복했다.")
        String answer,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static DiaryResponseDto from(Diary diary) {
        return DiaryResponseDto.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .mainText(diary.getMainText())
                .mood(diary.getMood())
                .impression(diary.getImpression())
                .questionContent(diary.getQuestion().getQuestion())
                .answer(diary.getAnswer())
                .createdAt(diary.getCreatedAt())
                .updatedAt(diary.getUpdatedAt())
                .build();
    }
}

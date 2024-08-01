package org.likelion.zagabi.Domain.Diary.Dto.Response;

import lombok.Builder;
import org.likelion.zagabi.Domain.Diary.Entity.Diary;
import org.likelion.zagabi.Domain.Diary.Entity.Enums.Mood;

import java.time.LocalDateTime;

@Builder
public record DiaryResponseDto(
        String title,
        String mainText,
        Mood mood,
        String impression,
        String questionContent,
        String answer,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static DiaryResponseDto from(Diary diary) {
        return DiaryResponseDto.builder()
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

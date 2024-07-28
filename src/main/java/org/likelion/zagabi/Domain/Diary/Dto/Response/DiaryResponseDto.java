package org.likelion.zagabi.Domain.Diary.Dto.Response;

import lombok.Builder;
import org.likelion.zagabi.Domain.Diary.Entity.Diary;
import org.likelion.zagabi.Domain.Diary.Entity.Enums.Mood;

import java.time.LocalDateTime;

@Builder
public record DiaryResponseDto(
        Long diaryId,
        String title,
        String mainText,
        Mood mood,
        String impression,
        Long questionId,
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
                .questionId(diary.getQuestion().getId()) // questionId 설정
                .answer(diary.getAnswer())
                .createdAt(diary.getCreatedAt())
                .updatedAt(diary.getUpdatedAt())
                .build();
    }
}

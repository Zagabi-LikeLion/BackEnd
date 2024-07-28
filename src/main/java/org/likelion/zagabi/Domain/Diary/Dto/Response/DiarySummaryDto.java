package org.likelion.zagabi.Domain.Diary.Dto.Response;

import lombok.Builder;
import org.likelion.zagabi.Domain.Diary.Entity.Diary;
import org.likelion.zagabi.Domain.Diary.Entity.Enums.Mood;

@Builder
public record DiarySummaryDto(
        Long id,
        String title,
        Mood mood
) {
    public static DiarySummaryDto from(Diary diary) {
        return DiarySummaryDto.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .mood(diary.getMood())
                .build();
    }
}
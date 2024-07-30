package org.likelion.zagabi.Domain.Diary.Dto.Request;

import lombok.Builder;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Diary.Entity.Diary;
import org.likelion.zagabi.Domain.Diary.Entity.DiaryQuestion;
import org.likelion.zagabi.Domain.Diary.Entity.Enums.Mood;

@Builder
public record DiaryRequestDto (
        String title,
        String mainText,
        Mood mood,
        String impression,
        String answer,
        Long questionId
) {
    public Diary toEntity(User user, DiaryQuestion question) {
        return Diary.builder()
                .title(title)
                .mainText(mainText)
                .mood(mood)
                .impression(impression)
                .answer(answer)
                .question(question)
                .user(user)
                .build();
    }
}

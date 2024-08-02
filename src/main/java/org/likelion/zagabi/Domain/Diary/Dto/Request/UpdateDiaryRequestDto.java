package org.likelion.zagabi.Domain.Diary.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Diary.Entity.Diary;
import org.likelion.zagabi.Domain.Diary.Entity.Enums.Mood;

@Builder
public record UpdateDiaryRequestDto(
        @Schema(description = "일기 제목", example = "오늘의 일기")
        String title,

        @Schema(description = "일기 본문", example = "오늘 어쩌구 저쩌구")
        String mainText,

        @Schema(description = "기분", example = "HAPPY")
        Mood mood,

        @Schema(description = "느낀점", example = "도커공부를 해야겠다.")
        String impression,

        @Schema(description = "질문에 대한 대답", example = "행복했다.")
        String answer
) {
    public Diary toEntity(User user) {
        return Diary.builder()
                .title(title)
                .mainText(mainText)
                .mood(mood)
                .impression(impression)
                .answer(answer)
                .user(user)
                .build();
    }
}

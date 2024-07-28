package org.likelion.zagabi.Domain.Diary.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Diary.Dto.Request.UpdateDiaryRequestDto;
import org.likelion.zagabi.Domain.Diary.Entity.Enums.Mood;
import org.likelion.zagabi.Global.Common.BaseEntity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "diary")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private DiaryQuestion question;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String mainText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Mood mood;

    @Column
    private String impression;

    @Column
    private String answer;

    //일기 수정 시 호출 될 메서드
    public void update(UpdateDiaryRequestDto updateDiaryRequestDto) {
        this.title = updateDiaryRequestDto.title();
        this.mainText = updateDiaryRequestDto.mainText();
        this.mood = updateDiaryRequestDto.mood();
        this.impression = updateDiaryRequestDto.impression();
        this.answer = updateDiaryRequestDto.answer();
    }

    //질문 설정 메서드 추가
    public void setQuestion(DiaryQuestion question) {
        this.question = question;
    }
}

package org.likelion.zagabi.Global.Common;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Account.Entity.SecurityQuestion;
import org.likelion.zagabi.Domain.Account.Repository.SecurityQuestionRepository;
import org.likelion.zagabi.Domain.Diary.Entity.DiaryQuestion;
import org.likelion.zagabi.Domain.Diary.Repository.DiaryQuestionRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final SecurityQuestionRepository securityQuestionRepository;
    private final DiaryQuestionRepository diaryQuestionRepository;

    @PostConstruct
    public void loadSecurityQuestions() {
        if (securityQuestionRepository.count() == 0) {
            List<String> questions = Arrays.asList(
                    "어린 시절 가장 친한 친구의 이름은 무엇인가요?",
                    "첫 번째 반려동물의 이름은 무엇인가요?",
                    "부모님의 만난 장소는 어디인가요?",
                    "어린 시절 살던 거리의 이름은 무엇인가요?",
                    "첫 번째 학교의 이름은 무엇인가요?",
                    "결혼식 날짜는 언제인가요?",
                    "첫 번째 자동차의 모델은 무엇인가요?",
                    "가장 좋아하는 선생님의 이름은 무엇인가요?",
                    "생일은 언제인가요?",
                    "첫 번째 직장의 이름은 무엇인가요?"
            );

            for (String question : questions) {
                SecurityQuestion securityQuestion = SecurityQuestion.builder()
                        .question(question)
                        .build();
                securityQuestionRepository.save(securityQuestion);
            }
        }
    }

    @PostConstruct
    public void loadDiaryQuestions() {
        if (diaryQuestionRepository.count() == 0) {
            List<String> questions = Arrays.asList(
                    "오늘의 기분은 어땠나요?",
                    "오늘 가장 기억에 남는 순간은 무엇인가요?",
                    "오늘 배운 새로운 것은 무엇인가요?",
                    "오늘 어떤 어려움이 있었나요?",
                    "오늘 가장 감사한 일은 무엇인가요?",
                    "오늘 누구와 시간을 보냈나요?",
                    "오늘 어떤 책을 읽었나요?",
                    "오늘 가장 웃긴 순간은 무엇인가요?",
                    "오늘 어떤 운동을 했나요?",
                    "오늘 가장 맛있었던 음식은 무엇인가요?",
                    "오늘 하루를 세 단어로 표현한다면?",
                    "오늘 어떤 음악을 들었나요?",
                    "오늘 어떤 영화를 봤나요?",
                    "오늘 가장 큰 성취는 무엇인가요?",
                    "오늘 누구에게 도움을 주었나요?",
                    "오늘 무엇을 놓쳤나요?",
                    "오늘 가장 행복했던 순간은 무엇인가요?",
                    "오늘 어떤 실수를 했나요?",
                    "오늘 어떤 자연 현상을 봤나요?",
                    "오늘 어떤 꿈을 꿨나요?",
                    "오늘 어떤 일을 계획했나요?",
                    "오늘 어떤 색깔이 떠오르나요?",
                    "오늘 무엇을 위해 기도했나요?",
                    "오늘 무엇을 만들었나요?",
                    "오늘 무엇을 사고 싶었나요?",
                    "오늘 무엇을 읽었나요?",
                    "오늘 어떤 향기를 맡았나요?",
                    "오늘 가장 긴 시간 동안 한 일은 무엇인가요?",
                    "오늘 무엇을 보았나요?",
                    "오늘 누구를 만나고 싶었나요?",
                    "오늘 어떤 날씨였나요?"

            );

            for (String question : questions) {
                DiaryQuestion diaryQuestion = DiaryQuestion.builder()
                        .question(question)
                        .build();
                diaryQuestionRepository.save(diaryQuestion);
            }
        }
    }
}

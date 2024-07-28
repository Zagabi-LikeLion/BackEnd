package org.likelion.zagabi.Domain.Diary.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Diary.Dto.Response.DiaryQuestionResponseDto;
import org.likelion.zagabi.Domain.Diary.Dto.Response.DiaryResponseDto;
import org.likelion.zagabi.Domain.Diary.Dto.Response.DiarySummaryDto;
import org.likelion.zagabi.Domain.Diary.Entity.Diary;
import org.likelion.zagabi.Domain.Diary.Entity.DiaryQuestion;
import org.likelion.zagabi.Domain.Diary.Repository.DiaryQuestionRepository;
import org.likelion.zagabi.Domain.Diary.Repository.DiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryQueryService {

    private final DiaryRepository diaryRepository;
    /*private final UserRepository userRepository;*/
    private final DiaryQuestionRepository diaryQuestionRepository;

    //일기 정보 조회
    public DiaryResponseDto getDiary(/*String email, */Long id) {

        //id로 일기 조회
        Diary diary = diaryRepository.findById(id).orElseThrow();
        /*if (!diary.getUser().getEmail().equals(email)) {
            throw new RuntimeException("권한이 없습니다.");
        }*/

        //조회 된 일기 정보를 DTO로 변환 후 반환
        return DiaryResponseDto.from(diary);
    }

    /*// 요청한 날의 일기 요약 조회
    public DiarySummaryDto getDiaryByDay(LocalDate date, String email) {
        // 이메일로 유저 정보 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        // 특정 날짜에 작성된 일기 조회
        Diary diary = diaryRepository.findByDateAndUserEmail(date, email)
                .orElseThrow(() -> new RuntimeException("해당 날짜에 작성된 일기가 없습니다"));

        return DiarySummaryDto.from(diary);
    }*/

    // 일기에 제공할 질문 가져오기
    public DiaryQuestionResponseDto getDiaryQuestion() {
        DiaryQuestion question = diaryQuestionRepository.findRandomQuestion()
                .orElseThrow(() -> new RuntimeException("DB에 질문이 존재하지 않음"));
        return DiaryQuestionResponseDto.from(question);
    }

}

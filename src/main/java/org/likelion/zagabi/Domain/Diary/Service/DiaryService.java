package org.likelion.zagabi.Domain.Diary.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Diary.Dto.Request.DiaryRequestDto;
import org.likelion.zagabi.Domain.Diary.Dto.Request.UpdateDiaryRequestDto;
import org.likelion.zagabi.Domain.Diary.Dto.Response.DiaryResponseDto;
import org.likelion.zagabi.Domain.Diary.Entity.Diary;
import org.likelion.zagabi.Domain.Diary.Entity.DiaryQuestion;
import org.likelion.zagabi.Domain.Diary.Repository.DiaryQuestionRepository;
import org.likelion.zagabi.Domain.Diary.Repository.DiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    /*private final UserRepository userRepository;*/
    private final DiaryQuestionRepository diaryQuestionRepository;

    //일기 저장
    public DiaryResponseDto save(/*String email, */DiaryRequestDto diaryRequestDto) {

        /*//이메일로 유저 정보 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));*/

        //질문 ID로 질문 조회
        DiaryQuestion question = diaryQuestionRepository.findById(diaryRequestDto.questionId())
                .orElseThrow(() -> new RuntimeException("질문을 찾을 수 없습니다"));

        //파라미터로 받은 DTO를 Entity로 변환 후 Repository에 저장
        Diary diary = diaryRequestDto.toEntity(/*user, */question); // 질문 포함

        diaryRepository.save(diary);

        //diary를 DTO로 변환 후 컨트롤러단으로 반환
        return DiaryResponseDto.from(diary);
    }

    //일기 수정
    public DiaryResponseDto updateDiary(/*String email, */Long id, UpdateDiaryRequestDto updateDiaryRequestDto) {
        //id로 일기 조회
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일기를 찾을 수 없습니다"));

        /*//유저 이메일 확인
        if (!diary.getUser().getEmail().equals(email)) {
            throw new RuntimeException("해당 일기에 접근할 권한이 없습니다.");
        }
*/
        //파라미터로 받은 DTO를 이용해 일기 업데이트
        diary.update(updateDiaryRequestDto);

        return DiaryResponseDto.from(diary);
    }

    //일기 삭제
    public void deleteDiary(/*String email, */Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일기를 찾을 수 없습니다"));

        /*//유저 이메일 확인
        if (!diary.getUser().getEmail().equals(email)) {
            throw new RuntimeException("해당 일기에 접근할 권한이 없습니다.");
        }*/

        diaryRepository.delete(diary);
    }
}

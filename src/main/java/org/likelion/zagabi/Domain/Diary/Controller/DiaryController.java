package org.likelion.zagabi.Domain.Diary.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Diary.Dto.Request.DiaryRequestDto;
import org.likelion.zagabi.Domain.Diary.Dto.Request.UpdateDiaryRequestDto;
import org.likelion.zagabi.Domain.Diary.Dto.Response.DiaryQuestionResponseDto;
import org.likelion.zagabi.Domain.Diary.Dto.Response.DiaryResponseDto;
import org.likelion.zagabi.Domain.Diary.Dto.Response.DiarySummaryDto;
import org.likelion.zagabi.Domain.Diary.Service.DiaryQueryService;
import org.likelion.zagabi.Domain.Diary.Service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;
    private final DiaryQueryService diaryQueryService;

    //일기 작성
    @PostMapping
    public ResponseEntity<?> createDiary(/*@AuthenticationPrincipal CustomUserDetails userDetails,*/
                                         @RequestBody DiaryRequestDto diaryRequestDto) {
        DiaryResponseDto diaryResponseDto = diaryService.save(/*userDetails.getUsername(), */diaryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(diaryResponseDto);
    }

    //일기 조회
    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryResponseDto> getDiary(/*@AuthenticationPrincipal CustomUserDetails userDetails,*/
                                                     @PathVariable Long diaryId) {
        DiaryResponseDto diaryResponseDto = diaryQueryService.getDiary(/*userDetails.getUsername(), */diaryId);
        return ResponseEntity.ok(diaryResponseDto);
    }


    /*// 요청한 날의 일기 조회
    @GetMapping("/by-day")
    public ResponseEntity<DiarySummaryDto> getDiaryByDay(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @RequestParam LocalDate date) {
        DiarySummaryDto diary = diaryQueryService.getDiaryByDay(date, userDetails.getUsername());
        return ResponseEntity.ok(diary);
    }*/

    //일기에 제공할 질문 조회
    @GetMapping("/question")
    public ResponseEntity<DiaryQuestionResponseDto> getDiaryQuestion() {
        DiaryQuestionResponseDto diaryQuestion = diaryQueryService.getDiaryQuestion();
        return ResponseEntity.ok(diaryQuestion);
    }

    //일기 수정
    @PatchMapping("/{diaryId}")
    public ResponseEntity<DiaryResponseDto> updateDiary(/*@AuthenticationPrincipal CustomUserDetails userDetails,*/
                                                        @PathVariable Long diaryId,
                                                        @RequestBody UpdateDiaryRequestDto updateDiaryRequestDto) {
        DiaryResponseDto diaryResponseDto = diaryService.updateDiary(/*userDetails.getUsername(), */diaryId, updateDiaryRequestDto);
        return ResponseEntity.ok(diaryResponseDto);
    }

    //일기 삭제
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiary(/*@AuthenticationPrincipal CustomUserDetails userDetails,*/
                                         @PathVariable Long diaryId) {
        diaryService.deleteDiary(/*userDetails.getUsername(), */diaryId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "성공적으로 일기가 삭제되었습니다."));
    }
}

package org.likelion.zagabi.Domain.Diary.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Account.Jwt.UserDetails.CustomUserDetails;
import org.likelion.zagabi.Domain.Diary.Dto.Request.DiaryRequestDto;
import org.likelion.zagabi.Domain.Diary.Dto.Request.UpdateDiaryRequestDto;
import org.likelion.zagabi.Domain.Diary.Dto.Response.DiaryQuestionResponseDto;
import org.likelion.zagabi.Domain.Diary.Dto.Response.DiaryResponseDto;
import org.likelion.zagabi.Domain.Diary.Service.DiaryQueryService;
import org.likelion.zagabi.Domain.Diary.Service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
@Tag(name = "일기 API", description = "일기 관련 API 입니다.")
public class DiaryController {

    private final DiaryService diaryService;
    private final DiaryQueryService diaryQueryService;

    //일기 작성
    @Operation(method = "POST", summary = "일기 작성", description = "일기를 작성 합니다. <br/>header에 accessToken을 담고, <br/>body에는 일기 내용을 담아서 전송합니다.<br/>mood = {HAPPY,SAD,ANGRY,SENSITIVE,SHY,ANXIOUS,BORED}")
    @PostMapping
    public ResponseEntity<DiaryResponseDto> createDiary(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @RequestBody DiaryRequestDto diaryRequestDto) {
        DiaryResponseDto diaryResponseDto = diaryService.save(userDetails.getUsername(), diaryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(diaryResponseDto);
    }

    //일기 조회
    @Operation(method = "POST", summary = "일기 조회", description = "일기를 조회합니다.<br/> header에 accessToken을 담고, <br/>URL parameter에는 조회하고 싶은 일기의 ID를 담아서 전송합니다.")
    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryResponseDto> getDiary(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                     @PathVariable Long diaryId) {
        DiaryResponseDto diaryResponseDto = diaryQueryService.getDiary(userDetails.getUsername(), diaryId);
        return ResponseEntity.ok(diaryResponseDto);
    }


    // 요청한 날의 일기 조회
    @GetMapping("/by-day")
    @Operation(method = "GET", summary = "날짜로 일기 조회", description = "날짜로 일기를 조회합니다.<br/> header에 accessToken을 담고,<br/> parameter에는 조회하고 싶은 날짜를 YYYY-MM-DD 형태로 담아서 전송합니다.",
            parameters = {@Parameter(name = "date", description = "조회할 날짜, YYYY-MM-DD 형식")})
    public ResponseEntity<DiaryResponseDto> getDiaryByDay(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @RequestParam LocalDate date) {
        DiaryResponseDto diary = diaryQueryService.getDiaryByDay(date, userDetails.getUsername());
        return ResponseEntity.ok(diary);
    }

    //일기에 제공할 질문 조회
    @Operation(method = "GET", summary = "일기에 들어갈 질문 제공", description = "일기에 들어갈 질문을 제공합니다.<br/> header에 accessToken을 담아서 전송합니다.")
    @GetMapping("/question")
    public ResponseEntity<DiaryQuestionResponseDto> getDiaryQuestion() {
        DiaryQuestionResponseDto diaryQuestion = diaryQueryService.getDiaryQuestion();
        return ResponseEntity.ok(diaryQuestion);
    }

    //일기 수정
    @Operation(method = "PATCH", summary = "일기 수정", description = "일기의 내용을 수정합니다.<br/> header에 accessToken을,<br/> URL parameter에는 조회하고 싶은 일기의 ID를,<br/> body에는 수정하려는 일기의 내용을 담아서 전송합니다.<br/>mood = {HAPPY,SAD,ANGRY,SENSITIVE,SHY,ANXIOUS,BORED}")
    @PatchMapping("/{diaryId}")
    public ResponseEntity<DiaryResponseDto> updateDiary(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @PathVariable Long diaryId,
                                                        @RequestBody UpdateDiaryRequestDto updateDiaryRequestDto) {
        DiaryResponseDto diaryResponseDto = diaryService.updateDiary(userDetails.getUsername(), diaryId, updateDiaryRequestDto);
        return ResponseEntity.ok(diaryResponseDto);
    }

    //일기 삭제
    @Operation(method = "DELETE", summary = "일기 삭제", description = "일기를 삭제합니다.<br/> header에 accessToken을 담고,<br/> URL parameter에는 삭제하고 싶은 일기의 ID를 담아서 전송합니다.")
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiary(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @PathVariable Long diaryId) {
        diaryService.deleteDiary(userDetails.getUsername(), diaryId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "성공적으로 일기가 삭제되었습니다."));
    }
}

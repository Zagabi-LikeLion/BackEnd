package org.likelion.zagabi.Domain.ValueChangeLog.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Value.Dto.request.CreateValueRequestDto;
import org.likelion.zagabi.Domain.Value.Dto.request.UpdateValueRequestDto;
import org.likelion.zagabi.Domain.Value.Dto.response.ValueResponseDto;
import org.likelion.zagabi.Domain.ValueChangeLog.Dto.request.CreateValueChangeLogDto;
import org.likelion.zagabi.Domain.ValueChangeLog.Dto.response.ValueChangeLogResponseDto;
import org.likelion.zagabi.Domain.ValueChangeLog.Service.ValueChangeLogQueryService;
import org.likelion.zagabi.Domain.ValueChangeLog.Service.ValueChangeLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/valuechangelog")
@Tag(name = "가치관 변화 기록 API", description = "가치관 변화 기록 관련 API 입니다.")
public class ValueChangeLogController {
    private final ValueChangeLogService valueChangeLogService;
    private final ValueChangeLogQueryService valueChangeLogQueryService;
    @Operation(method = "POST", summary = "가치관 변화 기록 생성", description = "가치관 변화 기록 생성을 합니다. header에 accessToken과 body에는 change_type(카테고리 추가 : add_category, 가치관 추가 : add_value, 순위 변동 : change_rank, 가치관 삭제 : delete_value ), change_reason(순위 변동일 때만 보내고 나머지 경우에는 null로 보내도 됌), category_id(change_type이 카테고리 추가, 가치관 추가, 순위변동, 가치관 삭제일 경우 필요, 나머지인 경우 null로 보내도 됌), value_id(change_type이 가치관 추가, 순위 변동, 가치관 삭제일 경우 필요, 나머지인 경우 null로 보내도 됌)를 담아서 전송합니다.")
    @PostMapping("")
    public ResponseEntity<?> createValueChangeLog(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateValueChangeLogDto createValueChangeLogDto){
        ValueChangeLogResponseDto valueChangeLogResponseDto = valueChangeLogService.createValueChangeLog(userDetails.getUsername(), createValueChangeLogDto);
        return new ResponseEntity<>(valueChangeLogResponseDto, HttpStatus.CREATED);
    }

    @Operation(method = "GET", summary = "가치관 변화 기록 조회", description = "가치관 변화 기록 조회를 합니다. header에 accessToken과 url parameter에 가치관 변화 기록 ID를 담아서 전송합니다.")
    @GetMapping("/{valueChangeLogId}")
    public ResponseEntity<?> getValueChangeLog(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long valueChangeLogId){
        return ResponseEntity.ok(valueChangeLogQueryService.getValueChangeLog(valueChangeLogId));
    }

    @Operation(method = "GET", summary = "가치관 변화 기록 전체 조회", description = "가치관 변화 기록 전체 조회를 합니다. header에 accessToken을 담아서 전송합니다.")
    @GetMapping("/getAll")
    public ResponseEntity<List<ValueChangeLogResponseDto>> getAllValue(@AuthenticationPrincipal UserDetails userDetails) {
        List<ValueChangeLogResponseDto> valueChangeLogResponseDtos = valueChangeLogQueryService.getAllValueChangeLog(userDetails.getUsername());
        return ResponseEntity.ok(valueChangeLogResponseDtos);
    }

}

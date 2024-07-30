package org.likelion.zagabi.Domain.ValueChangeLog.Controller;

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
public class ValueChangeLogController {
    private final ValueChangeLogService valueChangeLogService;
    private final ValueChangeLogQueryService valueChangeLogQueryService;

    @PostMapping("")
    public ResponseEntity<?> createValueChangeLog(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateValueChangeLogDto createValueChangeLogDto){
        ValueChangeLogResponseDto valueChangeLogResponseDto = valueChangeLogService.createValueChangeLog(userDetails.getUsername(), createValueChangeLogDto);
        return new ResponseEntity<>(valueChangeLogResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{valueChangeLogId}")
    public ResponseEntity<?> getValueChangeLog(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long valueChangeLogId){
        return ResponseEntity.ok(valueChangeLogQueryService.getValueChangeLog(valueChangeLogId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ValueChangeLogResponseDto>> getAllValue(@AuthenticationPrincipal UserDetails userDetails) {
        List<ValueChangeLogResponseDto> valueChangeLogResponseDtos = valueChangeLogQueryService.getAllValueChangeLog(userDetails.getUsername());
        return ResponseEntity.ok(valueChangeLogResponseDtos);
    }

}

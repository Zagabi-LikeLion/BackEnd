package org.likelion.zagabi.Domain.ValueTrashCan.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.likelion.zagabi.Domain.ValueTrashCan.Dto.request.CreateValueTrashCanRequestDto;
import org.likelion.zagabi.Domain.ValueTrashCan.Dto.response.ValueTrashCanResponseDto;
import org.likelion.zagabi.Domain.ValueTrashCan.Service.ValueTrashCanQueryService;
import org.likelion.zagabi.Domain.ValueTrashCan.Service.ValueTrashCanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/valuetrashcan")
@Tag(name = "가치관 휴지통 API", description = "가치관 휴지통 API 입니다.")
public class ValueTrashCanController {
    private final ValueTrashCanService valueTrashCanService;
    private final ValueTrashCanQueryService valueTrashCanQueryService;

    @Operation(method = "POST", summary = "가치관 휴지통 생성", description = "가치관 휴지통 생성을 합니다. header에 accessToken과 body에는 가치관 ID를 담아서 전송합니다.")
    @PostMapping("")
    public ResponseEntity<?> createValueTrashCan(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateValueTrashCanRequestDto createValueTrashCanRequestDto){
        ValueTrashCanResponseDto valueTrashCanResponseDto = valueTrashCanService.createValueTrashCan(userDetails.getUsername(), createValueTrashCanRequestDto);
        return new ResponseEntity<>(valueTrashCanResponseDto, HttpStatus.CREATED);
    }

    @Operation(method = "GET", summary = "가치관 휴지통 조회", description = "가치관 휴지통 조회를 합니다. header에 accessToken과 url parameter에는 가치관 변화 기록 ID를 담아서 전송합니다.")
    @GetMapping("/{valueTrashCanId}")
    public ResponseEntity<?> getValueTrashCan(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long valueTrashCanId){
        return ResponseEntity.ok(valueTrashCanQueryService.getValueTrashCan(valueTrashCanId));
    }

    @Operation(method = "GET", summary = "가치관 휴지통 전체 조회", description = "가치관 휴지통 전체 조회를 합니다. header에 accessToken을 담아서 전송합니다.")
    @GetMapping("/getAll")
    public ResponseEntity<List<ValueTrashCanResponseDto>> getAllValueTrashCan(@AuthenticationPrincipal UserDetails userDetails) {
        List<ValueTrashCanResponseDto> valueTrashCanResponseDtos = valueTrashCanQueryService.getAllValueTrashCan(userDetails.getUsername());
        return ResponseEntity.ok(valueTrashCanResponseDtos);
    }
}

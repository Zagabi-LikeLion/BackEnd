package org.likelion.zagabi.Domain.Value.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Value.Service.ValueService;
import org.likelion.zagabi.Domain.Value.Dto.request.CreateValueRequestDto;
import org.likelion.zagabi.Domain.Value.Dto.request.UpdateValueRequestDto;
import org.likelion.zagabi.Domain.Value.Dto.response.ValueResponseDto;
import org.likelion.zagabi.Domain.Value.Service.ValueQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/value")
@Tag(name = "가치관 API", description = "가치관 관련 API 입니다.")
public class ValueController {

    private final ValueService valueService;
    private final ValueQueryService valueQueryService;

    @Operation(method = "POST", summary = "가치관 생성", description = "가치관 생성을 합니다. header에 accessToken과 body에는 카테고리 ID와 가치관 내용을 담아서 전송합니다.")
    @PostMapping("")
    public ResponseEntity<?> createValue(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateValueRequestDto createValueRequestDto){
        ValueResponseDto valueResponseDto = valueService.createValue(createValueRequestDto);
        return new ResponseEntity<>(valueResponseDto, HttpStatus.CREATED);
    }

    @Operation(method = "GET", summary = "가치관 조회", description = "가치관 조회를 합니다. header에 accessToken과 url parameter에 가치관 ID를 담아서 전송합니다.")
    @GetMapping("/{valueId}")
    public ResponseEntity<?> getValue(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long valueId){
        return ResponseEntity.ok(valueQueryService.getValue(valueId));
    }

    @Operation(method = "GET", summary = "랭킹 변화", description = "가치관의 랭킹을 변화합니다. header에 accessToken과 body에 가치관 ID와 바꾸고 싶은 랭킹을 담아서 전송합니다.")
    @PatchMapping("")
    public ResponseEntity<?> updateRanking(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateValueRequestDto updateValueRequestDto){
        return ResponseEntity.ok(valueService.updateValueRanking(updateValueRequestDto));
    }


    @Operation(method = "DELETE", summary = "가치관 삭제", description = "가치관을 삭제합니다. header에 accessToken과 url parameter에 가치관 ID를 담아서 전송합니다.")
    @DeleteMapping("/{valueId}")
    public ResponseEntity<Void> deleteValue(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long valueId){
        valueService.deleteValue(valueId);
        return ResponseEntity.noContent().build();
    }


    @Operation(method = "GET", summary = "가치관 전체 조회", description = "가치관을 전체 조회합니다. header에 accessToken과 url parameter에 카테고리 ID를 담아서 전송합니다.")
    @GetMapping("/getAll/{categoryId}")
    public ResponseEntity<List<ValueResponseDto>> getAllValue(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long categoryId) {
        List<ValueResponseDto> valueResponseDtos = valueQueryService.getAllValue(categoryId);
        return ResponseEntity.ok(valueResponseDtos);
    }





    }

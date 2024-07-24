package org.likelion.zagabi.Domain.Value.Controller;

import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Value.Service.ValueService;
import org.likelion.zagabi.Domain.Value.Dto.request.CreateValueRequestDto;
import org.likelion.zagabi.Domain.Value.Dto.request.UpdateValueRequestDto;
import org.likelion.zagabi.Domain.Value.Dto.response.ValueResponseDto;
import org.likelion.zagabi.Domain.Value.Service.ValueQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/value")
public class ValueController {

    private final ValueService valueService;
    private final ValueQueryService valueQueryService;

    @PostMapping("")
    public ResponseEntity<?> createValue(@RequestBody CreateValueRequestDto createValueRequestDto){
        ValueResponseDto valueResponseDto = valueService.createValue(createValueRequestDto);
        return new ResponseEntity<>(valueResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{valueId}")
    public ResponseEntity<?> getValue(@PathVariable Long valueId){
        return ResponseEntity.ok(valueQueryService.getValue(valueId));
    }

    @PatchMapping("")
    public ResponseEntity<?> updateRanking(@RequestBody UpdateValueRequestDto updateValueRequestDto){
        return ResponseEntity.ok(valueService.updateValueRanking(updateValueRequestDto));
    }

    @DeleteMapping("/{valueId}")
    public ResponseEntity<Void> deleteValue(@PathVariable Long valueId){
        valueService.deleteValue(valueId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll/{categoryId}")
    public ResponseEntity<List<ValueResponseDto>> getAllValue(@PathVariable Long categoryId) {
        List<ValueResponseDto> valueResponseDtos = valueQueryService.getAllValue(categoryId);
        return ResponseEntity.ok(valueResponseDtos);
    }





    }

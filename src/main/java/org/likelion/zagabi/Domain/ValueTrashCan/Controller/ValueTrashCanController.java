package org.likelion.zagabi.Domain.ValueTrashCan.Controller;

import lombok.RequiredArgsConstructor;

import org.likelion.zagabi.Domain.ValueTrashCan.Dto.request.CreateValueTrashCanRequestDto;
import org.likelion.zagabi.Domain.ValueTrashCan.Dto.response.ValueTrashCanResponseDto;
import org.likelion.zagabi.Domain.ValueTrashCan.Service.ValueTrashCanQueryService;
import org.likelion.zagabi.Domain.ValueTrashCan.Service.ValueTrashCanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/valuetrashcan")
public class ValueTrashCanController {
    private final ValueTrashCanService valueTrashCanService;
    private final ValueTrashCanQueryService valueTrashCanQueryService;

    @PostMapping("")
    public ResponseEntity<?> createValueTrashCan(@RequestBody CreateValueTrashCanRequestDto createValueTrashCanRequestDto){
        ValueTrashCanResponseDto valueTrashCanResponseDto = valueTrashCanService.createValueTrashCan(createValueTrashCanRequestDto);
        return new ResponseEntity<>(valueTrashCanResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{valueTrashCanId}")
    public ResponseEntity<?> getValueTrashCan(@PathVariable Long valueTrashCanId){
        return ResponseEntity.ok(valueTrashCanQueryService.getValueTrashCan(valueTrashCanId));
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<ValueTrashCanResponseDto>> getAllValueTrashCan(@PathVariable Long userId) {
        List<ValueTrashCanResponseDto> valueTrashCanResponseDtos = valueTrashCanQueryService.getAllValueTrashCan(userId);
        return ResponseEntity.ok(valueTrashCanResponseDtos);
    }
}

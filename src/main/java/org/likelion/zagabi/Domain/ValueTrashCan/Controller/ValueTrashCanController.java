package org.likelion.zagabi.Domain.ValueTrashCan.Controller;

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
public class ValueTrashCanController {
    private final ValueTrashCanService valueTrashCanService;
    private final ValueTrashCanQueryService valueTrashCanQueryService;

    @PostMapping("")
    public ResponseEntity<?> createValueTrashCan(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateValueTrashCanRequestDto createValueTrashCanRequestDto){
        ValueTrashCanResponseDto valueTrashCanResponseDto = valueTrashCanService.createValueTrashCan(userDetails.getUsername(), createValueTrashCanRequestDto);
        return new ResponseEntity<>(valueTrashCanResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{valueTrashCanId}")
    public ResponseEntity<?> getValueTrashCan(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long valueTrashCanId){
        return ResponseEntity.ok(valueTrashCanQueryService.getValueTrashCan(valueTrashCanId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ValueTrashCanResponseDto>> getAllValueTrashCan(@AuthenticationPrincipal UserDetails userDetails) {
        List<ValueTrashCanResponseDto> valueTrashCanResponseDtos = valueTrashCanQueryService.getAllValueTrashCan(userDetails.getUsername());
        return ResponseEntity.ok(valueTrashCanResponseDtos);
    }
}

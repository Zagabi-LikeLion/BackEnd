package org.likelion.zagabi.Domain.Category.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Account.Jwt.UserDetails.CustomUserDetails;
import org.likelion.zagabi.Domain.Category.Dto.Request.ValueCategoryRequestDto;
import org.likelion.zagabi.Domain.Category.Dto.Response.ValueCategoryResponseDto;
import org.likelion.zagabi.Domain.Category.Service.ValueCategoryQueryService;
import org.likelion.zagabi.Domain.Category.Service.ValueCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/value/category")
public class ValueCategoryController {

    private final ValueCategoryService valueCategoryService;
    private final ValueCategoryQueryService valueCategoryQueryService;

    //카테고리 생성
    @PostMapping
    public ResponseEntity<ValueCategoryResponseDto> createCategory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                   @RequestBody ValueCategoryRequestDto request) {
        ValueCategoryResponseDto responseDto = valueCategoryService.save(userDetails.getUsername(), request.categoryName());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //모든 카테고리 조회
    @GetMapping
    public ResponseEntity<List<ValueCategoryResponseDto>> getAllCategories(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ValueCategoryResponseDto> response = valueCategoryQueryService.getAllValueCategories(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    //특정 카테고리 조회
    @GetMapping("/{categoryId}")
    public ResponseEntity<ValueCategoryResponseDto> getCategory(@PathVariable Long categoryId) {
        ValueCategoryResponseDto response = valueCategoryQueryService.getValueCategory(categoryId);
        return ResponseEntity.ok(response);
    }

    //카테고리 이름 변경
    @PatchMapping("/{categoryId}")
    public ResponseEntity<ValueCategoryResponseDto> updateCategoryName(@PathVariable Long categoryId,
                                                                       @RequestBody ValueCategoryRequestDto request) {
        ValueCategoryResponseDto response = valueCategoryService.updateCategoryName(categoryId, request.categoryName());
        return ResponseEntity.ok(response);
    }

    //카테고리 삭제(삭제할 때 해당 카테고리의 모든 가치관을 삭제하고 삭제해야함)
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        valueCategoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().body(Map.of("message", "성공적으로 카테고리가 삭제되었습니다."));
    }
}


package org.likelion.zagabi.Domain.Category.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "가치관 카테고리 API", description = "가치관 카테고리 관련 API 입니다.")
public class ValueCategoryController {

    private final ValueCategoryService valueCategoryService;
    private final ValueCategoryQueryService valueCategoryQueryService;

    //카테고리 생성
    @Operation(method = "POST", summary = "가치관 카테고리 생성", description = "가치관 카테고리를 생성 합니다.<br/> header에 accessToken을 담고,<br/> body에는 생성할 카테고리의 이름을 담아서 전송합니다.")
    @PostMapping
    public ResponseEntity<ValueCategoryResponseDto> createCategory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                   @RequestBody ValueCategoryRequestDto request) {
        ValueCategoryResponseDto responseDto = valueCategoryService.save(userDetails.getUsername(), request.categoryName());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //모든 카테고리 조회
    @Operation(method = "GET", summary = "모든 카테고리 조회", description = "모든 카테고리를 조회합니다.<br/> header에 accessToken을 담아서 전송합니다.")
    @GetMapping
    public ResponseEntity<List<ValueCategoryResponseDto>> getAllCategories(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ValueCategoryResponseDto> response = valueCategoryQueryService.getAllValueCategories(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    //특정 카테고리 조회
    @Operation(method = "GET", summary = "특정 카테고리 조회", description = "특정 카테고리를 조회합니다.<br/> header에 accessToken을 담고,<br/> URL parameter에는 조회하고 싶은 카테고리의 ID를 담아서 전송합니다.")
    @GetMapping("/{categoryId}")
    public ResponseEntity<ValueCategoryResponseDto> getCategory(@PathVariable Long categoryId) {
        ValueCategoryResponseDto response = valueCategoryQueryService.getValueCategory(categoryId);
        return ResponseEntity.ok(response);
    }

    //카테고리 이름 변경
    @Operation(method = "PATCH", summary = "카테고리 이름 변경", description = "카테고리의 이름을 수정합니다.<br/> header에 accessToken을,<br/> URL parameter에는 변경하고 싶은 카테고리의 ID를,<br/> body에는 수정하려는 카테고리의 이름을 담아서 전송합니다.")
    @PatchMapping("/{categoryId}")
    public ResponseEntity<ValueCategoryResponseDto> updateCategoryName(@PathVariable Long categoryId,
                                                                       @RequestBody ValueCategoryRequestDto request) {
        ValueCategoryResponseDto response = valueCategoryService.updateCategoryName(categoryId, request.categoryName());
        return ResponseEntity.ok(response);
    }

    //카테고리 삭제
    @Operation(method = "DELETE", summary = "카테고리 삭제", description = "카테고리를 삭제합니다.<br/> header에 accessToken을 담고,<br/> URL parameter에는 삭제하고 싶은 카테고리의 ID를 담아서 전송합니다.")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        valueCategoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().body(Map.of("message", "성공적으로 카테고리가 삭제되었습니다."));
    }
}


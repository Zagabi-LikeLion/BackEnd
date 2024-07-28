package org.likelion.zagabi.Domain.Category.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Category.Dto.Response.ValueCategoryResponseDto;
import org.likelion.zagabi.Domain.Category.Entity.ValueCategory;
import org.likelion.zagabi.Domain.Category.Repository.ValueCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ValueCategoryQueryService {

    private final ValueCategoryRepository valueCategoryRepository;

    /*//모든 카테고리 조회
    public List<ValueCategoryResponseDto> getAllValueCategories(String email) {

        //사용자 이메일로 모든 카테고리 조회
        List<ValueCategory> categories = valueCategoryRepository.findAllByUserEmail(email);

        // 카테고리 리스트를 DTO 리스트로 변환 후 반환
        return categories.stream()
                .map(category -> ValueCategoryResponseDto.builder()
                        .categoryId(category.getId())
                        .categoryName(category.getCategoryName())
                        .build())
                .collect(Collectors.toList());
    }*/

    //특정 카테고리 조회
    public ValueCategoryResponseDto getValueCategory(Long categoryId) {

        //카테고리 ID로 카테고리 조회
        ValueCategory valueCategory = valueCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다"));

        // 조회된 카테고리를 DTO로 변환 후 반환
        return ValueCategoryResponseDto.builder()
                .categoryId(valueCategory.getId())
                .categoryName(valueCategory.getCategoryName())
                .build();
    }
}

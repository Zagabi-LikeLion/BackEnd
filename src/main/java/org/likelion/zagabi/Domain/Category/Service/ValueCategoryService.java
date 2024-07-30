package org.likelion.zagabi.Domain.Category.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Account.Repository.UserJpaRepository;
import org.likelion.zagabi.Domain.Category.Dto.Response.ValueCategoryResponseDto;
import org.likelion.zagabi.Domain.Category.Entity.ValueCategory;
import org.likelion.zagabi.Domain.Category.Repository.ValueCategoryRepository;
import org.likelion.zagabi.Domain.Value.Repository.ValueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ValueCategoryService {

    private final ValueRepository valueRepository;
    private final ValueCategoryRepository valueCategoryRepository;
    private final UserJpaRepository userRepository;

    //카테고리 저장
    public ValueCategoryResponseDto save(String email, String categoryName) {

        //이메일로 유저 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다"));

        //DTO에서 categoryName을 뽑아서 Entity 생성
        ValueCategory valueCategory = ValueCategory.builder()
                .categoryName(categoryName)
                .user(user)
                .build();

        //생성한 Entity를 Repository에 저장
        valueCategoryRepository.save(valueCategory);

        //Repository에 저장 된 엔티티를 DTO로 변환 후 반환
        return ValueCategoryResponseDto.builder()
                .categoryId(valueCategory.getId())
                .categoryName(valueCategory.getCategoryName())
                .build();
    }


    //카테고리 이름 변경
    public ValueCategoryResponseDto updateCategoryName(Long categoryId, String categoryName) {

        //categoryId로 카테고리 조회
        ValueCategory valueCategory = valueCategoryRepository.findById(categoryId)
                .orElseThrow(()-> new RuntimeException("카테고리를 찾을 수 없습니다."));

        //카테고리 이름 업데이트
        valueCategory.updateCategoryName(categoryName);

        //업데이트 된 카테고리를 DTO로 변환 후 반환
        return ValueCategoryResponseDto.builder()
                .categoryId(valueCategory.getId())
                .categoryName(valueCategory.getCategoryName())
                .build();
    }


    //카테고리 삭제
    public void deleteCategory(Long categoryId) {

        //categoryId로 카테고리 조회
        ValueCategory valueCategory = valueCategoryRepository.findById(categoryId)
                .orElseThrow(()-> new RuntimeException("카테고리를 찾을 수 없습니다."));

        //해당 카테고리의 모든 가치관 삭제
        valueRepository.deleteAllByCategoryId(categoryId);

        //카테고리 삭제
        valueCategoryRepository.delete(valueCategory);
    }

}
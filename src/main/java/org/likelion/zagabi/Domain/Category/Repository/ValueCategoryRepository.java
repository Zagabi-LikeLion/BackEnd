package org.likelion.zagabi.Domain.Category.Repository;

import org.likelion.zagabi.Domain.Category.Entity.ValueCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValueCategoryRepository extends JpaRepository<ValueCategory, Long> {

    // 사용자 ID로 모든 카테고리 조회
    List<ValueCategory> findAllByUserEmail(String email);
}

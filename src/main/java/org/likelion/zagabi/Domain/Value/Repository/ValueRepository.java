package org.likelion.zagabi.Domain.Value.Repository;

import org.likelion.zagabi.Domain.Value.Entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValueRepository extends JpaRepository<Value, Long> {
    List<Value> findAllByCategoryId(Long category_Id);

    List<Value> findAllByCategoryIdAndRankingGreaterThan(Long categoryId, Integer ranking);
    Optional<Value> findByRankingAndCategoryId(Integer ranking, Long categoryId);

    //특정 카테고리에 속하는 모든 가치를 삭제하는 메서드
    void deleteAllByCategoryId(Long categoryId);
}

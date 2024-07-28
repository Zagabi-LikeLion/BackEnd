package org.likelion.zagabi.Domain.Value.Repository;

import org.likelion.zagabi.Domain.Value.Entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValueRepository extends JpaRepository<Value, Long> {
    List<Value> findAllByCategoryId(Long category_Id);

    List<Value> findAllByRankingGreaterThan(Integer ranking);
    Optional<Value> findByRanking(Integer ranking);
}
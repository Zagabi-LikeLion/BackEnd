package org.likelion.zagabi.domain.Value.Repository;

import org.likelion.zagabi.domain.Value.Entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValueRepository extends JpaRepository<Value, Long> {
    List<Value> findAllByCategoryId(Long category_Id);

    Optional<Value> findByRanking(Integer ranking);
}

package org.likelion.zagabi.Domain.ValueTrashCan.Repository;

import org.likelion.zagabi.Domain.ValueTrashCan.Entity.ValueTrashCan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValueTrashCanRepository extends JpaRepository<ValueTrashCan, Long> {
    List<ValueTrashCan> findAllByUserId(Long userId);

}

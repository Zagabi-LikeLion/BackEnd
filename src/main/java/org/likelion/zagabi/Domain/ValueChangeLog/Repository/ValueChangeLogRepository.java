package org.likelion.zagabi.Domain.ValueChangeLog.Repository;

import org.likelion.zagabi.Domain.Value.Entity.Value;
import org.likelion.zagabi.Domain.ValueChangeLog.Entity.ValueChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValueChangeLogRepository extends JpaRepository<ValueChangeLog, Long> {
    List<ValueChangeLog> findAllByUserId(Long userId);

}

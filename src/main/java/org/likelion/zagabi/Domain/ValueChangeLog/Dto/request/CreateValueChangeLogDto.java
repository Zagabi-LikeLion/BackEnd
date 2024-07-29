package org.likelion.zagabi.Domain.ValueChangeLog.Dto.request;

import lombok.Builder;
import org.likelion.zagabi.Domain.Value.Entity.Value;
import org.likelion.zagabi.Domain.ValueChangeLog.Entity.ChangeType;
import org.likelion.zagabi.Domain.ValueChangeLog.Entity.ValueChangeLog;

import java.time.LocalDateTime;

@Builder
public record CreateValueChangeLogDto(
        ChangeType changeType,
        String changeReason,
        Long category_id,
        Long value_id
) {
    public ValueChangeLog toEntity() {
        return ValueChangeLog.builder()
                .changeType(changeType)
                .changeReason(changeReason)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
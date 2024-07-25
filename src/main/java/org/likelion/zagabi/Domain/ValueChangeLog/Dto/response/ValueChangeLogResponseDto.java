package org.likelion.zagabi.Domain.ValueChangeLog.Dto.response;

import lombok.Builder;
import org.likelion.zagabi.Domain.Value.Dto.response.ValueResponseDto;
import org.likelion.zagabi.Domain.Value.Entity.Value;
import org.likelion.zagabi.Domain.ValueChangeLog.Entity.ValueChangeLog;

import java.time.LocalDateTime;

@Builder
public record ValueChangeLogResponseDto(
        Long id,
        String changeType,
        String changeReason,

//      Category category
        Value value,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer ranking
) {
    public static ValueChangeLogResponseDto from(ValueChangeLog valueChangeLog){
        return ValueChangeLogResponseDto.builder()
                .id(valueChangeLog.getId())
                .changeReason(valueChangeLog.getChangeReason())
//                .categoryId()
                .value(valueChangeLog.getValue())
                .createdAt(valueChangeLog.getCreatedAt())
                .updatedAt(valueChangeLog.getUpdatedAt())
                .ranking(valueChangeLog.getRanking())
                .build();
    }
}
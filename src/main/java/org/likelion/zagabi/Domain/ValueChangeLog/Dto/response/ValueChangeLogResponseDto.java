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

        String valueTitle,
        String categoryName,
        LocalDateTime createdAt,
        Integer ranking
) {
    public static ValueChangeLogResponseDto from(ValueChangeLog valueChangeLog){
        return ValueChangeLogResponseDto.builder()
                .id(valueChangeLog.getId())
                .changeType(String.valueOf(valueChangeLog.getChangeType()))
                .changeReason(valueChangeLog.getChangeReason())
                .valueTitle(valueChangeLog.getValueTitle())
                .categoryName(valueChangeLog.getCategoryName())
                .createdAt(valueChangeLog.getCreatedAt())
                .ranking(valueChangeLog.getRanking())
                .build();
    }
}
package org.likelion.zagabi.Domain.ValueTrashCan.Dto.response;

import lombok.Builder;

import org.likelion.zagabi.Domain.ValueTrashCan.Entity.ValueTrashCan;

import java.time.LocalDateTime;

@Builder
public record ValueTrashCanResponseDto(
        Long id,
        String value_title,
        String categoryName,
        LocalDateTime createdAt


        ) {
    public static ValueTrashCanResponseDto from(ValueTrashCan valueTrashCan){
        return ValueTrashCanResponseDto.builder()
                .id(valueTrashCan.getId())
                .value_title(valueTrashCan.getValue_title())
                .categoryName(valueTrashCan.getCategoryName())
                .createdAt(valueTrashCan.getCreatedAt())
                .build();
    }
}

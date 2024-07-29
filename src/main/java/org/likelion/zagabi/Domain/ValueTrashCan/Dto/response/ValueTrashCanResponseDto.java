package org.likelion.zagabi.Domain.ValueTrashCan.Dto.response;

import lombok.Builder;

import org.likelion.zagabi.Domain.ValueTrashCan.Entity.ValueTrashCan;

@Builder
public record ValueTrashCanResponseDto(
        Long id,
        String value_title

) {
    public static ValueTrashCanResponseDto from(ValueTrashCan valueTrashCan){
        return ValueTrashCanResponseDto.builder()
                .id(valueTrashCan.getId())
                .value_title(valueTrashCan.getValue_title())
                .build();
    }
}

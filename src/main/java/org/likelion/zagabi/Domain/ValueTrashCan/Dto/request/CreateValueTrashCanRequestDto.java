package org.likelion.zagabi.Domain.ValueTrashCan.Dto.request;

import lombok.Builder;
import org.likelion.zagabi.Domain.ValueTrashCan.Entity.ValueTrashCan;

@Builder
public record CreateValueTrashCanRequestDto(
        Long valueId
) {
    public ValueTrashCan toEntity() {
        return ValueTrashCan.builder()
                .build();
    }
}

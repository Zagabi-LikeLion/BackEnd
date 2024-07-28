package org.likelion.zagabi.Domain.Value.Dto.request;


import lombok.Builder;
import org.likelion.zagabi.Domain.Value.Entity.Value;


@Builder
public record CreateValueRequestDto(
        Long category_id,
        String value_title

) {
    public Value toEntity() {
        return Value.builder()
                .categoryId(category_id)
                .value_title(value_title)
                .build();
    }
}
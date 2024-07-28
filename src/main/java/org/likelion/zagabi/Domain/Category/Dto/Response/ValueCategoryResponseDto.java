package org.likelion.zagabi.Domain.Category.Dto.Response;

import lombok.Builder;

@Builder
public record ValueCategoryResponseDto(
        Long categoryId,
        String categoryName
) {
}

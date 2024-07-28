package org.likelion.zagabi.Domain.Category.Dto.Request;

import lombok.Builder;

@Builder
public record ValueCategoryRequestDto(
        String categoryName
) {
}

package org.likelion.zagabi.Domain.Category.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ValueCategoryRequestDto(

        @Schema(description = "카테고리 이름", example = "행복")
        String categoryName
) {
}

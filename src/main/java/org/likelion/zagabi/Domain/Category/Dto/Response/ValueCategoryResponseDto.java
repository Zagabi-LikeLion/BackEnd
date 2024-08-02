package org.likelion.zagabi.Domain.Category.Dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ValueCategoryResponseDto(

        @Schema(description = "카테고리 ID", example = "1")
        Long categoryId,

        @Schema(description = "카테고리 이름", example = "행복")
        String categoryName
) {
}

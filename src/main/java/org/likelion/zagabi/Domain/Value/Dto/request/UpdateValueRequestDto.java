package org.likelion.zagabi.Domain.Value.Dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
public record UpdateValueRequestDto(
        Long valueId,
        Integer changeRanking
) {
}

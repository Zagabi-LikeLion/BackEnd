package org.likelion.zagabi.domain.Value.Dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
public record UpdateValueRequestDto(
        Long valueId,
        Integer changeRanking
) {
}

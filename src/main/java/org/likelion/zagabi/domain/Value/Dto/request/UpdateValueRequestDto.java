package org.likelion.zagabi.domain.Value.Dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public record UpdateValueRequestDto(
        Long valueId,
        Integer changeRanking
) {
}

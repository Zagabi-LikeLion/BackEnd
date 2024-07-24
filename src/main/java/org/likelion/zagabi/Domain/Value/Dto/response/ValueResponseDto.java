package org.likelion.zagabi.Domain.Value.Dto.response;

import lombok.Builder;
import org.likelion.zagabi.Domain.Value.Entity.Value;

@Builder
public record ValueResponseDto(
      Long id,
      String category,
      String value_title,
      Integer rank

) {
    public static ValueResponseDto from(Value value){
        return ValueResponseDto.builder()
                .id(value.getId())
                .value_title(value.getValue_title())
                .rank(value.getRanking())
                .category(value.getCategory_name())
                .build();
    }
}

package org.likelion.zagabi.Value.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.zagabi.Value.Entity.Value;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ValueResponseDto {
    public Long id;
    public String category;
    public String value_title;
    public Integer rank;

    public static ValueResponseDto from(Value value){
        return ValueResponseDto.builder()
                .id(value.getId())
                .value_title(value.getValue_title())
                .rank(value.getRanking())
                .category(value.getCategory_name())
                .build();
    }
}

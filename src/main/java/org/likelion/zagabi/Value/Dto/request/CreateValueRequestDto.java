package org.likelion.zagabi.Value.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.zagabi.Value.Entity.Value;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateValueRequestDto {

    public Long category_id;
    public String value_title;

    public Value toEntity() {
        return Value.builder()
                .categoryId(category_id)
                .value_title(value_title)
                .build();
    }
}

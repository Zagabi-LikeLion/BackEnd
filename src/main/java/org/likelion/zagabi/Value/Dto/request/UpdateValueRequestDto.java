package org.likelion.zagabi.Value.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateValueRequestDto {
    public Long valueId;
    public Integer changeRanking;
}

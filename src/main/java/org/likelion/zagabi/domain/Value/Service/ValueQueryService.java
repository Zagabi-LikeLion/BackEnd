package org.likelion.zagabi.domain.Value.Service;

import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.domain.Value.Dto.response.ValueResponseDto;
import org.likelion.zagabi.domain.Value.Entity.Value;
import org.likelion.zagabi.domain.Value.Repository.ValueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ValueQueryService {
    private final ValueRepository valueRepository;

    public ValueResponseDto getValue(Long valueId){

        Value value = valueRepository.findById(valueId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 가치관입니다."));

        return ValueResponseDto.from(value);
    }

    public List<ValueResponseDto> getAllValue(Long categoryId){

        List<Value> values = valueRepository.findAllByCategoryId(categoryId);
        return values.stream()
                .map(ValueResponseDto::from)
                .collect(Collectors.toList());
    }
}

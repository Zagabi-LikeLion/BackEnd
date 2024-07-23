package org.likelion.zagabi.Value.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Value.Dto.request.CreateValueRequestDto;
import org.likelion.zagabi.Value.Dto.request.UpdateValueRequestDto;
import org.likelion.zagabi.Value.Dto.response.ValueResponseDto;
import org.likelion.zagabi.Value.Entity.Value;
import org.likelion.zagabi.Value.Repository.ValueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ValueService {
    private final ValueRepository valueRepository;
    @Transactional
    public ValueResponseDto createValue(CreateValueRequestDto createValueRequestDto) {
        Value value = createValueRequestDto.toEntity();
        List<Value> values = valueRepository.findAllByCategoryId(value.getCategoryId());
        int countValue = values.size()+1;

        // 현재 저장되어있는 가치관들의 마지막 순위로 set
        value.setFirstRank(countValue);

        // Category 부분 구현이 완료되면 추가할 코드
        // value.setCategory_name(categoryRepository.findById(value.getCategory_id()));

        valueRepository.save(value);
        return ValueResponseDto.from(value);
    }

    public ValueResponseDto getValue(Long valueId){

        Value value = valueRepository.findById(valueId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 가치관입니다."));

        return ValueResponseDto.from(value);
    }

    @Transactional
    public ValueResponseDto updateValueRanking(UpdateValueRequestDto updateValueRequestDto){
        // 랭킹을 바꾸는 가치관
        Value firstValue = valueRepository.findById(updateValueRequestDto.getValueId()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 가치관입니다."));
        // firstValue에 의해 랭킹이 바뀌어지는 가치관
        Value secondValue = valueRepository.findByRanking(updateValueRequestDto.getChangeRanking()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 가치관입니다."));

        //secondValue는 firstValue의 바꾸기전 랭킹으로 바뀌어짐
        secondValue.updateRanking(firstValue.getRanking());
        log.info("[Value Service] secondValue의 랭킹 ---> {}", secondValue.getRanking());

        firstValue.updateRanking(updateValueRequestDto.changeRanking);
        log.info("[Value Service] firstValue의 랭킹 ---> {}", firstValue.getRanking());


        valueRepository.save(firstValue);
        valueRepository.save(secondValue);

        return ValueResponseDto.from(firstValue);

    }

    @Transactional
    public void deleteValue(Long valueId){
        valueRepository.deleteById(valueId);
    }

    public List<ValueResponseDto> getAllValue(Long categoryId){

        List<Value> values = valueRepository.findAllByCategoryId(categoryId);
        return values.stream()
                .map(ValueResponseDto::from)
                .collect(Collectors.toList());
    }


}

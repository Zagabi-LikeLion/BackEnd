package org.likelion.zagabi.domain.Value.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.domain.Value.Dto.request.CreateValueRequestDto;
import org.likelion.zagabi.domain.Value.Dto.request.UpdateValueRequestDto;
import org.likelion.zagabi.domain.Value.Dto.response.ValueResponseDto;
import org.likelion.zagabi.domain.Value.Entity.Value;
import org.likelion.zagabi.domain.Value.Repository.ValueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ValueService {
    private final ValueRepository valueRepository;
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



    public ValueResponseDto updateValueRanking(UpdateValueRequestDto updateValueRequestDto){
        // 랭킹을 바꾸는 가치관
        Value firstValue = valueRepository.findById(updateValueRequestDto.valueId()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 가치관입니다."));
        // firstValue에 의해 랭킹이 바뀌어지는 가치관
        Value secondValue = valueRepository.findByRanking(updateValueRequestDto.changeRanking()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 가치관입니다."));

        //secondValue는 firstValue의 바꾸기전 랭킹으로 바뀌어짐
        secondValue.updateRanking(firstValue.getRanking());
        log.info("[Value Service] secondValue의 랭킹 ---> {}", secondValue.getRanking());

        firstValue.updateRanking(updateValueRequestDto.changeRanking());
        log.info("[Value Service] firstValue의 랭킹 ---> {}", firstValue.getRanking());


//        valueRepository.save(firstValue);
//        valueRepository.save(secondValue);

        return ValueResponseDto.from(firstValue);

    }

    public void deleteValue(Long valueId){
        Value deleteValue = valueRepository.findById(valueId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 가치관입니다."));
        int deleteRanking = deleteValue.getRanking();

        valueRepository.deleteById(valueId);

        // ranking이 삭제된 값의 ranking보다 높은 값들을 찾아 순위를 하나씩 올림
        List<Value> valuesToUpdate = valueRepository.findAllByRankingGreaterThan(deleteRanking);
        for (Value value : valuesToUpdate) {
            value.updateRanking(value.getRanking() - 1);
            valueRepository.save(value);
        }
    }




}

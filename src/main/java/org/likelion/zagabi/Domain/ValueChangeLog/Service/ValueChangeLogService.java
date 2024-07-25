
package org.likelion.zagabi.Domain.ValueChangeLog.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Value.Dto.request.CreateValueRequestDto;
import org.likelion.zagabi.Domain.Value.Dto.response.ValueResponseDto;
import org.likelion.zagabi.Domain.Value.Entity.Value;
import org.likelion.zagabi.Domain.Value.Repository.ValueRepository;
import org.likelion.zagabi.Domain.ValueChangeLog.Dto.request.CreateValueChangeLogDto;
import org.likelion.zagabi.Domain.ValueChangeLog.Dto.response.ValueChangeLogResponseDto;
import org.likelion.zagabi.Domain.ValueChangeLog.Entity.ChangeType;
import org.likelion.zagabi.Domain.ValueChangeLog.Entity.ValueChangeLog;
import org.likelion.zagabi.Domain.ValueChangeLog.Repository.ValueChangeLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ValueChangeLogService {
    private final ValueRepository valueRepository;
    private final ValueChangeLogRepository valueChangeLogRepository;

    public ValueChangeLogResponseDto createValueChangeLog (CreateValueChangeLogDto createValueChangeLogDto) {

        ValueChangeLog valueChangeLog = createValueChangeLogDto.toEntity();

        if (valueChangeLog.getChangeType() == ChangeType.Add_Category) {
//            카테고리 추가되면 추가할 코드
//            Category category = categoryRepository.findById(createValueChangeLogDto.category_id());
//            valueChangeLog.setCategory(category);
            valueChangeLog.setCreatedAt(LocalDateTime.now());
        }
        else if(valueChangeLog.getChangeType() == ChangeType.Add_Value) {
            Optional<Value> value = valueRepository.findById(createValueChangeLogDto.value_id());
            valueChangeLog.setValue(value.get());
            valueChangeLog.setCreatedAt(LocalDateTime.now());

        }
        else if(valueChangeLog.getChangeType() == ChangeType.Change_Rank){
            Optional<Value> value = valueRepository.findById(createValueChangeLogDto.value_id());
            valueChangeLog.setValue(value.get());
            valueChangeLog.setRanking(value.get().getRanking());
            valueChangeLog.setCreatedAt(LocalDateTime.now());
        }
        else if (valueChangeLog.getChangeType() == ChangeType.Delete_Value){
            valueChangeLog.setCreatedAt(LocalDateTime.now());
        }

        valueChangeLogRepository.save(valueChangeLog);
        return ValueChangeLogResponseDto.from(valueChangeLog);
    }
}

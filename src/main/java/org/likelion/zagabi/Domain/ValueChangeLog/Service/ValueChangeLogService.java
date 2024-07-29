
package org.likelion.zagabi.Domain.ValueChangeLog.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Category.Entity.ValueCategory;
import org.likelion.zagabi.Domain.Category.Repository.ValueCategoryRepository;
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
    private final ValueCategoryRepository valueCategoryRepository;

    public ValueChangeLogResponseDto createValueChangeLog (/*String Email*/CreateValueChangeLogDto createValueChangeLogDto) {

        ValueChangeLog valueChangeLog = createValueChangeLogDto.toEntity();
        /*//이메일로 유저 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다"));*/

//        valueChangeLog.setUser(user);

        if (valueChangeLog.getChangeType() == ChangeType.Add_Category) {
            if(createValueChangeLogDto.category_id() == null) {
                throw new IllegalArgumentException("카테고리 ID를 입력해주세요");
            }
            Optional<ValueCategory> valueCategory = Optional.ofNullable(valueCategoryRepository.findById(createValueChangeLogDto.category_id()).orElseThrow(() -> new RuntimeException("존재하지 않는 가치관 카테고리입니다.")));;
            valueChangeLog.setCategoryName(valueCategory.get().getCategoryName());
//            카테고리 추가되면 추가할 코드
//            Category category = categoryRepository.findById(createValueChangeLogDto.category_id());
//            valueChangeLog.setCategory(category);
//            valueChangeLog.setCreatedAt(LocalDateTime.now());
        }
        else if(valueChangeLog.getChangeType() == ChangeType.Add_Value) {
            if(createValueChangeLogDto.value_id() == null) {
                throw new IllegalArgumentException("가치관 ID를 입력해주세요");
            }
            Optional<Value> value = Optional.ofNullable(valueRepository.findById(createValueChangeLogDto.value_id()).orElseThrow(() -> new RuntimeException("가치관을 찾을 수 없습니다")));;

            if(createValueChangeLogDto.category_id() == null) {
                throw new IllegalArgumentException("카테고리 ID를 입력해주세요");
            }
            Optional<ValueCategory> valueCategory = Optional.ofNullable(valueCategoryRepository.findById(createValueChangeLogDto.category_id()).orElseThrow(() -> new RuntimeException("존재하지 않는 가치관 카테고리입니다.")));
            valueChangeLog.setCategoryName(valueCategory.get().getCategoryName());
            valueChangeLog.setValueTitle(value.get().getValue_title());
            valueChangeLog.setValue(value.get());

        }
        else if(valueChangeLog.getChangeType() == ChangeType.Change_Rank){
            if(createValueChangeLogDto.category_id() == null) {
                throw new IllegalArgumentException("카테고리 ID를 입력해주세요");
            }
            if(createValueChangeLogDto.changeReason() == null) {
                throw new IllegalArgumentException("가치관 순위 변화 이유를 입력해주세요");
            }
            if(createValueChangeLogDto.value_id() == null) {
                throw new IllegalArgumentException("가치관 ID를 입력해주세요");
            }
            Optional<ValueCategory> valueCategory = Optional.ofNullable(valueCategoryRepository.findById(createValueChangeLogDto.category_id()).orElseThrow(() -> new RuntimeException("존재하지 않는 가치관 카테고리입니다.")));
            valueChangeLog.setCategoryName(valueCategory.get().getCategoryName());
            Optional<Value> value = Optional.ofNullable(valueRepository.findById(createValueChangeLogDto.value_id()).orElseThrow(() -> new RuntimeException("가치관을 찾을 수 없습니다")));;
            valueChangeLog.setValueTitle(value.get().getValue_title());
            valueChangeLog.setValue(value.get());
            valueChangeLog.setRanking(value.get().getRanking());

        }
        else if (valueChangeLog.getChangeType() == ChangeType.Delete_Value){
            if(createValueChangeLogDto.category_id() == null) {
                throw new IllegalArgumentException("카테고리 ID를 입력해주세요");
            }
            if(createValueChangeLogDto.value_id() == null) {
                throw new IllegalArgumentException("가치관 ID를 입력해주세요");
            }
            Optional<ValueCategory> valueCategory = Optional.ofNullable(valueCategoryRepository.findById(createValueChangeLogDto.category_id()).orElseThrow(() -> new RuntimeException("존재하지 않는 가치관 카테고리입니다.")));
            valueChangeLog.setCategoryName(valueCategory.get().getCategoryName());
            Optional<Value> value = Optional.ofNullable(valueRepository.findById(createValueChangeLogDto.value_id()).orElseThrow(() -> new RuntimeException("가치관을 찾을 수 없습니다")));;
            valueChangeLog.setValueTitle(value.get().getValue_title());
            valueChangeLog.setValue(value.get());
        }

        valueChangeLogRepository.save(valueChangeLog);
        return ValueChangeLogResponseDto.from(valueChangeLog);
    }
}

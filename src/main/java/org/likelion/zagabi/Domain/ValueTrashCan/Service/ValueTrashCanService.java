package org.likelion.zagabi.Domain.ValueTrashCan.Service;



import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Account.Repository.UserJpaRepository;
import org.likelion.zagabi.Domain.Value.Entity.Value;
import org.likelion.zagabi.Domain.Value.Repository.ValueRepository;
import org.likelion.zagabi.Domain.ValueTrashCan.Dto.request.CreateValueTrashCanRequestDto;
import org.likelion.zagabi.Domain.ValueTrashCan.Dto.response.ValueTrashCanResponseDto;
import org.likelion.zagabi.Domain.ValueTrashCan.Entity.ValueTrashCan;
import org.likelion.zagabi.Domain.ValueTrashCan.Repository.ValueTrashCanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ValueTrashCanService {
    private final ValueTrashCanRepository valueTrashCanRepository;
    private final ValueRepository valueRepository;
    private final UserJpaRepository userJpaRepository;

    public ValueTrashCanResponseDto createValueTrashCan(String email, CreateValueTrashCanRequestDto createValueTrashCanRequestDto) {


     User user = userJpaRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));


        Optional<Value> value = Optional.ofNullable(valueRepository.findById(createValueTrashCanRequestDto.valueId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가치관입니다.")));
        ValueTrashCan valueTrashCan = createValueTrashCanRequestDto.toEntity();

        valueTrashCan.setValue_title(value.get().getValue_title());
        valueTrashCan.setUser(user);
        valueTrashCan.setCreatedAt(LocalDateTime.now());
        valueTrashCan.setCategoryName(value.get().getCategory_name());

        valueTrashCanRepository.save(valueTrashCan);

        int deleteRanking = value.get().getRanking();

        valueRepository.deleteById(createValueTrashCanRequestDto.valueId());

        // ranking이 삭제된 값의 ranking보다 높은 값들을 찾아 순위를 하나씩 올림
        List<Value> valuesToUpdate = valueRepository.findAllByRankingGreaterThan(deleteRanking);
        for (Value whatValue : valuesToUpdate) {
            whatValue.updateRanking(whatValue.getRanking() - 1);
            valueRepository.save(whatValue);
        }
        return ValueTrashCanResponseDto.from(valueTrashCan);
    }
}

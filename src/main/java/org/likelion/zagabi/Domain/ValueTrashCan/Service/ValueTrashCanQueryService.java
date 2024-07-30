package org.likelion.zagabi.Domain.ValueTrashCan.Service;

import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Account.Repository.UserJpaRepository;
import org.likelion.zagabi.Domain.ValueTrashCan.Dto.response.ValueTrashCanResponseDto;
import org.likelion.zagabi.Domain.ValueTrashCan.Entity.ValueTrashCan;
import org.likelion.zagabi.Domain.ValueTrashCan.Repository.ValueTrashCanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ValueTrashCanQueryService {
    private final ValueTrashCanRepository valueTrashCanRepository;
    private final UserJpaRepository userJpaRepository;
    public ValueTrashCanResponseDto getValueTrashCan(Long valueTrashCanId){

        ValueTrashCan valueTrashCan = valueTrashCanRepository.findById(valueTrashCanId).orElseThrow(()-> new IllegalArgumentException("가치관 쓰레기통에 존재하지 않는 가치관입니다."));

        return ValueTrashCanResponseDto.from(valueTrashCan);
    }

    public List<ValueTrashCanResponseDto> getAllValueTrashCan(String email){
        //이메일로 유저 조회
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다"));
        List<ValueTrashCan> valueTrashCans = valueTrashCanRepository.findAllByUserId(user.getId());
        return valueTrashCans.stream()
                .map(ValueTrashCanResponseDto::from)
                .collect(Collectors.toList());
    }
}

package org.likelion.zagabi.Domain.ValueChangeLog.Service;

import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Account.Repository.UserJpaRepository;
import org.likelion.zagabi.Domain.Value.Dto.response.ValueResponseDto;
import org.likelion.zagabi.Domain.Value.Entity.Value;
import org.likelion.zagabi.Domain.ValueChangeLog.Dto.response.ValueChangeLogResponseDto;
import org.likelion.zagabi.Domain.ValueChangeLog.Entity.ValueChangeLog;
import org.likelion.zagabi.Domain.ValueChangeLog.Repository.ValueChangeLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ValueChangeLogQueryService {
    private final ValueChangeLogRepository valueChangeLogRepository;
    private final UserJpaRepository userJpaRepository;
    public ValueChangeLogResponseDto getValueChangeLog(Long valueChangeLogId){

        ValueChangeLog valueChangeLog = valueChangeLogRepository.findById(valueChangeLogId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 가치관 변화 기록입니다."));

        return ValueChangeLogResponseDto.from(valueChangeLog);
    }


    public List<ValueChangeLogResponseDto> getAllValueChangeLog(String email){
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다"));

        List<ValueChangeLog> valueChangeLogs = valueChangeLogRepository.findAllByUserId(user.getId());
        return valueChangeLogs.stream()
                .map(ValueChangeLogResponseDto::from)
                .collect(Collectors.toList());
    }
}

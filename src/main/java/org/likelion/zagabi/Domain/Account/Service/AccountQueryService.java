package org.likelion.zagabi.Domain.Account.Service;

import lombok.RequiredArgsConstructor;
import org.likelion.zagabi.Domain.Account.Dto.Response.SecurityQuestionResponseDto;
import org.likelion.zagabi.Domain.Account.Entity.SecurityQuestion;
import org.likelion.zagabi.Domain.Account.Repository.SecurityQuestionRepository;
import org.likelion.zagabi.Domain.Diary.Dto.Response.DiaryQuestionResponseDto;
import org.likelion.zagabi.Domain.Diary.Entity.DiaryQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountQueryService {
    private final SecurityQuestionRepository securityQuestionRepository;

    public List<SecurityQuestionResponseDto> getSecurityQuestions() {
        List<SecurityQuestion> securityQuestions = securityQuestionRepository.findAll();
        if (securityQuestions.isEmpty()) {
            throw new RuntimeException("DB에 질문이 존재하지 않음");
        }
        return securityQuestions.stream()
                .map(SecurityQuestionResponseDto::from)
                .collect(Collectors.toList());
    }
}
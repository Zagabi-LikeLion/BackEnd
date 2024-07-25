package org.likelion.zagabi.Domain.ValueTrashCan.Service;



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
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ValueTrashCanService {
    private final ValueTrashCanRepository valueTrashCanRepository;
    private final ValueRepository valueRepository;


    public ValueTrashCanResponseDto createValueTrashCan(CreateValueTrashCanRequestDto createValueTrashCanRequestDto) {

//      인증 인가가 구현이 된다면 추가할 코드
//      User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));


        Optional<Value> value = Optional.ofNullable(valueRepository.findById(createValueTrashCanRequestDto.valueId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가치관입니다.")));
        ValueTrashCan valueTrashCan = createValueTrashCanRequestDto.toEntity();

        valueTrashCan.setValue_title(value.get().getValue_title());
//      valueTrashCan.setUser(user);
        valueTrashCanRepository.save(valueTrashCan);

        //가치관 휴지통에 이동한 후 가치관 삭제
        valueRepository.deleteById(createValueTrashCanRequestDto.valueId());
        return ValueTrashCanResponseDto.from(valueTrashCan);
    }
}

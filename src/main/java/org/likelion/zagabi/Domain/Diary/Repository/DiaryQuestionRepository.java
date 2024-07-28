package org.likelion.zagabi.Domain.Diary.Repository;

import org.likelion.zagabi.Domain.Diary.Entity.DiaryQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaryQuestionRepository extends JpaRepository<DiaryQuestion, Long> {

    //diary_question 테이블의 모든 행을 무작위로 정렬한 다음 그 중 하나만 선택하여 반환(랜덤)
    @Query(value = "SELECT * FROM diary_question ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<DiaryQuestion> findRandomQuestion();
}

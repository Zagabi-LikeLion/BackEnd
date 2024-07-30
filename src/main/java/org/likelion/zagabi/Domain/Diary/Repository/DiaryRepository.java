package org.likelion.zagabi.Domain.Diary.Repository;

import org.likelion.zagabi.Domain.Diary.Entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    // 특정한 날에 특정 유저가 작성한 일기를 조회
    @Query("SELECT d FROM Diary d WHERE DATE(d.createdAt) = :date AND d.user.email = :email")
    Optional<Diary> findByDateAndUserEmail(@Param("date") LocalDate date, @Param("email") String email);
}

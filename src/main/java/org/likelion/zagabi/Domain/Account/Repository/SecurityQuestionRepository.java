package org.likelion.zagabi.Domain.Account.Repository;

import org.likelion.zagabi.Domain.Account.Entity.SecurityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, Long> {
}

package org.likelion.zagabi.Domain.Account.Repository;

import org.likelion.zagabi.Domain.Account.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}

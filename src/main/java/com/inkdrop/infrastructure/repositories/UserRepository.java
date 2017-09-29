package com.inkdrop.infrastructure.repositories;

import com.inkdrop.domain.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUid(Integer uid);

  @EntityGraph("with-subscriptions")
  User findByBackendAccessToken(String token);
}

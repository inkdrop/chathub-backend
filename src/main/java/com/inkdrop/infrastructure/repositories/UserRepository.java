package com.inkdrop.infrastructure.repositories;

import com.inkdrop.domain.user.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUid(Integer uid);

  @Cacheable(cacheNames = "userByBackendToken")
  @EntityGraph("with-join-rooms")
  User findByBackendAccessToken(String token);
}

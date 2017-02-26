package com.inkdrop.infrastructure.repositories;

import com.inkdrop.domain.models.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUid(Integer uid);

  @Cacheable(cacheNames = "userByBackendToken", key = "#root.args[0]")
  User findByBackendAccessToken(String token);
}

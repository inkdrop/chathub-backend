package com.inkdrop.app.domain.repositories;

import com.inkdrop.app.domain.models.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByAccessToken(String token);

  User findByUid(Integer uid);

  @Cacheable(cacheNames = "userByBackendToken", key = "#root.args[0]")
  User findByBackendAccessToken(String token);
}

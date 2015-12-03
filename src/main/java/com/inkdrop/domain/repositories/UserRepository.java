package com.inkdrop.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inkdrop.domain.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByAccessToken(String token);
 	User findByUid(Integer uid);
	User findByBackendAccessToken(String token);
}

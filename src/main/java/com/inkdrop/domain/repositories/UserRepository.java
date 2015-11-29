package com.inkdrop.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import com.inkdrop.domain.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public User findByAccessToken(String token);
	public User findByUid(Integer uid);
}

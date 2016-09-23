package com.inkdrop.app.domain.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.inkdrop.app.domain.models.User;

public interface UserRepository extends MongoRepository<User, String> {
	User findByAccessToken(String token);
 	User findByUid(Integer uid);
	@Cacheable(cacheNames="userByBackendToken", key="#root.args[0]") User findByBackendAccessToken(String token);
}

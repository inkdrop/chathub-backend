package com.inkdrop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.User;
import com.inkdrop.domain.repositories.UserRepository;

@Component
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User findByUid(Integer uid){
		return userRepository.findByUid(uid);
	}

	public User findByBackendToken(String token){
		return userRepository.findByBackendAccessToken(token);
	}
}

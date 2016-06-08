package com.inkdrop;

import org.springframework.beans.factory.annotation.Autowired;

import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.UserRepository;

public class TestHelper {

	@Autowired protected UserRepository userRepo;
	
	protected User createUser(){
		User u = new User();
		u.setNickname("testUser");
		u.setUid(1234);
		
		return userRepo.save(u);
	}
	
}

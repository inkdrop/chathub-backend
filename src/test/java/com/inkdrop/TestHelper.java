package com.inkdrop;

import com.inkdrop.domain.models.User;
import com.inkdrop.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TestHelper {

  @Autowired
  protected UserRepository userRepo;

  protected User createUser() {
    User u = new User();
    u.setLogin("testUser");
    u.setUid(1234);
    u.setBackendAccessToken("valid");

    return userRepo.save(u);
  }

}

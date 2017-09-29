package com.inkdrop.domain.user.services;

import com.inkdrop.application.helpers.TokenGeneratorHelper;
import com.inkdrop.domain.user.User;
import com.inkdrop.domain.user.events.UserCreatedEvent;
import com.inkdrop.infrastructure.repositories.UserRepository;
import java.io.IOException;
import org.kohsuke.github.GHMyself;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class UserInitialisationService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ApplicationContext context;

  public User findOrInstantiateUser(GHMyself githubUser, String accessToken) throws IOException {
    User user = userRepository.findByUid(githubUser.getId());
    if (user == null) {
      user = new User();
      user.setUid(githubUser.getId());
      user.setMemberSince(githubUser.getCreatedAt());
      user.setBackendAccessToken(TokenGeneratorHelper.newToken(25));
      context.publishEvent(new UserCreatedEvent(user, githubUser));
    }
    user.setAccessToken(accessToken);
    user = updateDataFromGithub(githubUser, user);
    return user;
  }

  private User updateDataFromGithub(GHMyself currentUser, User user) throws IOException {
    user.setAvatar(currentUser.getAvatarUrl());
    user.setCompany(currentUser.getCompany());
    user.setEmail(currentUser.getEmail());
    user.setLocation(currentUser.getLocation());
    user.setName(currentUser.getName());
    user.setLogin(currentUser.getLogin());

    return user;
  }
}

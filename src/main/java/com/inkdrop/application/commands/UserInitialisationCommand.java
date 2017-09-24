package com.inkdrop.application.commands;

import com.inkdrop.domain.user.User;
import com.inkdrop.infrastructure.annotations.Command;
import com.inkdrop.infrastructure.repositories.UserRepository;
import java.io.IOException;
import org.kohsuke.github.GHMyself;
import org.springframework.beans.factory.annotation.Autowired;

@Command
public class UserInitialisationCommand {

  @Autowired
  UserRepository userRepository;

  public User findOrInstantiateUser(GHMyself currentUser, String accessToken) throws IOException {
    User user = userRepository.findByUid(currentUser.getId());
    if (user == null) {
      user = new User();
      user.setUid(currentUser.getId());
      user.setMemberSince(currentUser.getCreatedAt());
    }
    user.setAccessToken(accessToken);
    user = userFromGithub(currentUser, user);
    return user;
  }

  private User userFromGithub(GHMyself currentUser, User user) throws IOException {
    user.setAvatar(currentUser.getAvatarUrl());
    user.setCompany(currentUser.getCompany());
    user.setEmail(currentUser.getEmail());
    user.setLocation(currentUser.getLocation());
    user.setName(currentUser.getName());
    user.setLogin(currentUser.getLogin());
    user.setUpdatedAt(null);

    return user;
  }
}

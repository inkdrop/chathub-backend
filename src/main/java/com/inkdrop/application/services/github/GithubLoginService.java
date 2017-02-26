package com.inkdrop.application.services.github;

import com.inkdrop.application.exceptions.ChathubBackendException;
import com.inkdrop.domain.models.User;
import com.inkdrop.infrastructure.repositories.UserRepository;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHMyself;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GithubLoginService extends AbstractGithubService {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  SetupUserService setupUserService;


  private GHMyself githubUser = null;

  public User createOrLoginUser(String githubAccessToken) throws ChathubBackendException {
    try {
      this.githubUser = getCurrentUser(githubAccessToken);
      User user = userService.findOrCreateUser(githubUser, githubAccessToken);
      if (user.getId() == null) {
        log.info("New user arrived, setting up");
        setupUserService.setupUser(user, githubUser);
      }
      return userRepository.save(user);
    } catch (IOException exception) {
      throw new ChathubBackendException(exception.getMessage());
    }
  }
}

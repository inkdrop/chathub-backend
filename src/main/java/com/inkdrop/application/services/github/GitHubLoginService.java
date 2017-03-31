package com.inkdrop.application.services.github;

import com.inkdrop.application.commands.UserInitialisationCommand;
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
public class GitHubLoginService extends AbstractGitHubService {

  @Autowired
  UserInitialisationCommand userInitialisationCommand;

  @Autowired
  UserRepository userRepository;

  @Autowired
  SetupUserService setupUserService;

  public User createOrLoginUser(String githubAccessToken) throws ChathubBackendException {
    try {
      GHMyself gitHubUser = getCurrentUser(githubAccessToken);
      User user = userInitialisationCommand.findOrInstantiateUser(gitHubUser, githubAccessToken);
      if (user.isNewRecord()) {
        log.info("New user arrived, setting up");
        setupUserService.setupUser(user, gitHubUser);
      }
      return userRepository.save(user);
    } catch (IOException exception) {
      throw new ChathubBackendException(exception.getMessage());
    }
  }
}

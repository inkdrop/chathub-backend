package com.inkdrop.application.services.github;

import com.inkdrop.domain.user.User;
import com.inkdrop.domain.user.services.UserInitialisationService;
import com.inkdrop.infrastructure.exceptions.ChathubBackendException;
import com.inkdrop.infrastructure.repositories.UserRepository;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHMyself;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GitHubLoginService extends AbstractGitHubService {

  @Autowired
  UserInitialisationService userInitialisationService;

  @Autowired
  UserRepository userRepository;

  public User createOrFindUser(String githubAccessToken) throws ChathubBackendException {
    try {
      GHMyself gitHubUser = getCurrentUser(githubAccessToken);
      User user = userInitialisationService.findOrInstantiateUser(gitHubUser, githubAccessToken);
      return userRepository.save(user);
    } catch (IOException exception) {
      throw new ChathubBackendException(exception.getMessage());
    }
  }
}

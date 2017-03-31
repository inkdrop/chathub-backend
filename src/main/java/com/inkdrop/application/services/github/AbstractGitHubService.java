package com.inkdrop.application.services.github;

import java.io.IOException;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GitHub;

public abstract class AbstractGitHubService {

  protected GHMyself getCurrentUser(String token) throws IOException {
    return getGitHubConnection(token).getMyself();
  }

  private GitHub getGitHubConnection(String token) throws IOException {
    return GitHub.connectUsingOAuth(token);
  }
}

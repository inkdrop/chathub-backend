package com.inkdrop.app.services.github;

import java.io.IOException;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GitHub;

public abstract class AbstractGithubService {

	protected GitHub getGitHubConnection(String token) throws IOException {
		return GitHub.connectUsingOAuth(token);
	}

	protected GHMyself getCurrentUser(String token) throws IOException{
		return getGitHubConnection(token).getMyself();
	}

	protected Integer getUidFromGithub(String token) throws IOException{
		return getGitHubConnection(token).getMyself().getId();
	}
}

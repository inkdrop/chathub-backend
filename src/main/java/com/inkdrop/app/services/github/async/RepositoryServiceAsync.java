package com.inkdrop.app.services.github.async;

import java.io.IOException;
import java.util.concurrent.Future;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHOrganization;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.inkdrop.app.services.github.RepositoryService;

@Service
public class RepositoryServiceAsync extends RepositoryService {

	@Async
	public Future<Void> findOrCreateRoomAsync(GHMyself currentUser) throws IOException{
		currentUser.getRepositories().values()
				.stream()
				.forEach(repo -> findOrCreateRoom(repo));

		for (GHOrganization org : currentUser.getAllOrganizations()) {
			org.getRepositories().values()
					.stream()
					.forEach(repo -> findOrCreateRoom(repo));
		}
		return new AsyncResult<Void>(null);
	}
}

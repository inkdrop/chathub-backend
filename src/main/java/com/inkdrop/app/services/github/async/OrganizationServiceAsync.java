package com.inkdrop.app.services.github.async;

import java.io.IOException;
import java.util.concurrent.Future;

import org.kohsuke.github.GHMyself;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.inkdrop.app.services.github.OrganizationService;

@Service
public class OrganizationServiceAsync extends OrganizationService {

	@Async
	public Future<Void> findOrCreateOrganizationAsync(GHMyself currentUser) throws IOException{
		currentUser.getOrganizations()
				.stream()
				.forEach(org -> findOrCreateOrganization(org));
		return new AsyncResult<Void>(null);
	}
}

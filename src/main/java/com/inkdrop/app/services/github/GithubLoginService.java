package com.inkdrop.app.services.github;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.RoomRepository;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.services.github.async.OrganizationServiceAsync;
import com.inkdrop.app.services.github.async.RepositoryServiceAsync;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GithubLoginService extends AbstractGithubService {

	@Autowired private UserService userService;
	@Autowired private OrganizationService organizationService;
	@Autowired private RepositoryService repositoryService;

	@Autowired private OrganizationServiceAsync organizationServiceAsync;
	@Autowired private RepositoryServiceAsync repositoryServiceAsync;

	@Autowired private UserRepository userRepository;
	@Autowired private RoomRepository roomRepository;

	private GHMyself githubUser = null;

	public User loginUser(String ghubAccessToken) throws IOException {
		this.githubUser = getCurrentUser(ghubAccessToken);
		User user = userService.findOrCreateUser(githubUser, ghubAccessToken);
		if(user.getId() == null){
			log.info("New user arrived");
			doSync(user);
		} else {
			log.info("Existing user logging in: "+user.getLogin());
			doAsync(user);
		}

		return userRepository.save(user);
	}

	private void doSync(User user) throws IOException {
		log.info("Creating organizations");
		findOrCreateOrganizations();
		log.info("Creating rooms");
		findOrCreateRooms();
		log.info("Linking user to rooms");
		linkUserToRooms(user);
		log.info("Done: linking user to rooms");
	}

	private void doAsync(User user) throws IOException {
		log.info("[ASYNC] Updating organizations");
		organizationServiceAsync.findOrCreateOrganizationAsync(githubUser);
		log.info("[ASYNC] Updating rooms");
		repositoryServiceAsync.findOrCreateRoomAsync(githubUser);
	}

	private void linkUserToRooms(User user) throws IOException {
		Map<String, GHRepository> repositories = githubUser.getRepositories();
		List<Integer> userRepoUid = repositories
				.values().stream()
				.map(repo -> repo.getId()).collect(Collectors.toList());

		for (GHOrganization org : githubUser.getAllOrganizations()) {
			userRepoUid.addAll(org.getRepositories().values()
					.stream()
					.map(repo -> repo.getId())
					.collect(Collectors.toList()));
		}
		userRepoUid.stream()
		.forEach(uid -> user.getRooms().add(roomRepository.findByUid(uid)));
		//		user.setRooms(roomRepository.findByUidIn(userRepoUid)
		//				.stream().collect(Collectors.toSet()));
	}

	private void findOrCreateOrganizations() throws IOException {
		githubUser.getAllOrganizations()
		.stream()
		.forEach(org -> organizationService.findOrCreateOrganization(org));
	}

	private void findOrCreateRooms() throws IOException {
		githubUser.getRepositories().values()
		.stream()
		.forEach(repo -> repositoryService.findOrCreateRoom(repo));
	}
}

package com.inkdrop.app.services;

import java.io.IOException;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inkdrop.app.domain.models.Organization;
import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.OrganizationRepository;
import com.inkdrop.app.domain.repositories.RoomRepository;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.eventEmitter.LogEventEmitter;
import com.inkdrop.app.exceptions.ChathubBackendException;
import com.inkdrop.app.helpers.InstantHelper;

@Service
public class GitHubService {

	@Autowired UserRepository userRepository;

	@Autowired OrganizationRepository organizationRepo;

	@Autowired RoomRepository roomRespository;
	
	@Autowired LogEventEmitter emitter;

	private Logger log = LogManager.getLogger(GitHubService.class);

	@Transactional
	public void createOrUpdateUser(String token) throws IOException {
		log.info("Token: "+token);
		User user = userRepository.findByAccessToken(token);
		if(user == null) {
			user = new User();
			user.setAccessToken(token);
			user = loadUserFromGithub(user, true);
			emitter.newUser(user);
		} else {
			log.info("User exists...checking if need to update...");
			if(InstantHelper.biggerThanSixHours(user.getUpdatedAt())){
				log.info("Last update bigger than 6 hours. Updating...");
				user = loadUserFromGithub(user, false);
			}
		}
	}

	@Transactional
	public void createOrUpdateOrg(String name, String token) throws ChathubBackendException {
		try{
			Organization org = organizationRepo.findByLoginIgnoreCase(name);
			if(org == null)
				org = new Organization();

			GHOrganization ghOrganization = getGitHubConnection(token).getOrganization(name);

			org.setAvatar(ghOrganization.getAvatarUrl());
			org.setBlog(ghOrganization.getBlog());
			org.setName(ghOrganization.getName());
			org.setCompany(ghOrganization.getCompany());
			org.setLogin(ghOrganization.getLogin());
			org.setUid(ghOrganization.getId());
			org.setLocation(ghOrganization.getLocation());
			org.setUpdatedAt(null);
			org.setMembers(ghOrganization.listMembers().asList().stream().map(u -> u.getLogin()).collect(Collectors.toSet()));

			organizationRepo.save(org);
			for(GHRepository repo : ghOrganization.getRepositories().values()) 
				findOrCreateRepository(repo, org);
		}catch(IOException e){
			throw new ChathubBackendException(e.getMessage());
		}
	}

	private User loadUserFromGithub(User user, boolean newUser) throws IOException {
		GitHub gh = getGitHubConnection(user.getAccessToken());
		GHMyself myself = gh.getMyself();
		user.setAvatar(myself.getAvatarUrl());
		user.setCompany(myself.getCompany());
		user.setEmail(myself.getEmail());
		user.setLocation(myself.getLocation());
		user.setMemberSince(myself.getCreatedAt());
		user.setName(myself.getName());
		user.setNickname(myself.getLogin());
		user.setUid(myself.getId());
		user.setUpdatedAt(null); // FIXME Find out if there is a way to call save forcing @PreUpdate

		user = userRepository.save(user);
		if(newUser)
			createOrganizations(user, gh);
		return user;
	}

	private void addUserToRoom(User user, Room repo) {
		if(!user.getRooms().contains(repo))
			user.getRooms().add(repo);
		userRepository.save(user);
	}

	private GitHub getGitHubConnection(String token) throws IOException {
		return GitHub.connectUsingOAuth(token);
	}

	private void createOrganizations(User user, GitHub gh) throws IOException {
		for(GHOrganization org : gh.getMyself().getAllOrganizations()){
			Organization organization = organizationRepo.findByUid(org.getId());
			if(organization == null)
				organization = new Organization();

			organization.setAvatar(org.getAvatarUrl());
			organization.setBlog(org.getBlog());
			organization.setCompany(org.getCompany());
			organization.setName(org.getName());
			organization.setUid(org.getId());
			organization.setLogin(org.getLogin());
			organization.setMembers(org.listMembers().asList().stream().map(u -> u.getLogin()).collect(Collectors.toSet()));

			organization = organizationRepo.save(organization);
			for(GHRepository repo : org.getRepositories().values()) {
				Room r = findOrCreateRepository(repo, organization);
				addUserToRoom(user, r);
			}
		}
	}

	private Room findOrCreateRepository(GHRepository repoGh, Organization org) throws IOException {
		Room repo = roomRespository.findByUid(repoGh.getId());
		if(repo == null){
			repo = new Room();
			repo.set_private(repoGh.isPrivate());
			repo.setCreatedAt(repoGh.getCreatedAt());
			repo.setDescription(repoGh.getDescription());
			repo.setHomepage(repoGh.getHomepage());
			repo.setFullName(repoGh.getFullName());
			repo.setName(repoGh.getName());
			repo.setOrganization(org);
			repo.setUid(repoGh.getId());
		}
		
		repo.setOwner(repoGh.getOwner().getLogin());
		repo.setUpdatedAt(repoGh.getUpdatedAt());
		
		roomRespository.save(repo);
		return repo;
	}
}

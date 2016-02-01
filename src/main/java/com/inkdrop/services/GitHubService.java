package com.inkdrop.services;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.models.User;
import com.inkdrop.domain.repositories.RoomRepository;
import com.inkdrop.domain.repositories.UserRepository;
import com.inkdrop.exceptions.ChathubBackendException;
import com.inkdrop.helpers.InstantHelper;

@Component
public class GitHubService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoomRepository roomRepostitory;

	private Logger log = LogManager.getLogger(GitHubService.class);

	public User createOrUpdateUser(String token) throws IOException {
		log.info("Token: "+token);
		User user = userRepository.findByAccessToken(token);
		if(user == null) {
			user = new User();
			user.setAccessToken(token);
			user = createOrUpdateUser(user);
		} else {
			log.info("User exists...checking if need to update...");
			if(InstantHelper.biggerThanSixHours(user.getUpdatedAt())){
				log.info("Last update bigger than 6 hours. Updating...");
				user = createOrUpdateUser(user);
			}
		}
		return user;
	}

	private User createOrUpdateUser(User user) throws IOException {
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
		user.setUpdatedAt(null); // FIXME Find out if there is a way to call save forcing and @PreUpdate

		user = userRepository.save(user);
		createOrgsFor(user, gh);
		return user;
	}

	private void createOrgsFor(User user, GitHub gh) throws IOException {
		for(GHOrganization org : gh.getMyself().getAllOrganizations()){
			Room room = roomRepostitory.findByUid(org.getId());
			if(room == null)
				room = new Room();

			room.setAvatar(org.getAvatarUrl());
			room.setBlog(org.getBlog());
			room.setCompany(org.getCompany());
			room.setName(org.getName());
			room.setUid(org.getId());
			room.setLogin(org.getLogin());

			room = roomRepostitory.save(room);
			addRoomToUser(user, room);
		}
	}

	private void addRoomToUser(User user, Room room) {
		if(!user.getRooms().contains(room)){
			user.getRooms().add(room);
			userRepository.save(user);
		}
	}

	private GitHub getGitHubConnection(String token) throws IOException{
		return GitHub.connectUsingOAuth(token);
	}

//	private GitHub getGitHubConnection() throws IOException{
//		return GitHub.connectAnonymously();
//	}

	public Room createOrUpdateRoom(String name, String token) throws ChathubBackendException {
		try{
			Room room = roomRepostitory.findByLoginIgnoreCase(name);
			if(room == null)
				room = new Room();

			GHOrganization ghOrganization = getGitHubConnection(token).getOrganization(name);

			room.setAvatar(ghOrganization.getAvatarUrl());
			room.setBlog(ghOrganization.getBlog());
			room.setName(ghOrganization.getName());
			room.setCompany(ghOrganization.getCompany());
			room.setLogin(ghOrganization.getLogin());
			room.setUid(ghOrganization.getId());
			room.setLocation(ghOrganization.getLocation());
			room.setUpdatedAt(null);

			return roomRepostitory.save(room);
		}catch(IOException e){
			throw new ChathubBackendException(e.getMessage());
		}

	}
}

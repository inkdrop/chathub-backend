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
import com.inkdrop.domain.repositories.RoomRepostitory;
import com.inkdrop.domain.repositories.UserRepository;

@Component
public class GitHubService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoomRepostitory roomRepostitory;
	
	private Logger log = LogManager.getLogger(GitHubService.class);
	
	public User createOrUpdateUser(String token) throws IOException{
		log.info("Token: "+token);
		GitHub gh = getGitHubConnection(token);
		User user = userRepository.findByAccessToken(token);
		if(user == null){
			user = new User();
			user.setAccessToken(token);
		}
		
		GHMyself myself = gh.getMyself();
		user.setAvatar(myself.getAvatarUrl());
		user.setCompany(myself.getCompany());
		user.setEmail(myself.getEmail());
		user.setLocation(myself.getLocation());
		user.setMemberSince(myself.getCreatedAt());
		user.setName(myself.getName());
		user.setNickname(myself.getLogin());
		user.setUid(myself.getId());
		
		user = userRepository.save(user);
		createReposFor(user, gh);
		return user;
	}
	
	private void createReposFor(User user, GitHub gh) throws IOException {
		for(GHOrganization org : gh.getMyself().getAllOrganizations()){
			Room room = roomRepostitory.findByUid(org.getId());
			if(room == null){
				room = new Room();
			}
			
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
}

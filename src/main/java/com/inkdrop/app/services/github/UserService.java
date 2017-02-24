package com.inkdrop.app.services.github;

import java.io.IOException;

import org.kohsuke.github.GHMyself;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.UserRepository;

@Service
public class UserService extends AbstractGithubService {

	@Autowired UserRepository userRepository;

	public User findOrCreateUser(GHMyself currentUser, String accessToken) throws IOException{
		User user = userRepository.findByUid(currentUser.getId());
		if(user == null){
			user = new User();
		}
		user.setAccessToken(accessToken);
		user = userFromGithub(currentUser, user);
		return user;
	}

	private User userFromGithub(GHMyself currentUser, User user) throws IOException{
		user.setAvatar(currentUser.getAvatarUrl());
		user.setCompany(currentUser.getCompany());
		user.setEmail(currentUser.getEmails2().get(0).getEmail());
		user.setLocation(currentUser.getLocation());
		user.setMemberSince(currentUser.getCreatedAt());
		user.setName(currentUser.getName());
		user.setLogin(currentUser.getLogin());
		user.setUid(currentUser.getId());
		user.setUpdatedAt(null);

		return user;
	}
}

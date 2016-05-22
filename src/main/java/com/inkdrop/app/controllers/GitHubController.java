package com.inkdrop.app.controllers;

import java.io.IOException;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.services.GitHubService;

@RestController
@EnableAutoConfiguration
public class GitHubController {

	@Autowired
	GitHubService gitHubService;

	@Autowired
	UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST, path="/auth/github")
	public ResponseEntity<?> createUser(@PathParam("token") String token){
		try {
			gitHubService.createFromGithub(token);
			User user = userRepository.findByAccessToken(token);
			user.setFirebaseJwt(getFirebaseJwtToken(user.getUid()));
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	private String getFirebaseJwtToken(Integer uid) {
		return FirebaseAuth.getInstance().createCustomToken(uid.toString());
	}
}

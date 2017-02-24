package com.inkdrop.app.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.inkdrop.app.controllers.api.v1.BasicController;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.exceptions.ChathubBackendException;
import com.inkdrop.app.services.github.GithubLoginService;

@RestController
@EnableAutoConfiguration
public class GitHubController extends BasicController {

	@Autowired
	GithubLoginService gitHubService;

	@Autowired
	UserRepository userRepository;


	@RequestMapping(method = RequestMethod.POST, path="/auth/github")
	public ResponseEntity<?> createUser(@PathParam("token") String token){
		try {
			User user = gitHubService.loginUser(token);
			if(user == null)
				throw new ChathubBackendException("No user was found");
			user.setFirebaseJwt(getFirebaseJwtToken(user.getUid()));
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ChathubBackendException e) {
			return createErrorResponse(e);
		}
	}

	private String getFirebaseJwtToken(Integer uid) {
		Map<String, Object> params = new HashMap<>();
		return FirebaseAuth.getInstance().createCustomToken(uid.toString(), params);
	}
}

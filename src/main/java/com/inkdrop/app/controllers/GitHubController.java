package com.inkdrop.app.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.inkdrop.app.feignInterfaces.GithubAccessToken;
import com.inkdrop.app.services.github.GithubLoginService;

import feign.Feign;
import feign.codec.StringDecoder;

@RestController
@EnableAutoConfiguration
public class GitHubController extends BasicController {

	@Autowired
	GithubLoginService gitHubService;

	@Autowired
	UserRepository userRepository;
	
	@Value("${github.client}")
	String ghKey;
	
	@Value("${github.secret}")
	String ghSecret;

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
	
	@RequestMapping(method = RequestMethod.POST, path="/auth/github/code")
	public ResponseEntity<?> getAccessToken(@PathParam("code") String code) throws UnsupportedEncodingException{
		GithubAccessToken github = Feign.builder()
            .decoder(new StringDecoder())
            .target(GithubAccessToken.class, "https://github.com");

		String token = github.accessToken(ghKey, ghSecret, code);
		String accessToken = token.substring(token.indexOf("=") + 1, token.indexOf("&"));
		return createSuccessfulResponse(createUser(accessToken));
	}

	private String getFirebaseJwtToken(Integer uid) {
		Map<String, Object> params = new HashMap<>();
		return FirebaseAuth.getInstance().createCustomToken(uid.toString(), params);
	}
}

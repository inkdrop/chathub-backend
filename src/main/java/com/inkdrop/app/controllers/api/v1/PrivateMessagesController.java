package com.inkdrop.app.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.services.PrivateMessageService;

/**
 * Not implenented on the front-end yet
 * @author luizkowalski
 *
 */
@RestController
@EnableAutoConfiguration
public class PrivateMessagesController extends BasicController{

	@Autowired
	PrivateMessageService service;

	@Autowired
	UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST, path="/v1/private_message/{uid}")
	public ResponseEntity<?> receiveMessage(@RequestBody Params params,
			@PathVariable Integer uid,
			@RequestHeader("Auth-Token") String token){
		try {
			User sender = userRepository.findByUid(uid);
			User receiver = userRepository.findByUid(uid);
			String content = params.getContent();

			service.send(sender, receiver, content);

			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}

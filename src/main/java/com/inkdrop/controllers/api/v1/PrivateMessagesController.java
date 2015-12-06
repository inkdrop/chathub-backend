package com.inkdrop.controllers.api.v1;

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

import com.inkdrop.domain.models.PrivateMessage;
import com.inkdrop.services.PrivateMessageService;

@RestController
@EnableAutoConfiguration
public class PrivateMessagesController {

	@Autowired
	PrivateMessageService service;

	@RequestMapping(method = RequestMethod.POST, path="/v1/private_message/{uid}")
	public ResponseEntity<?> receiveMessage(@RequestBody PrivateMessage message,
			@PathVariable Integer uid,
			@RequestHeader("Auth-Token") String token){
		try {
			PrivateMessage pm = service.buildMessage(message, uid, token);
			service.saveAndSend(pm);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}

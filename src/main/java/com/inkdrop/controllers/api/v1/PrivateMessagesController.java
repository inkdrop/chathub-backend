package com.inkdrop.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inkdrop.domain.models.PrivateMessage;
import com.inkdrop.services.PrivateMessageService;

@RestController
@EnableAutoConfiguration
public class PrivateMessagesController {

	@Autowired
	PrivateMessageService service;

	@RequestMapping(method = RequestMethod.POST, path="/v1/private_message/{uid}")
	public void receiveMessage(@RequestParam PrivateMessage message,
			@PathVariable String uidDestination,
			@RequestHeader("Auth-Token") String token){
		try {
			service.saveAndSend(message);
		} catch(Exception e) {

		}
	}
}

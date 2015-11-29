package com.inkdrop.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

	@RequestMapping(method = RequestMethod.POST, path="/private_message")
	public void receiveMessage(@RequestParam PrivateMessage message){
		service.saveAndSend(message);
	}
}

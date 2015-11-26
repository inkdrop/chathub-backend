package com.inkdrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inkdrop.model.Message;
import com.inkdrop.services.MessageService;

@RestController
@EnableAutoConfiguration
public class IndexController {

	@Autowired
	MessageService service;

	@RequestMapping(method = RequestMethod.POST, path="/message")
	public void receiveMessage(@RequestBody Message message){
		service.saveMessage(message);
	}

	@RequestMapping(method = RequestMethod.POST, path="/message/rooms")
	public void receiveMessageAll(@RequestParam String message){
		service.sendToAllRooms(message);
	}
}

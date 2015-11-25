package com.inkdrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inkdrop.queues.ChatManager;

@RestController
@EnableAutoConfiguration
public class IndexController {

	@Autowired
	ChatManager manager;
	
	@RequestMapping(method = RequestMethod.POST, path="/message")
	public void receiveMessage(@RequestParam String room,@RequestParam String message){
		manager.sendMessageToRoom(message, room);
	}
	
	@RequestMapping(method = RequestMethod.POST, path="/message/rooms")
	public void receiveMessageAll(@RequestParam String message){
		manager.sendToAllRooms(message);
	}
}

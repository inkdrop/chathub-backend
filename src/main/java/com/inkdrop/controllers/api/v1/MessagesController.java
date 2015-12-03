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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inkdrop.domain.models.Message;
import com.inkdrop.services.MessageService;

@RestController
@EnableAutoConfiguration
public class MessagesController {

	@Autowired
	MessageService messageService;

	@RequestMapping(method = RequestMethod.POST, path="/v1/message/{room}")
	public ResponseEntity<?> receiveMessage(@PathVariable String room,
			@RequestBody Message partialMessage,
			@RequestHeader("Auth-Token") String token){
		try{
			Message m = messageService.buildMessage(partialMessage, room, token);
			messageService.saveAndSend(m);

			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path="/v1/message/rooms")
	public void receiveMessageAll(@RequestParam String message, @RequestHeader("Auth-Token") String token){
		messageService.sendToAllRooms(message);
	}
}

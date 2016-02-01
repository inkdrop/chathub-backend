package com.inkdrop.controllers.api.v1;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

import com.inkdrop.controllers.api.models.Params;
import com.inkdrop.domain.models.Message;
import com.inkdrop.exceptions.ChathubBackendException;
import com.inkdrop.services.MessageService;
import com.inkdrop.services.RoomService;
import com.inkdrop.services.UserService;

@RestController
@EnableAutoConfiguration
public class MessagesController {

	private Logger log = LogManager.getLogger(MessagesController.class);

	@Autowired
	MessageService messageService;

	@Autowired
	RoomService roomService;

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.POST, path="/v1/messages/{room}")
	public ResponseEntity<?> sendMessageToRoom(@PathVariable String room,
			@RequestBody Params params,
			@RequestHeader("Auth-Token") String token){
		try{
			Message m = messageService.buildMessage(params.getContent(),
					roomService.findByLogin(room),
					userService.findByBackendToken(token));

			messageService.saveAndSend(m);

			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(ChathubBackendException e){
			log.error(e);
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
